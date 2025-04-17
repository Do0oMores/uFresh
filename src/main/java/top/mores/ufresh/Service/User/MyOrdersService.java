package top.mores.ufresh.Service.User;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.*;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.Notification;
import top.mores.ufresh.POJO.Order_items;
import top.mores.ufresh.POJO.Orders;
import top.mores.ufresh.Utils.NotificationMessage;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyOrdersService {
    private final NotificationMessage notificationMessage;

    public MyOrdersService(NotificationMessage notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    /**
     * 获取未结算订单物品
     *
     * @param userId 用户ID
     * @return 订单内物品信息
     */
    public APIResponse<Map<String, Object>> getOrderItems(int userId) {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            //获取用户订单
            OrdersDao ordersDao = session.getMapper(OrdersDao.class);
            List<Orders> userNoFinishOrder = ordersDao.getUserOrdersByStatus(userId, "未结算");

            if (userNoFinishOrder != null && !userNoFinishOrder.isEmpty()) {
                Map<String, Object> resultMap = new HashMap<>();
                String order_uuid = userNoFinishOrder.get(0).getOrder_uuid();
                double total_price = userNoFinishOrder.get(0).getTotal_price();
                resultMap.put("total_price", total_price);
                OrderItemsDao orderItemsDao = session.getMapper(OrderItemsDao.class);
                List<Order_items> orderItems = orderItemsDao.getOrderItemsByUUID(order_uuid);
                resultMap.put("order_items", orderItems);
                resultMap.put("order_uuid", order_uuid);
                return new APIResponse<>(200, resultMap);
            } else {
                return new APIResponse<>(200, "暂无未结算订单");
            }
        } catch (Exception e) {
            return new APIResponse<>(500, "发生了预料之外的错误：" + e.getMessage());
        }
    }

    /**
     * 根据订单号更新订单信息，同时检测库存是否充足
     *
     * @param order 订单信息
     * @return 更新结果
     */
    public APIResponse<Void> updateOrderStatusByOrderUUID(Orders order) {
        if (order.getOrder_uuid() == null || order.getOrder_uuid().isEmpty()) {
            return new APIResponse<>(404, "空订单无法执行此操作");
        } else {
            try (SqlSession session = MybatisUtils.getSqlSession()) {
                // 先获取订单内所有商品明细
                OrderItemsDao orderItemDao = session.getMapper(OrderItemsDao.class);
                List<Order_items> orderItems = orderItemDao.getOrderItemsByOrderUUID(order.getOrder_uuid());
                // 检查每个商品的库存是否足够
                for (Order_items item : orderItems) {
                    int inventory = getCommodityAmount(item.getCommodity_id());
                    if (item.getQuantity() > inventory) {
                        session.rollback();
                        return new APIResponse<>(500, "商品库存不足，库存数量：" + inventory);
                    }
                }
                // 库存充足则执行订单状态更新
                OrdersDao ordersDao = session.getMapper(OrdersDao.class);
                int result = ordersDao.updateOrderStatus(order);
                if (result != 1) {
                    session.rollback();
                    return new APIResponse<>(500, "订单提交失败");
                }

                // 更新订单状态成功后，扣减每个商品的库存
                CommodityDao commodityDao = session.getMapper(CommodityDao.class);
                for (Order_items item : orderItems) {
                    int updateCount = commodityDao.reduceCommodityQuantity(item.getCommodity_id(), item.getQuantity());
                    if (updateCount != 1) {
                        session.rollback();
                        return new APIResponse<>(500, "商品 " + item.getCommodity_id() + " 库存扣减失败");
                    }
                }
                NotificationDao notificationDao = session.getMapper(NotificationDao.class);
                Notification notification = new Notification();
                notification.setUser_id(1);
                notification.setOrder_uuid(order.getOrder_uuid());
                notification.setNotification_title(notificationMessage.getOrderSubmit().getTitle());
                notification.setNotification_content(notificationMessage.getOrderSubmit().getContent().replace("{order_uuid}", order.getOrder_uuid()));
                notification.setType(notificationMessage.getOrderSubmit().getType());
                notification.setTime(LocalDateTime.now());
                if (notificationDao.addNewNotification(notification) != 1) {
                    session.rollback();
                    return new APIResponse<>(500, "消息队列生成失败");
                }
                // 如果所有操作均成功则提交事务
                session.commit();
                return new APIResponse<>(200, "订单已提交");
            } catch (Exception e) {
                return new APIResponse<>(500, "发生意料之外的错误：" + e.getMessage());
            }
        }
    }

    /**
     * 返回库存的商品数量
     *
     * @param commodity_id 商品ID
     * @return 商品数量
     */
    private int getCommodityAmount(int commodity_id) {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            CommodityDao commodityDao = session.getMapper(CommodityDao.class);
            return commodityDao.getCommodityQuantity(commodity_id);
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 减少订单商品数量
     *
     * @param order_items 指定的订单商品
     * @return 减少结果
     */
    public APIResponse<Void> decreaseOrderQuantity(Order_items order_items) {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            OrderItemsDao orderItemsDao = session.getMapper(OrderItemsDao.class);
            int result = orderItemsDao.decreaseQuantity(order_items);
            if (result == 1) {
                session.commit();
                return new APIResponse<>(200, "");
            } else {
                session.rollback();
                return new APIResponse<>(404, "减少订单内商品时发生错误，可能未找到匹配记录");
            }
        } catch (Exception e) {
            return new APIResponse<>(500, "减少订单内商品时发生意料之外的错误" + e.getMessage());
        }
    }

    /**
     * 移除订单内商品
     *
     * @param order_items 指定的订单商品
     * @return 移除结果
     */
    public APIResponse<Void> deleteOrderItem(Order_items order_items) {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            OrderItemsDao orderItemsDao = session.getMapper(OrderItemsDao.class);
            int result = orderItemsDao.deleteOrderItem(order_items);
            if (result == 1) {
                session.commit();
                return new APIResponse<>(200, "已移除该商品");
            } else {
                session.rollback();
                return new APIResponse<>(404, "移除商品时发生错误，可能是未找到对应的商品");
            }
        } catch (Exception e) {
            return new APIResponse<>(500, "发生意料之外的错误：" + e.getMessage());
        }
    }

    /**
     * 增加订单内商品数量
     *
     * @param order_items 订单内商品
     * @return 增加结果
     */
    public APIResponse<Void> increaseQuantity(Order_items order_items) {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            OrderItemsDao orderItemsDao = session.getMapper(OrderItemsDao.class);
            int result = orderItemsDao.increaseQuantity(order_items);
            if (result == 1) {
                session.commit();
                return new APIResponse<>(200, "");
            } else {
                session.rollback();
                return new APIResponse<>(404, "增加商品数量时发生错误，可能是未找到指定的商品");
            }
        } catch (Exception e) {
            return new APIResponse<>(500, "发生意料之外的错误：" + e.getMessage());
        }
    }

    /**
     * 清空订单与订单内商品
     *
     * @param order 订单号
     * @return 清空结果
     */
    public APIResponse<Void> clearOrder(Orders order) {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            OrdersDao ordersDao = session.getMapper(OrdersDao.class);
            OrderItemsDao orderItemsDao = session.getMapper(OrderItemsDao.class);
            int result = ordersDao.deleteOrder(order);
            int result1 = orderItemsDao.clearOrderItem(order.getOrder_uuid());
            if (result == 1 && result1 >= 1) {
                session.commit();
                return new APIResponse<>(200, "订单已取消");
            } else {
                session.rollback();
                return new APIResponse<>(404, "清空订单时发生错误，可能是未找到指定订单");
            }
        } catch (Exception e) {
            return new APIResponse<>(500, "发生意料之外的错误" + e.getMessage());
        }
    }
}

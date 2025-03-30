package top.mores.ufresh.Service.User;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.CommodityDao;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.DAO.OrderItemsDao;
import top.mores.ufresh.DAO.OrdersDao;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.Order_items;
import top.mores.ufresh.POJO.Orders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyOrdersService {

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
                        return new APIResponse<>(500, "商品 " + item.getCommodity_id()
                                + " 库存不足，库存数量：" + inventory);
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
                // 如果所有操作均成功则提交事务
                session.commit();
                return new APIResponse<>(200, "订单已提交且库存已更新");
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
            e.printStackTrace();
            return -1;
        }
    }
}

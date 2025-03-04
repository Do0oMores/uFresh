package top.mores.ufresh.Service.User;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.*;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.Cart_items;
import top.mores.ufresh.POJO.Order_items;
import top.mores.ufresh.POJO.Orders;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShopCartService {

    /**
     * 将商品添加到购物车
     *
     * @param cart_items 商品
     * @return 添加结果
     */
    public APIResponse<Void> addCommodityToCart(Cart_items cart_items) {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            CartItemsDao cartItemsDao = session.getMapper(CartItemsDao.class);
            // 查询购物车中是否存在该商品
            List<Cart_items> existingCartItems = cartItemsDao.getCommodityIDFromUserCart(
                    cart_items.getUser_id(), cart_items.getCommodity_id()
            );

            LocalDateTime now = LocalDateTime.now();
            cart_items.setAdded_time(now);

            if (existingCartItems == null || existingCartItems.isEmpty()) {
                // 购物车中没有该商品，直接添加
                return handleCartOperation(session,
                        cartItemsDao.addCommodityToCart(cart_items),
                        "添加到购物车成功",
                        "添加到购物车失败"
                );
            }

            // 遍历购物车，检查是否已有相同商品和规格
            for (Cart_items existingItem : existingCartItems) {
                if (existingItem.getCommodity_id() == cart_items.getCommodity_id() &&
                        existingItem.getSpec_id() == cart_items.getSpec_id()) {
                    // 购物车中有完全相同的商品，增加数量
                    int updatedRows = cartItemsDao.addCommodityAmount(
                            cart_items.getUser_id(),
                            cart_items.getCommodity_id(),
                            cart_items.getSpec_id(),
                            cart_items.getAmount()
                    );
                    return handleCartOperation(session,
                            updatedRows,
                            "购物车商品数量更新成功",
                            "更新购物车商品数量失败"
                    );
                }
            }
            // 购物车中有相同商品但规格不同，插入新记录
            return handleCartOperation(session,
                    cartItemsDao.addCommodityToCart(cart_items),
                    "添加到购物车成功",
                    "添加到购物车失败"
            );
        } catch (Exception e) {
            return new APIResponse<>(500, "系统错误" + e.getMessage());
        }
    }


    /**
     * 添加到购物车处理
     *
     * @param session         sql
     * @param operationResult 处理结果
     * @param successMessage  成功
     * @param failureMessage  失败
     * @return 添加结果
     */
    private APIResponse<Void> handleCartOperation(SqlSession session, int operationResult,
                                                  String successMessage, String failureMessage) {
        if (operationResult == 1) {
            session.commit();
            return new APIResponse<>(200, successMessage);
        } else {
            session.rollback();
            return new APIResponse<>(500, failureMessage);
        }
    }

    /**
     * 获取用户购物车
     *
     * @param user_id 用户ID
     * @return 购物车内商品信息
     */
    public APIResponse<List<Cart_items>> getUserCart(int user_id) {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            CartItemsDao cartItemsDao = session.getMapper(CartItemsDao.class);
            List<Cart_items> cart_items = cartItemsDao.getUserCart(user_id);
            return new APIResponse<>(200, cart_items);
        } catch (Exception e) {
            return new APIResponse<>(500, "发生意料之外的错误：" + e.getMessage());
        }
    }

    /**
     * 创建订单
     *
     * @param cart_items 购物车物品
     * @return 订单创建结果
     */
    public APIResponse<Void> builtOrder(List<Cart_items> cart_items) {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            OrdersDao ordersDao = session.getMapper(OrdersDao.class);
            OrderItemsDao orderItemsDao = session.getMapper(OrderItemsDao.class);
            CommoditySpecsDao commoditySpecsDao = session.getMapper(CommoditySpecsDao.class);
            CartItemsDao cartItemsDao = session.getMapper(CartItemsDao.class);

            if (cart_items == null || cart_items.isEmpty()) {
                return new APIResponse<>(400, "购物车为空，无法结算");
            }

            int userID = cart_items.get(0).getUser_id();

            // 检查是否有未结算订单
            if (!ordersDao.getUserOrdersByStatus(userID, "未结算").isEmpty()) {
                return new APIResponse<>(400, "您有未结算的订单待处理");
            }

            List<Integer> cart_item_id = cart_items.stream()
                    .map(Cart_items::getCart_item_id)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            if (cart_item_id.isEmpty()) {
                return new APIResponse<>(400, "没有可移除的商品");
            }

            Map<String, Double> priceCache = new HashMap<>();

            double total_price = cart_items.stream()
                    .mapToDouble(item -> {
                        String key = item.getCommodity_id() + "-" + item.getSpec();
                        return priceCache.computeIfAbsent(key, k -> commoditySpecsDao.getPrice(item.getCommodity_id(), item.getSpec())) * item.getAmount();
                    })
                    .sum();

            // 生成订单 UUID
            String order_uuid = UUID.randomUUID().toString();

            // 批量插入订单物品
            for (Cart_items item : cart_items) {
                Order_items order_item = new Order_items();
                order_item.setOrder_uuid(order_uuid);
                order_item.setSpec(item.getSpec());
                order_item.setQuantity(item.getAmount());
                order_item.setCommodity_id(item.getCommodity_id());

                if (orderItemsDao.addOrderItem(order_item) != 1) {
                    session.rollback();
                    return new APIResponse<>(400, "存在无法加入订单的商品：" + item.getCommodity_id());
                }
            }

            // 创建订单
            Orders order = new Orders();
            order.setOrder_uuid(order_uuid);
            order.setUser_id(userID);
            order.setStatus("未结算");
            order.setCreated_time(LocalDateTime.now());
            order.setTotal_price(total_price);

            if (ordersDao.addOrder(order) != 1) {
                session.rollback();
                return new APIResponse<>(400, "生成订单失败");
            }

            // 批量移除购物车商品
            if (cartItemsDao.removeCommoditiesFromCart(cart_item_id) < cart_item_id.size()) {
                session.rollback();
                return new APIResponse<>(400, "从购物车移除商品失败");
            }

            session.commit();
            return new APIResponse<>(200, "订单生成成功：" + order_uuid);

        } catch (Exception e) {
            return new APIResponse<>(500, "发生意料之外的错误：" + e.getMessage());
        }
    }
}

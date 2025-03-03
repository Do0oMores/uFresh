package top.mores.ufresh.Service.User;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.CartItemsDao;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.Cart_items;

import java.time.LocalDateTime;
import java.util.List;

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
            System.out.println(e.getMessage());
            return new APIResponse<>(500, "系统错误");
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

//    public APIResponse<Void> builtOrder(int commodity_id,String spec){
//        try (SqlSession session = MybatisUtils.getSqlSession()) {
//
//        }
//    }
}

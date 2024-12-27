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

    public APIResponse<Void> addCommodityToCart(Cart_items cart_items) {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            CartItemsDao cartItemsDao = session.getMapper(CartItemsDao.class);
            // 查询购物车中是否存在该商品
            Cart_items existingCartItem = cartItemsDao.getCommodityIDFromUserCart(
                    cart_items.getUser_id(), cart_items.getCommodity_id()
            );

            LocalDateTime now = LocalDateTime.now();
            cart_items.setAdded_time(now);


            if (existingCartItem == null) {
                // 购物车中没有该商品，直接添加
                return handleCartOperation(session,
                        cartItemsDao.addCommodityToCart(cart_items),
                        "添加到购物车成功",
                        "添加到购物车失败"
                );
            }
            if (existingCartItem.getCommodity_id() == cart_items.getCommodity_id() &&
                    existingCartItem.getSpec_id() == cart_items.getSpec_id()) {
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
            // 购物车中有相同商品但规格不同，插入新记录
            return handleCartOperation(session,
                    cartItemsDao.addCommodityToCart(cart_items),
                    "添加到购物车成功",
                    "添加到购物车失败"
            );
        } catch (Exception e) {
            e.printStackTrace();
            return new APIResponse<>(500, "系统错误");
        }
    }

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

    public APIResponse<List<Cart_items>> getUserCart(int user_id) {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            CartItemsDao cartItemsDao = session.getMapper(CartItemsDao.class);
            List<Cart_items> cart_items = cartItemsDao.getUserCart(user_id);
            return new APIResponse<>(200, cart_items);
        }catch (Exception e) {
            return new APIResponse<>(500, "发生意料之外的错误："+e.getMessage());
        }
    }
}

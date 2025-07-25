package top.mores.ufresh.DAO;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.mores.ufresh.POJO.Cart_items;

import java.util.List;

@Repository
public interface CartItemsDao {
    List<Cart_items> getCommodityIDFromUserCart(int user_id, int commodity_id);

    int addCommodityToCart(Cart_items cart_items);

    int addCommodityAmount(int user_id, int commodity_id, int spec_id, int amount);

    List<Cart_items> getUserCart(int user_id);

    int removeCommoditiesFromCart(@Param("cartItemIds") List<Integer> cartItemIds);

    List<Cart_items> selectCartItemsByName(String name, int user_id);
}

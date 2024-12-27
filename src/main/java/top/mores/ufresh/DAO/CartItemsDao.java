package top.mores.ufresh.DAO;

import top.mores.ufresh.POJO.Cart_items;

public interface CartItemsDao {
    Cart_items getCommodityIDFromUserCart(int user_id, int commodity_id);

    int addCommodityToCart(Cart_items cart_items);

    int addCommodityAmount(int user_id, int commodity_id, int spec_id, int amount);
}

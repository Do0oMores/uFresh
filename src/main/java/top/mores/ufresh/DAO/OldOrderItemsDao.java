package top.mores.ufresh.DAO;

import top.mores.ufresh.POJO.Old_order_items;
import top.mores.ufresh.POJO.Orders;

import java.util.List;

public interface OldOrderItemsDao {
    List<Old_order_items> getOldOrderItems(Orders orders);

    List<Old_order_items> getOldOrderItemsByOrderUUID(Orders orders);
}

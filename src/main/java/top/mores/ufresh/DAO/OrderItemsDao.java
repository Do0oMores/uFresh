package top.mores.ufresh.DAO;

import top.mores.ufresh.POJO.Order_items;

import java.util.List;

public interface OrderItemsDao {

    List<Order_items> getOrderItemsByUUID(int order_uuid);

    int addOrderItem(Order_items order_items);
}

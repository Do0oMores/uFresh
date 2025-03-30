package top.mores.ufresh.DAO;

import org.springframework.stereotype.Repository;
import top.mores.ufresh.POJO.Order_items;

import java.util.List;

@Repository
public interface OrderItemsDao {

    List<Order_items> getOrderItemsByUUID(String order_uuid);

    int addOrderItem(Order_items order_items);

    List<Order_items> getOrderItemsByOrderUUID(String order_uuid);
}

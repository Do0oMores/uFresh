package top.mores.ufresh.DAO;

import org.springframework.stereotype.Repository;
import top.mores.ufresh.POJO.Orders;

import java.util.List;

@Repository
public interface OrdersDao {

    List<Orders> getOrdersList();

    List<Orders> getOrdersByConditions(Orders orders);

    List<Orders> getUserOrdersByStatus(int user_id,String status);

    int addOrder(Orders orders);

    int updateOrderStatus(Orders orders);
}

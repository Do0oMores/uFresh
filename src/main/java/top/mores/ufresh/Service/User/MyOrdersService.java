package top.mores.ufresh.Service.User;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
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
                return new APIResponse<>(200, resultMap);
            } else {
                return new APIResponse<>(200, "暂无未结算订单");
            }
        } catch (Exception e) {
            return new APIResponse<>(500, "发生了预料之外的错误：" + e.getMessage());
        }
    }
}

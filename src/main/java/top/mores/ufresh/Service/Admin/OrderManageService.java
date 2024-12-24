package top.mores.ufresh.Service.Admin;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.DAO.OrdersDao;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.Orders;

import java.util.List;

@Service
public class OrderManageService {

    /**
     * 推送所有订单信息
     *
     * @return 所有订单信息
     */
    public APIResponse<List<Orders>> getOrders() {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            OrdersDao ordersDao = session.getMapper(OrdersDao.class);
            List<Orders> orders = ordersDao.getOrdersList();
            return new APIResponse<>(200, orders);
        } catch (Exception e) {
            return new APIResponse<>(500, "拉取信息出错：" + e.getMessage());
        }
    }

    /**
     * 根据条件查找订单
     *
     * @param orders 传入的查找信息
     * @return 查询结果
     */
    public APIResponse<List<Orders>> getOrdersByConditions(Orders orders) {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            OrdersDao ordersDao = session.getMapper(OrdersDao.class);
            List<Orders> ordersList = ordersDao.getOrdersByConditions(orders);
            if (!ordersList.isEmpty()) {
                return new APIResponse<>(200, "共查询到：" + ordersList.size() + " 条数据", ordersList);
            } else {
                return new APIResponse<>(404, "没有查询到有关的订单");
            }
        } catch (Exception e) {
            return new APIResponse<>(500, "查询出现错误：" + e.getMessage());
        }
    }
}

package top.mores.ufresh.Service.Admin;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.DAO.OrdersDao;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.Orders;

import java.time.LocalDateTime;
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

    /**
     * 管理员编辑订单状态
     *
     * @param orders 编辑的订单信息
     * @return 编辑结果
     */
    public APIResponse<Void> editOrder(Orders orders) {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            OrdersDao ordersDao = session.getMapper(OrdersDao.class);
            LocalDateTime now;
            if (orders.getStatus().equals("已完成")) {
                now = LocalDateTime.now();
                orders.setCompletion_time(now);
            }
            int result = ordersDao.editOrder(orders);
            if (result == 1) {
                session.commit();
                return new APIResponse<>(200, "已修改订单状态");
            } else {
                session.rollback();
                return new APIResponse<>(404, "修改订单状态时出现错误，可能是未找到该订单");
            }
        } catch (Exception e) {
            return new APIResponse<>(500, "发生了意料之外的错误：" + e.getMessage());
        }
    }
}

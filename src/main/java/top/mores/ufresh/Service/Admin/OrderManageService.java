package top.mores.ufresh.Service.Admin;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.DAO.NotificationDao;
import top.mores.ufresh.DAO.OrdersDao;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.Notification;
import top.mores.ufresh.POJO.Orders;
import top.mores.ufresh.Utils.NotificationMessage;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderManageService {
    private final NotificationMessage notificationMessage;

    @Autowired
    public OrderManageService(NotificationMessage notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

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
            NotificationDao notificationDao = session.getMapper(NotificationDao.class);
            LocalDateTime now;
            int notiResult = 0;
            if (orders.getStatus().equals("已完成")) {
                now = LocalDateTime.now();
                orders.setCompletion_time(now);
                Notification notification = new Notification();
                notification.setUser_id(orders.getUser_id());
                notification.setNotification_title(notificationMessage.getOrderFinish().getTitle());
                notification.setNotification_content(notificationMessage.getOrderFinish().getContent());
                notification.setType(notificationMessage.getOrderFinish().getType());
                notification.setTime(now);
                notiResult = notificationDao.addNewNotification(notification);
            }
            int result = ordersDao.editOrder(orders);
            if (result == 1 && notiResult == 1) {
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

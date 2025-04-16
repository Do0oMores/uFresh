package top.mores.ufresh.Service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.DAO.NotificationDao;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.Notification;

import java.util.List;

@Service
public class NotificationService {

    /**
     * 获取消息数
     *
     * @param notification 指定用户的消息
     * @return 消息数量
     */
    public APIResponse<Integer> getNotificationCount(Notification notification) {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            NotificationDao notificationDao = session.getMapper(NotificationDao.class);
            notification.setEnable_read(false);
            List<Notification> data = notificationDao.fetchUserRead(notification);
            if (!data.isEmpty()) {
                return new APIResponse<>(200, data.size());
            } else {
                return new APIResponse<>(404, "");
            }
        } catch (Exception e) {
            return new APIResponse<>(500, "发生意料之外的错误：" + e.getMessage());
        }
    }

    /**
     * 获取用户消息列表
     *
     * @param notification 用户ID
     * @return 消息列表
     */
    public APIResponse<List<Notification>> getUserNotificationList(Notification notification) {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            NotificationDao notificationDao = session.getMapper(NotificationDao.class);
            List<Notification> data = notificationDao.fetchUserNotification(notification.getUser_id());
            return new APIResponse<>(200, data);
        } catch (Exception e) {
            return new APIResponse<>(500, "发生意料之外的错误：" + e.getMessage());
        }
    }

    /**
     * 更新用户消息已读状态
     *
     * @param notification 消息
     * @return 更新结果
     */
    public APIResponse<Void> updateReadStatus(Notification notification) {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            NotificationDao notificationDao = session.getMapper(NotificationDao.class);
            notification.setEnable_read(true);
            int result = notificationDao.renewRead(notification);
            if (result == 1) {
                session.commit();
                return new APIResponse<>(200, "");
            } else {
                return new APIResponse<>(404, "未找到该条信息");
            }
        } catch (Exception e) {
            return new APIResponse<>(500, "发生意料之外的错误" + e.getMessage());
        }
    }
}

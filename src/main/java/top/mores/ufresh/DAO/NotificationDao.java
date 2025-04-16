package top.mores.ufresh.DAO;

import org.springframework.stereotype.Repository;
import top.mores.ufresh.POJO.Notification;

import java.util.List;

@Repository
public interface NotificationDao {
    List<Notification> fetchUserRead(Notification notification);

    int addNewNotification(int user_id,int notification_code);
}

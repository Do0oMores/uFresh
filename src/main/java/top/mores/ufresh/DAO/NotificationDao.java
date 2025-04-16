package top.mores.ufresh.DAO;

import org.springframework.stereotype.Repository;
import top.mores.ufresh.POJO.Notification;

import java.util.List;

@Repository
public interface NotificationDao {
    List<Notification> fetchUserRead(Notification notification);

    int addNewNotification(Notification notification);

    List<Notification> fetchUserNotification(int user_id);

    int renewRead(Notification notification);

    int addAfterSaleNotification(Notification notification);
}

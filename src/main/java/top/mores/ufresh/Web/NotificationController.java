package top.mores.ufresh.Web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.Notification;
import top.mores.ufresh.Service.NotificationService;

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    /**
     * 获取消息数
     *
     * @param notification 指定用户
     * @return 消息数
     */
    @PostMapping("/getNotificationCount")
    @ResponseBody
    public ResponseEntity<?> getNotificationCount(@RequestBody Notification notification) {
        APIResponse<Integer> response = notificationService.getNotificationCount(notification);
        return ResponseEntity.ok(response);
    }
}

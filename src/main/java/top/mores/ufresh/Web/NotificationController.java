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

import java.util.List;

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

    /**
     * 拉取消息列表
     *
     * @param notification 用户ID
     * @return 消息列表
     */
    @PostMapping("/fetch-notificationList")
    @ResponseBody
    public ResponseEntity<?> fetchNotificationList(@RequestBody Notification notification) {
        APIResponse<List<Notification>> response = notificationService.getUserNotificationList(notification);
        return ResponseEntity.ok(response);
    }

    /**
     * 更新消息已读状态
     *
     * @param notification 用户消息
     * @return 更新已读状态结果
     */
    @PostMapping("/mark-as-read")
    @ResponseBody
    public ResponseEntity<?> markAsRead(@RequestBody Notification notification) {
        APIResponse<Void> response = notificationService.updateReadStatus(notification);
        return ResponseEntity.ok(response);
    }
}

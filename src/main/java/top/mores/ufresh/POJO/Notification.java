package top.mores.ufresh.POJO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class Notification {
    private int id;
    private int user_id;
    private String  notification_title;
    private boolean enable_read;
    private String type;
    private String notification_content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
    private String order_uuid;

    public String getOrder_uuid() {
        return order_uuid;
    }

    public void setOrder_uuid(String order_uuid) {
        this.order_uuid = order_uuid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public boolean isEnable_read() {
        return enable_read;
    }

    public void setEnable_read(boolean enable_read) {
        this.enable_read = enable_read;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNotification_title() {
        return notification_title;
    }

    public void setNotification_title(String notification_title) {
        this.notification_title = notification_title;
    }

    public String getNotification_content() {
        return notification_content;
    }

    public void setNotification_content(String notification_content) {
        this.notification_content = notification_content;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", notification_title='" + notification_title + '\'' +
                ", enable_read=" + enable_read +
                ", type='" + type + '\'' +
                ", notification_content='" + notification_content + '\'' +
                ", time=" + time +
                ", order_uuid='" + order_uuid + '\'' +
                '}';
    }
}

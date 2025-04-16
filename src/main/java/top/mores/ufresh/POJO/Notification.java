package top.mores.ufresh.POJO;

public class Notification {

    private int id;
    private int user_id;
    private int notification_code;
    private boolean enable_read;

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

    public int getNotification_code() {
        return notification_code;
    }

    public void setNotification_code(int notification_code) {
        this.notification_code = notification_code;
    }

    public boolean isEnable_read() {
        return enable_read;
    }

    public void setEnable_read(boolean enable_read) {
        this.enable_read = enable_read;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", notification_code=" + notification_code +
                ", enable_read=" + enable_read +
                '}';
    }
}

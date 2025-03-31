package top.mores.ufresh.POJO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class Orders {
    private int order_id;
    private int user_id;
    private double total_price;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created_time;
    private String user_name;
    private String order_uuid;
    private String pickup_method;
    private String order_note;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completion_time;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreated_time() {
        return created_time;
    }

    public void setCreated_time(LocalDateTime created_time) {
        this.created_time = created_time;
    }

    public String getOrder_uuid() {
        return order_uuid;
    }

    public void setOrder_uuid(String order_uuid) {
        this.order_uuid = order_uuid;
    }

    public String getPickup_method() {
        return pickup_method;
    }

    public void setPickup_method(String pickup_method) {
        this.pickup_method = pickup_method;
    }

    public String getOrder_note() {
        return order_note;
    }

    public void setOrder_note(String order_note) {
        this.order_note = order_note;
    }

    public LocalDateTime getCompletion_time() {
        return completion_time;
    }

    public void setCompletion_time(LocalDateTime completion_time) {
        this.completion_time = completion_time;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "order_id=" + order_id +
                ", user_id=" + user_id +
                ", total_price=" + total_price +
                ", status='" + status + '\'' +
                ", created_time=" + created_time +
                ", user_name='" + user_name + '\'' +
                ", order_uuid='" + order_uuid + '\'' +
                ", pickup_method='" + pickup_method + '\'' +
                ", order_note='" + order_note + '\'' +
                ", completion_time=" + completion_time +
                '}';
    }
}

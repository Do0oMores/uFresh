package top.mores.ufresh.POJO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public class Old_order_items {
    private String status;
    private String order_uuid;
    private double total_price;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created_time;
    private String pickup_method;
    private String order_note;
    private List<Commodity> commodity_list;

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public String getOrder_uuid() {
        return order_uuid;
    }

    public void setOrder_uuid(String order_uuid) {
        this.order_uuid = order_uuid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Commodity> getCommodity_list() {
        return commodity_list;
    }

    public void setCommodity_list(List<Commodity> commodity_list) {
        this.commodity_list = commodity_list;
    }

    public LocalDateTime getCreated_time() {
        return created_time;
    }

    public void setCreated_time(LocalDateTime created_time) {
        this.created_time = created_time;
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

    @Override
    public String toString() {
        return "Old_order_items{" +
                "status='" + status + '\'' +
                ", order_uuid='" + order_uuid + '\'' +
                ", total_price=" + total_price +
                ", created_time=" + created_time +
                ", pickup_method='" + pickup_method + '\'' +
                ", order_note='" + order_note + '\'' +
                ", commodity_list=" + commodity_list +
                '}';
    }
}

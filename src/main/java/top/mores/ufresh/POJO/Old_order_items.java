package top.mores.ufresh.POJO;

import java.util.List;

public class Old_order_items {
    private String status;
    private String order_uuid;
    private double total_price;
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

    @Override
    public String toString() {
        return "Old_order_items{" +
                "status='" + status + '\'' +
                ", order_uuid='" + order_uuid + '\'' +
                ", total_price=" + total_price +
                ", commodity_list=" + commodity_list +
                '}';
    }
}

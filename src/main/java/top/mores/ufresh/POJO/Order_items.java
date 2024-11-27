package top.mores.ufresh.POJO;

public class Order_items {
    private int order_item_id;
    private int order_id;
    private int commodity_id;
    private int quantity;
    private double price;

    public int getOrder_item_id() {
        return order_item_id;
    }

    public void setOrder_item_id(int order_item_id) {
        this.order_item_id = order_item_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getCommodity_id() {
        return commodity_id;
    }

    public void setCommodity_id(int commodity_id) {
        this.commodity_id = commodity_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Order_items{" +
                "order_item_id=" + order_item_id +
                ", order_id=" + order_id +
                ", commodity_id=" + commodity_id +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}

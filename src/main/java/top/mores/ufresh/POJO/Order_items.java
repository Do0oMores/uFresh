package top.mores.ufresh.POJO;

public class Order_items {
    private int order_item_id;
    private int order_id;
    private int commodity_id;
    private int quantity;
    private String spec;
    private String order_uuid;
    private String commodity_name;
    private String image;
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

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getOrder_uuid() {
        return order_uuid;
    }

    public void setOrder_uuid(String order_uuid) {
        this.order_uuid = order_uuid;
    }

    public String getCommodity_name() {
        return commodity_name;
    }

    public void setCommodity_name(String commodity_name) {
        this.commodity_name = commodity_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
                ", spec='" + spec + '\'' +
                ", order_uuid='" + order_uuid + '\'' +
                ", commodity_name='" + commodity_name + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                '}';
    }
}

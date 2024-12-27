package top.mores.ufresh.POJO;

import java.time.LocalDateTime;

public class Cart_items {
    private int cart_item_id;
    private int user_id;
    private int commodity_id;
    private int amount;
    private double added_price;
    private int spec_id;
    private LocalDateTime added_time;

    public int getSpec_id() {
        return spec_id;
    }

    public void setSpec_id(int spec_id) {
        this.spec_id = spec_id;
    }

    public LocalDateTime getAdded_time() {
        return added_time;
    }

    public void setAdded_time(LocalDateTime added_time) {
        this.added_time = added_time;
    }

    public int getCart_item_id() {
        return cart_item_id;
    }

    public void setCart_item_id(int cart_item_id) {
        this.cart_item_id = cart_item_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCommodity_id() {
        return commodity_id;
    }

    public void setCommodity_id(int commodity_id) {
        this.commodity_id = commodity_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getAdded_price() {
        return added_price;
    }

    public void setAdded_price(double added_price) {
        this.added_price = added_price;
    }

    @Override
    public String toString() {
        return "Cart_items{" +
                "cart_item_id=" + cart_item_id +
                ", user_id=" + user_id +
                ", commodity_id=" + commodity_id +
                ", amount=" + amount +
                ", added_price=" + added_price +
                ", spec_id=" + spec_id +
                ", added_time=" + added_time +
                '}';
    }
}

package top.mores.ufresh.POJO;

public class Cart_items {
    private int cart_item_id;
    private int cart_id;
    private int commodity_id;
    private int amount;
    private double added_price;

    public int getCart_item_id() {
        return cart_item_id;
    }

    public void setCart_item_id(int cart_item_id) {
        this.cart_item_id = cart_item_id;
    }

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
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
                ", cart_id=" + cart_id +
                ", commodity_id=" + commodity_id +
                ", amount=" + amount +
                ", added_price=" + added_price +
                '}';
    }
}

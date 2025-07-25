package top.mores.ufresh.POJO;

public class Commodity_specs {
    private int id;
    private int commodity_id;
    private String spec;
    private double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCommodity_id() {
        return commodity_id;
    }

    public void setCommodity_id(int commodity_id) {
        this.commodity_id = commodity_id;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Commodity_specs{" +
                "id=" + id +
                ", commodity_id=" + commodity_id +
                ", spec='" + spec + '\'' +
                ", price=" + price +
                '}';
    }
}

package top.mores.ufresh.POJO;

public class Commodity_specs {
    private int id;
    private int commodity_id;
    private String spec_name;
    private String spec_value;

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

    public String getSpec_name() {
        return spec_name;
    }

    public void setSpec_name(String spec_name) {
        this.spec_name = spec_name;
    }

    public String getSpec_value() {
        return spec_value;
    }

    public void setSpec_value(String spec_value) {
        this.spec_value = spec_value;
    }

    @Override
    public String toString() {
        return "Commodity_specs{" +
                "id=" + id +
                ", commodity_id=" + commodity_id +
                ", spec_name='" + spec_name + '\'' +
                ", spec_value='" + spec_value + '\'' +
                '}';
    }
}

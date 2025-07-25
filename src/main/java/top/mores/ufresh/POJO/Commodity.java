package top.mores.ufresh.POJO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public class Commodity {
    private int commodity_id;
    private String commodity_name;
    private String type;
    private int inventory;
    private String description;
    private String image;
    private String support;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate mfd;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate exp;
    private String status;
    private String tag;
    private double price;
    private String spec;
    private int quantity;
    private List<Commodity_specs> specs;

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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getMfd() {
        return mfd;
    }

    public void setMfd(LocalDate mfd) {
        this.mfd = mfd;
    }

    public LocalDate getExp() {
        return exp;
    }

    public void setExp(LocalDate exp) {
        this.exp = exp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public int getCommodity_id() {
        return commodity_id;
    }

    public void setCommodity_id(int commodity_id) {
        this.commodity_id = commodity_id;
    }

    public String getCommodity_name() {
        return commodity_name;
    }

    public void setCommodity_name(String commodity_name) {
        this.commodity_name = commodity_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Commodity_specs> getSpecs() {
        return specs;
    }

    public void setSpecs(List<Commodity_specs> specs) {
        this.specs = specs;
    }

    @Override
    public String toString() {
        return "Commodity{" +
                "commodity_id=" + commodity_id +
                ", commodity_name='" + commodity_name + '\'' +
                ", type='" + type + '\'' +
                ", inventory=" + inventory +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", support='" + support + '\'' +
                ", mfd=" + mfd +
                ", exp=" + exp +
                ", status='" + status + '\'' +
                ", tag='" + tag + '\'' +
                ", price=" + price +
                ", spec='" + spec + '\'' +
                ", quantity=" + quantity +
                ", specs=" + specs +
                '}';
    }
}

package top.mores.ufresh.POJO;

import java.time.LocalDate;

public class Commodity {
    private int commodity_id;
    private String commodity_name;
    private String type;
    private double price;
    private int inventory;
    private String description;
    private String image;
    private String support;
    private LocalDate mfd;
    private LocalDate exp;

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    @Override
    public String toString() {
        return "Commodity{" +
                "commodity_id=" + commodity_id +
                ", commodity_name='" + commodity_name + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", inventory=" + inventory +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", support='" + support + '\'' +
                ", mfd=" + mfd +
                ", exp=" + exp +
                '}';
    }
}

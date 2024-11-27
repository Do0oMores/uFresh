package top.mores.ufresh.POJO;

public class Commodity_information {
    private int information_id;
    private String description;
    private String image;
    private String support;
    private long mfd;
    private long exp;

    public int getInformation_id() {
        return information_id;
    }

    public void setInformation_id(int information_id) {
        this.information_id = information_id;
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

    public long getMfd() {
        return mfd;
    }

    public void setMfd(long mfd) {
        this.mfd = mfd;
    }

    public long getExp() {
        return exp;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "Commodity_information{" +
                "information_id=" + information_id +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", support='" + support + '\'' +
                ", mfd=" + mfd +
                ", exp=" + exp +
                '}';
    }
}

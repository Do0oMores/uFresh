package top.mores.ufresh.POJO;

public class After_sales {
    private int after_sales_id;
    private int order_uuid;
    private String service_type;
    private String reasons;
    private String image;
    private String progress;

    public int getAfter_sales_id() {
        return after_sales_id;
    }

    public void setAfter_sales_id(int after_sales_id) {
        this.after_sales_id = after_sales_id;
    }

    public int getOrder_uuid() {
        return order_uuid;
    }

    public void setOrder_uuid(int order_uuid) {
        this.order_uuid = order_uuid;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "After_sales{" +
                "after_sales_id=" + after_sales_id +
                ", order_uuid=" + order_uuid +
                ", service_type='" + service_type + '\'' +
                ", reasons='" + reasons + '\'' +
                ", image='" + image + '\'' +
                ", progress='" + progress + '\'' +
                '}';
    }
}

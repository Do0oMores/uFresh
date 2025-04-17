package top.mores.ufresh.POJO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class Waste {
    private int id;
    private int commodity_id;
    private int waste_amount;
    private String waste_type;
    private String remarks;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;

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

    public int getWaste_amount() {
        return waste_amount;
    }

    public void setWaste_amount(int waste_amount) {
        this.waste_amount = waste_amount;
    }

    public String getWaste_type() {
        return waste_type;
    }

    public void setWaste_type(String waste_type) {
        this.waste_type = waste_type;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Waste{" +
                "id=" + id +
                ", commodity_id=" + commodity_id +
                ", waste_amount=" + waste_amount +
                ", waste_type='" + waste_type + '\'' +
                ", remarks='" + remarks + '\'' +
                ", time=" + time +
                '}';
    }
}

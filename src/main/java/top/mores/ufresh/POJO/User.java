package top.mores.ufresh.POJO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class User {
    private int user_id;
    private String user_name;
    private String password;
    private Boolean enabled;
    private Boolean admin_enabled;
    private String address;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate register_time;
    private String phone;
    private String email;
    private String avatar_url;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getRegister_time() {
        return register_time;
    }

    public void setRegister_time(LocalDate register_time) {
        this.register_time = register_time;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getAdmin_enabled() {
        return admin_enabled;
    }

    public void setAdmin_enabled(Boolean admin_enabled) {
        this.admin_enabled = admin_enabled;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", admin_enabled=" + admin_enabled +
                ", address='" + address + '\'' +
                ", register_time=" + register_time +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", avatar_url='" + avatar_url + '\'' +
                '}';
    }
}

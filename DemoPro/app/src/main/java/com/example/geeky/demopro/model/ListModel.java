package com.example.geeky.demopro.model;

/**
 * Created by Sudarshan on 11/7/2016.
 */

public class ListModel {
    private String title;
    private String content;
    private String username;
    private String imgurl;
    private String phone;
    private String price;
    private String status;
    private String location;
    private String city;
    private int pid;


    public ListModel (String title, String content, String username, String imgurl, String phone,
                      String price, String status, String location, int pid, String city) {
        this.setTitle(title);
        this.setContent(content);
        this.setUsername(username);
        this.setImgurl(imgurl);
        this.setPhone(phone);
        this.setPrice(price);
        this.setStatus(status);
        this.setLocation(location);
        this.setPid(pid);
        this.setCity(city);

    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getUsername() {
        return username;
    }

    public String getImgurl() {
        return imgurl;
    }

    public String getPhone() {
        return phone;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

package com.example.wechat;

import java.io.Serializable;

public class UserBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id; // 菜品ID
    private String name; // 菜品名称
    private String sales; // 月售信息
    private String price; // 菜品价格
    private int img; // 菜品图片
    private String targetPage; // 目标页面标识符

    // 构造函数
    public UserBean(int id, String name, String sales, String price, int img, String targetPage) {
        this.id = id;
        this.name = name;
        this.sales = sales;
        this.price = price;
        this.img = img;
        this.targetPage = targetPage;
    }


    // 获取目标页面标识符
    public String getTargetPage() {
        return targetPage;
    }

    // 设置目标页面标识符
    public void setTargetPage(String targetPage) {
        this.targetPage = targetPage;
    }

    // 其他 getter 和 setter 方法
    // ...

    // 获取菜品ID
    public int getId() {
        return id;
    }

    // 设置菜品ID
    public void setId(int id) {
        this.id = id;
    }

    // 其他 getter 和 setter 方法
    // ...
//菜品图片
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}


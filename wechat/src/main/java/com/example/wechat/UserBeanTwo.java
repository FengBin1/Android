package com.example.wechat;

public class UserBeanTwo {
    private int id; // 菜品ID
    private String name; // 菜品名称
    private int img; // 菜品图片
    private String targetPage; // 目标页面标识符

    // 构造函数
    public UserBeanTwo(int id, String name, int img, String targetPage) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.targetPage = targetPage;
    }

    // Getter 和 Setter 方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTargetPage() {
        return targetPage;
    }

    public void setTargetPage(String targetPage) {
        this.targetPage = targetPage;
    }
}

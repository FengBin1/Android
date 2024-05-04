package com.example.wechat;

public class ChatMessage {
    private String content;
    private String time;
    private String senderName;
    private int id;
    private int img; // 新添加的img属性

    public ChatMessage(String content, String time, int id, String senderName, int img) {
        this.content = content;
        this.time = time;
        this.id = id;
        this.senderName = senderName;
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getSenderName() {
        return senderName;
    }
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public int getSenderId() {
        return id;
    }

    public void setSenderId(int id) {
        this.id = id;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}

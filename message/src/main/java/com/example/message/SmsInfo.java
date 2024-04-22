package com.example.message;

public class SmsInfo {
    private int _id;			 // 短信的主键
    private String address; // 发送地址
    private String body;     // 短信内容
    // 构造方法
    public SmsInfo(int _id, String address,String body) {
        this._id = _id;
        this.address = address;
        this.body = body;
    }
    public int get_id() {
        return _id;
    }
    public void set_id(int _id) {
        this._id = _id;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
}

package com.example.page;

import java.io.Serializable;

    public class ChatBean implements Serializable {
        private static final long serialVersionUID = 1L;

        private String text; // 菜品名称
        private int img; // 菜品图片
        private int senderId;

        // 构造函数
        public ChatBean(String text,int img) {
            this.text = text;
            this.img = img;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getImg() {
            return img;
        }

        public void setImg(int img) {
            this.img = img;
        }

        public int getSenderId() {
            return senderId;
        }

        public void setSenderId(int senderId) {
            this.senderId = senderId;
        }
}

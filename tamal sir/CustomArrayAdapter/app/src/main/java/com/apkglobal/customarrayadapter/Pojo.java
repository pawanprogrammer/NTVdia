package com.apkglobal.customarrayadapter;

public class Pojo {

    String title, body;
    int image;

    public Pojo(String title, String body, int image) {
        this.title = title;
        this.body = body;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public int getImage() {
        return image;
    }
}

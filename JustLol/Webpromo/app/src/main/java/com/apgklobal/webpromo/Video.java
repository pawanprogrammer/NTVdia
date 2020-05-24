package com.apgklobal.webpromo;

public class Video {
    String title, code;

    public Video(String title, String code) {
        this.title = title;
        this.code = code;
    }
    public Video()
    { }

    public String getTitle() {
        return title;
    }

    public String getCode() {
        return code;
    }
}

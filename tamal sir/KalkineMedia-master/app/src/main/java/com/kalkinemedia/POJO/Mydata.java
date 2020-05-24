package com.kalkinemedia.POJO;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Mydata implements Parcelable {
    @SerializedName("ID")
    private String id;
    @SerializedName("post_title")
    private String post_title;
    @SerializedName("post_date")
    private String post_date;
    @SerializedName("post_author")
    private String post_author;
    @SerializedName("img_link")
    private String img_link;
    @SerializedName("news_link")
    private String news_link;
    @SerializedName("post_content")
    private String post_content;

    protected Mydata(Parcel in) {
        id = in.readString();
        post_title = in.readString();
        post_date = in.readString();
        post_author = in.readString();
        img_link = in.readString();
        news_link = in.readString();
        post_content = in.readString();
        post_titles = in.createStringArray();
        post_dates = in.createStringArray();
        post_authors = in.createStringArray();
        img_links = in.createStringArray();
        post_contents = in.createStringArray();
        news_links = in.createStringArray();
    }

    public static final Creator<Mydata> CREATOR = new Creator<Mydata>() {
        @Override
        public Mydata createFromParcel(Parcel in) {
            return new Mydata(in);
        }

        @Override
        public Mydata[] newArray(int size) {
            return new Mydata[size];
        }
    };

    public Mydata(String[] post_title, String[] post_by, String[] post_date, String[] post_content, String[] post_image) {
        this.post_titles = post_title;
        this.post_authors = post_by;
        this.post_dates = post_date;
        this.post_contents = post_content;
        this.img_links = post_image;
    }

    public String[] getPost_titles() {
        return post_titles;
    }

    public String[] getPost_dates() {
        return post_dates;
    }

    public String[] getPost_authors() {
        return post_authors;
    }

    public String[] getImg_links() {
        return img_links;
    }

    public String[] getPost_contents() {
        return post_contents;
    }

    public String[] getNews_links() {
        return news_links;
    }

    private String [] post_titles;
    private String [] post_dates;
    private String [] post_authors;
    private String [] img_links;
    private String [] post_contents;
    private String [] news_links;

    public Mydata(String id, String post_title, String post_date, String post_author, String img_link, String news_link, String post_content) {
        this.id = id;
        this.post_title = post_title;
        this.post_date = post_date;
        this.post_author = post_author;
        this.img_link = img_link;
        this.news_link = news_link;
        this.post_content = post_content;
    }

    public String getId() {
        return id;
    }

    public String getPost_title() {
        return post_title;
    }

    public String getPost_date() {
        return post_date;
    }

    public String getPost_author() {
        return post_author;
    }

    public String getImg_link() {
        return img_link;
    }

    public String getNews_link() {
        return news_link;
    }

    public String getPost_content() {
        return post_content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(post_title);
        dest.writeString(post_date);
        dest.writeString(post_author);
        dest.writeString(img_link);
        dest.writeString(news_link);
        dest.writeString(post_content);
        dest.writeStringArray(post_titles);
        dest.writeStringArray(post_dates);
        dest.writeStringArray(post_authors);
        dest.writeStringArray(img_links);
        dest.writeStringArray(post_contents);
        dest.writeStringArray(news_links);
    }
}
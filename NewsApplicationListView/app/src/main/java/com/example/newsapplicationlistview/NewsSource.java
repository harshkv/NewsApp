package com.example.newsapplicationlistview;

import java.io.Serializable;

public class NewsSource implements Serializable {
    String imageURL;
    String author;
    String title;
    String publishedAt;
    String webURL;

    public NewsSource() {
        this.imageURL = imageURL;
        this.author = author;
        this.title = title;
        this.publishedAt = publishedAt;
        this.webURL = webURL;
    }

    @Override
    public String toString() {
        return "NewsSource{" +
                "imageURL='" + imageURL + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", webURL='" + webURL + '\'' +
                '}';
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getWebURL() {
        return webURL;
    }

    public void setWebURL(String webURL) {
        this.webURL = webURL;
    }
}

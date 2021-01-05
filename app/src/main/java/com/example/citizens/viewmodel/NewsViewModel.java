package com.example.citizens.viewmodel;

import android.media.Image;

public class NewsViewModel {
    private String title;
    private Image cover;
    private String URL;

    public NewsViewModel(String title, Image cover, String URL) {
        this.title = title;
        this.cover = cover;
        this.URL = URL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Image getCover() {
        return cover;
    }

    public void setCover(Image cover) {
        this.cover = cover;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}

package com.example.citizens.viewmodel;

import android.media.Image;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

public class NewsViewModel {
    private String index;
    private String title;
    private String coverURL;
    private String newsURL;
    private String date;

    public NewsViewModel(String index, String title, String coverURL, String newsURL, String date) {
        this.index = index;
        this.title = title;
        this.coverURL = coverURL;
        this.newsURL = newsURL;
        this.date = date;
    }

    public NewsViewModel(JSONObject object) throws JSONException {
        this.index = object.getString("id");
        this.title = object.getString("title");
        this.coverURL = object.getString("cover_img_url");
        this.newsURL = object.getString("mobile_url");
        this.date = object.getString("date");
    }

    public JSONObject toJSONObject() throws JSONException {
        JSONObject object = new JSONObject();

        object.put("id", this.index);
        object.put("title", this.title);
        object.put("cover_img_url", this.coverURL);
        object.put("mobile_url", this.newsURL);
        object.put("date", this.date);
        return object;
    }

    @Override
    public int hashCode() {
        return index.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof NewsViewModel)) {
            return false;
        }
        NewsViewModel rhs = (NewsViewModel) obj;
        return this.index.equals(rhs.index);
    }

    public String getDate() {
        return date;
    }

    public String getIndex() {
        return index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverURL() {
        return coverURL;
    }

    public void setCoverURL(String coverURL) {
        this.coverURL = coverURL;
    }

    public String getNewsURL() {
        return newsURL;
    }

    public void setNewsURL(String newsURL) {
        this.newsURL = newsURL;
    }
}

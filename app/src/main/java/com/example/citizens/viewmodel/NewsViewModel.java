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
    private String createTime;
    private String updateTime;

    private String labels;

    public NewsViewModel(String index, String title, String coverURL, String newsURL, String date,
                         String createTime, String updateTime, String labels) {
        this.index = index;
        this.title = title;
        this.coverURL = coverURL;
        this.newsURL = newsURL;
        this.date = date;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.labels = labels;
    }

    public NewsViewModel(JSONObject object) throws JSONException {
        this.index = object.getString("id");
        this.title = object.getString("title");
        this.coverURL = object.getString("cover_img_url");
        this.newsURL = object.getString("mobile_url");
        this.date = object.getString("date");
        this.createTime = object.getString("news_create_time");
        this.updateTime = object.getString("news_update_time");
        this.labels = object.getString("labels");
    }

    public JSONObject toJSONObject() throws JSONException {
        JSONObject object = new JSONObject();

        object.put("id", this.index);
        object.put("title", this.title);
        object.put("cover_img_url", this.coverURL);
        object.put("mobile_url", this.newsURL);
        object.put("date", this.date);
        object.put("news_create_time", this.createTime);
        object.put("news_update_time", this.updateTime);
        object.put("labels", this.labels);
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

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String time) {
        this.createTime = time;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String time) {
        this.updateTime = time;
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

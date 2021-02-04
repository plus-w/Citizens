package com.example.citizens.utils;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.JsonReader;
import android.util.JsonToken;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.example.citizens.adapter.NewsRecyclerViewAdapter;
import com.example.citizens.viewmodel.NewsViewModel;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class NetworkPort {
    private static NetworkPort instance;



    private Handler handler = null;
    private NetworkPort() {}

    public static synchronized NetworkPort getInstance() {
        if (instance == null) {
            instance = new NetworkPort();
        }
        return instance;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void getNews(Context context, NewsRecyclerViewAdapter adapter, SwipyRefreshLayout swipyRefreshLayout, String date) {
        RequestQueue requestQueue = MySingleton.getInstance(context).getRequestQueue();

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(currentDate);

        if (date != null) {
            Date lastDate = null;
            try {
                lastDate = new Date(dateFormat.parse(date).getTime() - 24*60*60*1000);
                dateString = dateFormat.format(lastDate);
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(context, "加载错误", Toast.LENGTH_SHORT).show();
            }
        }
        Toast.makeText(context, dateString, Toast.LENGTH_SHORT).show();
        String url = "http://139.180.188.157:5000/news/" + dateString;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    List<NewsViewModel> newsList = new ArrayList<NewsViewModel>();

                    JSONArray newsJSONArray = jsonObject.getJSONArray("data");

                    for (int i = 0; i < newsJSONArray.length(); i++) {
                        JSONObject object = (JSONObject) newsJSONArray.get(i);
//                        MySingleton.getInstance(context).getImageLoader();
                        newsList.add(new NewsViewModel(
                                object.getString("id"),
                                object.getString("title"),
                                object.getString("cover_img_url"),
                                object.getString("mobile_url"),
                                object.getString("date")));
                    }

                    adapter.updateNewsList(newsList);
                    adapter.notifyDataSetChanged();
//                    Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(context, "网络数据错误", Toast.LENGTH_SHORT).show();
                } finally {
                    if(swipyRefreshLayout.isRefreshing()) {
                        swipyRefreshLayout.setRefreshing(false);
                    }
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                if(swipyRefreshLayout.isRefreshing()) {
                    swipyRefreshLayout.setRefreshing(false);
                }
                Toast.makeText(context, "网络错误", Toast.LENGTH_LONG).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20*1000, 1, 1.0f));
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

}

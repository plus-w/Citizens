package com.example.citizens.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
import com.example.citizens.R;
import com.example.citizens.adapter.MatchRecyclerViewAdapter;
import com.example.citizens.adapter.NewsRecyclerViewAdapter;
import com.example.citizens.fragment.DataFragment;
import com.example.citizens.fragment.EFLDataFragment;
import com.example.citizens.fragment.EPLDataFragment;
import com.example.citizens.fragment.FADataFragment;
import com.example.citizens.fragment.OverviewDataFragment;
import com.example.citizens.fragment.UCLDataFragment;
import com.example.citizens.viewmodel.MatchViewModel;
import com.example.citizens.viewmodel.NewsViewModel;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

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
import java.util.logging.Level;
import java.util.logging.Logger;

public class NetworkPort {
    private static NetworkPort instance;

    public static final Integer appPort = 5000;
    public static final String serverIP = "139.180.188.157";


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

    public void getStatistics(Context context, DataFragment dataFragment) {
        RequestQueue requestQueue = MySingleton.getInstance(context).getRequestQueue();
        String url = "http://" + serverIP + ":" + appPort + "/statistics";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONObject statJSONObject = jsonObject.getJSONObject("data");
                    dataFragment.updateStatisticsData(statJSONObject);

                    SharedPreferences.Editor editor= context.getSharedPreferences("cache", Context.MODE_PRIVATE).edit();
                    editor.putString("statistics_json_object", statJSONObject.toString());
                    editor.apply();

//                    Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "网络数据错误", Toast.LENGTH_SHORT).show();
                } finally {
//                    if(swipyRefreshLayout.isRefreshing()) {
//                        swipyRefreshLayout.setRefreshing(false);
//                    }
//                    Toast.makeText(context, "数据已更新", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
//                if(swipyRefreshLayout.isRefreshing()) {
//                    swipyRefreshLayout.setRefreshing(false);
//                }
                Toast.makeText(context, "网络错误", Toast.LENGTH_LONG).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20*1000, 1, 1.0f));
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    public void getMatchSchedule(Context context, MatchRecyclerViewAdapter adapter, SwipyRefreshLayout swipyRefreshLayout, String teamID) {
        RequestQueue requestQueue = MySingleton.getInstance(context).getRequestQueue();

        String url = "http://" + serverIP + ":" + appPort + "/match/" + teamID;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    List<MatchViewModel> matchList = new ArrayList<MatchViewModel>();

                    JSONArray matchJSONArray = jsonObject.getJSONArray("data");

                    for (int i = 0; i < matchJSONArray.length(); i++) {
                        JSONObject object = (JSONObject) matchJSONArray.get(i);
//                        MySingleton.getInstance(context).getImageLoader();
                        matchList.add(new MatchViewModel(
                                object.getString("match_id"),
                                object.getString("league"),
                                object.getString("type"), object.getString("round"),
                                object.getString("date"), object.getString("time"),
                                object.getString("home_name"), object.getString("away_name"),
                                object.getString("home_score"), object.getString("away_score"),
                                object.getString("home_logo_url"), object.getString("away_logo_url")));
                    }

                    SharedPreferences.Editor editor= context.getSharedPreferences("cache", Context.MODE_PRIVATE).edit();
                    editor.putString("match_json_array", matchJSONArray.toString());
                    editor.apply();

                    adapter.updateMatchSchedule(matchList);
                    adapter.notifyDataSetChanged();
//                    Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "网络数据错误", Toast.LENGTH_SHORT).show();
                } finally {
                    if(swipyRefreshLayout.isRefreshing()) {
                        swipyRefreshLayout.setRefreshing(false);
                    }
                    Toast.makeText(context, "数据已更新", Toast.LENGTH_LONG).show();
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
//        Toast.makeText(context, dateString, Toast.LENGTH_SHORT).show();
        String url = "http://139.180.188.157:5000/news/" + dateString;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    List<NewsViewModel> newsList = new ArrayList<NewsViewModel>();

                    // load news list from server data
                    JSONArray newsJSONArray = jsonObject.getJSONArray("data");

                    for (int i = 0; i < newsJSONArray.length(); i++) {
                        JSONObject object = (JSONObject) newsJSONArray.get(i);
//                        MySingleton.getInstance(context).getImageLoader();
                        newsList.add(new NewsViewModel(object));
                    }

                    //check cache not empty
                    SharedPreferences preferences = context.getSharedPreferences("cache", Context.MODE_PRIVATE);
                    String newsJSONArrayCacheString = preferences.getString("news_json_array", "");

                    if (!newsJSONArrayCacheString.isEmpty()) {
                        JSONArray newsJSONArrayCache = new JSONArray(newsJSONArrayCacheString);
                        for (int i = 0; i < newsJSONArrayCache.length(); i++) {
                            JSONObject object = (JSONObject) newsJSONArrayCache.get(i);
                            newsList.add(new NewsViewModel(object));
                        }
                    }

                    adapter.updateNewsList(newsList);
                    adapter.notifyDataSetChanged();

                    //cache current news list
                    List<NewsViewModel> currentNewsList = adapter.getCurrentData();
                    JSONArray currentJSONArray = new JSONArray();
                    for (int i = 0; i < currentNewsList.size(); i++) {
                        currentJSONArray.put(currentNewsList.get(i).toJSONObject());
                    }

                    SharedPreferences.Editor editor= context.getSharedPreferences("cache", Context.MODE_PRIVATE).edit();
                    editor.putString("news_json_array", currentJSONArray.toString());
                    editor.apply();

//                    adapter.notifyDataSetChanged();
//                    Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "网络数据错误", Toast.LENGTH_SHORT).show();
                } finally {
                    if(swipyRefreshLayout.isRefreshing()) {
                        swipyRefreshLayout.setRefreshing(false);
                    }
                    Toast.makeText(context, "数据已更新", Toast.LENGTH_SHORT).show();
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

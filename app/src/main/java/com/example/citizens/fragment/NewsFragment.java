package com.example.citizens.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.citizens.R;
import com.example.citizens.activity.WebViewActivity;
import com.example.citizens.adapter.NewsRecyclerViewAdapter;
import com.example.citizens.utils.NetworkPort;
import com.example.citizens.viewmodel.NewsViewModel;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment implements NewsRecyclerViewAdapter.ItemClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//    private String mParam1;

    private RecyclerView recyclerView;
    private NewsRecyclerViewAdapter newsRecyclerViewAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
//    private SwipeRefreshLayout swipeRefreshLayoutNews;
    private SwipyRefreshLayout swipeRefreshLayoutNews;

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public NewsRecyclerViewAdapter getNewsRecyclerViewAdapter() {
        return newsRecyclerViewAdapter;
    }

    public RecyclerView.LayoutManager getRecyclerViewLayoutManager() {
        return recyclerViewLayoutManager;
    }

    public SwipyRefreshLayout getSwipeRefreshLayoutNews() {
        return swipeRefreshLayoutNews;
    }

    public NewsFragment() {
        // Required empty public constructor
    }

//    public SwipeRefreshLayout getSwipeRefreshLayoutNews() {
//        return mSwipeRefreshLayoutNews;
//    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
//     * @param param1 Parameter 1.
     * @return A new instance of fragment NewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//        }
        List<NewsViewModel> newsList = new ArrayList<NewsViewModel>();
        newsRecyclerViewAdapter = new NewsRecyclerViewAdapter(getContext(), newsList);
        newsRecyclerViewAdapter.setClickListener(this);
        recyclerViewLayoutManager = new LinearLayoutManager(getActivity());

        SharedPreferences preferences = getActivity().getApplicationContext().getSharedPreferences("cache", Context.MODE_PRIVATE);
        String newsJSONArrayString = preferences.getString("news_json_array", "");

        if (!newsJSONArrayString.isEmpty()) {
            try{
                JSONArray newsJSONArray = new JSONArray(newsJSONArrayString);
                for (int i = 0; i < newsJSONArray.length(); i++) {
                    JSONObject object = (JSONObject) newsJSONArray.get(i);
                    newsList.add(new NewsViewModel(
                            object.getString("id"),
                            object.getString("title"),
                            object.getString("cover_img_url"),
                            object.getString("mobile_url"),
                            object.getString("date")));
                }
                newsRecyclerViewAdapter.updateNewsList(newsList);
                newsRecyclerViewAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                Toast.makeText(getContext(), "数据预加载失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_news);
//        mRecyclerView.setHasFixedSize(true);  // fix item size to improve performance

        swipeRefreshLayoutNews = (SwipyRefreshLayout) view.findViewById(R.id.swiperefresh_news);
        swipeRefreshLayoutNews.setColorSchemeResources(R.color.blue, R.color.sky_blue, R.color.light_gold, R.color.red);
        swipeRefreshLayoutNews.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                swipeRefreshLayoutNews.setRefreshing(true);
                String date = null;
                if (direction == SwipyRefreshLayoutDirection.BOTTOM) {
                    if (newsRecyclerViewAdapter.getItemCount() > 0) {
                        date = newsRecyclerViewAdapter.getItem(newsRecyclerViewAdapter.getItemCount() - 1).getDate();
                    } else {
                        Date currentDate = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        date = dateFormat.format(currentDate);
                    }
                }
                NetworkPort.getInstance().getNews(
                        getActivity().getApplicationContext(),
                        newsRecyclerViewAdapter,
                        swipeRefreshLayoutNews,
                        date);

//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        NetworkPort.getInstance().getNews(getActivity().getApplicationContext(), newsRecyclerViewAdapter);
////                        newsRecyclerViewAdapter.updateData(direction, getActivity().getApplicationContext());
//                        if(swipeRefreshLayoutNews.isRefreshing()) {
//                            swipeRefreshLayoutNews.setRefreshing(false);
//                        }
//                        newsRecyclerViewAdapter.notifyDataSetChanged();
//                    }
//                }, 1000);

            }
        });

        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.setAdapter(newsRecyclerViewAdapter);



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        Toast.makeText(getContext(), "onResume", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent newsIntent = new Intent(getActivity(), WebViewActivity.class);
        newsIntent.putExtra("URL", newsRecyclerViewAdapter.getItem(position).getNewsURL());
        startActivity(newsIntent);
//        Toast.makeText(getActivity(), newsRecyclerViewAdapter.getItem(position).getTitle() + " Clicked", Toast.LENGTH_SHORT).show();
    }
}
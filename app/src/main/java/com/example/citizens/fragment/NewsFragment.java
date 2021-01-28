package com.example.citizens.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.citizens.R;
import com.example.citizens.activity.NewsWebViewActivity;
import com.example.citizens.adapter.NewsRecyclerViewAdapter;
import com.example.citizens.viewmodel.NewsViewModel;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
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
        List<NewsViewModel> data = new ArrayList<NewsViewModel>();
//        for (int i = 0; i < 10; i++) {
//            NewsViewModel newsViewModel = new NewsViewModel("新闻-" + (10 - i), null, "www.bing.com");
//            data.add(newsViewModel);
//        }

        newsRecyclerViewAdapter = new NewsRecyclerViewAdapter(data);
        newsRecyclerViewAdapter.setClickListener(this);
        recyclerViewLayoutManager = new LinearLayoutManager(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_news);
//        mRecyclerView.setHasFixedSize(true);  // fix item size to improve performance

        swipeRefreshLayoutNews = (SwipyRefreshLayout) view.findViewById(R.id.swiperefresh_news);
        swipeRefreshLayoutNews.setColorSchemeResources(R.color.blue, R.color.sky_blue);
        swipeRefreshLayoutNews.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                swipeRefreshLayoutNews.setRefreshing(true);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        newsRecyclerViewAdapter.updateData(direction);
                        if(swipeRefreshLayoutNews.isRefreshing()) {
                            swipeRefreshLayoutNews.setRefreshing(false);
                        }
                    }
                }, 1000);
            }
        });

        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.setAdapter(newsRecyclerViewAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent newsIntent = new Intent(getActivity(), NewsWebViewActivity.class);
        newsIntent.putExtra("URL", "https://m.zhibo8.cc/news/web/zuqiu/2021-01-27/601079e34755d.htm");
        startActivity(newsIntent);
//        Toast.makeText(getActivity(), newsRecyclerViewAdapter.getItem(position).getTitle() + " Clicked", Toast.LENGTH_SHORT).show();
    }
}
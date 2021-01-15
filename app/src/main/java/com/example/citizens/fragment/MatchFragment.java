package com.example.citizens.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.citizens.R;
import com.example.citizens.adapter.MatchRecyclerViewAdapter;
import com.example.citizens.viewmodel.MatchViewModel;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MatchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MatchFragment extends Fragment implements MatchRecyclerViewAdapter.ItemClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;

    private RecyclerView recyclerView;
    private MatchRecyclerViewAdapter matchRecyclerViewAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
//    private SwipeRefreshLayout  swipeRefreshLayoutMatch;
    private SwipyRefreshLayout swipeRefreshLayoutMatch;

    public MatchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
//     * @param param1 Parameter 1.
     * @return A new instance of fragment MatchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MatchFragment newInstance() {
        MatchFragment fragment = new MatchFragment();
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

        List<MatchViewModel> data = new ArrayList<MatchViewModel>();

        matchRecyclerViewAdapter = new MatchRecyclerViewAdapter(data);
        matchRecyclerViewAdapter.setClickListener(this);
        recyclerViewLayoutManager = new LinearLayoutManager(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_match, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_match);

        swipeRefreshLayoutMatch = (SwipyRefreshLayout) view.findViewById(R.id.swiperefresh_match);
        swipeRefreshLayoutMatch.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                swipeRefreshLayoutMatch.setRefreshing(true);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        matchRecyclerViewAdapter.updateData(direction);
                        if(swipeRefreshLayoutMatch.isRefreshing()) {
                            swipeRefreshLayoutMatch.setRefreshing(false);
                        }
                    }
                }, 1000);
            }
        });

        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.setAdapter(matchRecyclerViewAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getActivity(), "Item Clicked", Toast.LENGTH_SHORT).show();
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public MatchRecyclerViewAdapter getMatchRecyclerViewAdapter() {
        return matchRecyclerViewAdapter;
    }

    public RecyclerView.LayoutManager getRecyclerViewLayoutManager() {
        return recyclerViewLayoutManager;
    }

    public SwipyRefreshLayout getSwipeRefreshLayoutMatch() {
        return swipeRefreshLayoutMatch;
    }
}
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
import com.example.citizens.adapter.MatchRecyclerViewAdapter;
import com.example.citizens.utils.NetworkPort;
import com.example.citizens.viewmodel.MatchViewModel;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    private RecyclerView matchRecyclerView;
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

        List<MatchViewModel> matchList = new ArrayList<MatchViewModel>();

        matchRecyclerViewAdapter = new MatchRecyclerViewAdapter(getContext(), matchList);
        matchRecyclerViewAdapter.setClickListener(this);
        recyclerViewLayoutManager = new LinearLayoutManager(getActivity());

        SharedPreferences preferences = getActivity().getApplicationContext().getSharedPreferences("cache", Context.MODE_PRIVATE);
        String matchJSONArrayString = preferences.getString("match_json_array", "");

        if (!matchJSONArrayString.isEmpty()) {
            try {
                JSONArray matchJSONArray = new JSONArray(matchJSONArrayString);

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
                            object.getString("home_logo_url"), object.getString("away_logo_url"),
                            object.getString("match_live_url")));
                }
                matchRecyclerViewAdapter.updateMatchSchedule(matchList);
                matchRecyclerViewAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                Toast.makeText(getContext(), "数据预加载失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_match, container, false);
        matchRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_match);

        swipeRefreshLayoutMatch = (SwipyRefreshLayout) view.findViewById(R.id.swiperefresh_match);
        swipeRefreshLayoutMatch.setColorSchemeResources(R.color.blue, R.color.sky_blue, R.color.light_gold, R.color.red);
        swipeRefreshLayoutMatch.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                swipeRefreshLayoutMatch.setRefreshing(true);
                NetworkPort.getInstance().getMatchSchedule(
                        getActivity().getApplicationContext(),
                        matchRecyclerViewAdapter,
                        swipeRefreshLayoutMatch,
                        "139");
            }
        });

        matchRecyclerView.setLayoutManager(recyclerViewLayoutManager);
        matchRecyclerView.setAdapter(matchRecyclerViewAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onItemClick(View view, int position) {
//        Toast.makeText(getActivity(), "Item Clicked", Toast.LENGTH_SHORT).show();
        Intent matchIntent = new Intent(getActivity(), WebViewActivity.class);
        matchIntent.putExtra("URL", matchRecyclerViewAdapter.getItem(position).getMatchLiveURL());
        startActivity(matchIntent);
    }

    public RecyclerView getMatchRecyclerView() {
        return matchRecyclerView;
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
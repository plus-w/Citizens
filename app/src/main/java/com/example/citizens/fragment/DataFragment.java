package com.example.citizens.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.citizens.R;
import com.example.citizens.adapter.ViewPagerAdapter;
import com.example.citizens.utils.MyViewPager;
import com.example.citizens.utils.NetworkPort;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DataFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    public SwipyRefreshLayout getSwipeRefreshLayoutData() {
        return swipeRefreshLayoutData;
    }

    private SwipyRefreshLayout swipeRefreshLayoutData;

    private MyViewPager mViewPager;
    private RadioGroup mRadioGroup;
//    private SwipyRefreshLayout mSwipyRefreshLayout;

    final private OverviewDataFragment mOverviewDataFragment = OverviewDataFragment.newInstance();
    final private EPLDataFragment mEPLDataFragment = EPLDataFragment.newInstance();
    final private UCLDataFragment mUCLDataFragment = UCLDataFragment.newInstance();
    final private FADataFragment mFADataFragment = FADataFragment.newInstance();
    final private EFLDataFragment mEFLDataFragment = EFLDataFragment.newInstance();

    private List<Integer> mRadioButtonID = new ArrayList<Integer>();
    private RadioButton mOverviewRadioButton;
    private RadioButton mEPLRadioButton;
    private RadioButton mUCLRadioButton;
    private RadioButton mFARadioButton;
    private RadioButton mEFLRadioButton;

    private Boolean mDataLoaded = false;
//
//    private RadioButton mPrevRadioButton;
    Integer currentPosition = -1;

    private FragmentManager mFragmentManager;

    public DataFragment() {
        // Required empty public constructor
    }

    public DataFragment(FragmentManager fm) {
        // Required empty public constructor
        mFragmentManager = fm;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
//     * @param param1 Parameter 1.
     * @return A new instance of fragment DataFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DataFragment newInstance(FragmentManager fm) {
        DataFragment fragment = new DataFragment(fm);
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data, container, false);

//        mSwipyRefreshLayout = view.findViewById(R.id.swiperefresh_data);
//        mSwipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh(SwipyRefreshLayoutDirection direction) {
//                mSwipyRefreshLayout.setRefreshing(true);
//                NetworkPort.getInstance().getStatistics(getActivity().getApplicationContext(), mSwipyRefreshLayout, DataFragment.this);
//
//            }
//        });
        DataFragment currentInstance = this;
        swipeRefreshLayoutData = (SwipyRefreshLayout) view.findViewById(R.id.swiperefresh_data);
        swipeRefreshLayoutData.setColorSchemeResources(R.color.blue, R.color.sky_blue, R.color.light_gold, R.color.red);
        swipeRefreshLayoutData.setOnRefreshListener(null);
        swipeRefreshLayoutData.setEnabled(false);

        mOverviewRadioButton = (RadioButton) view.findViewById(R.id.radio_button_overview);
        mEPLRadioButton = (RadioButton) view.findViewById(R.id.radio_button_epl);
        mUCLRadioButton = (RadioButton) view.findViewById(R.id.radio_button_ucl);
        mFARadioButton = (RadioButton) view.findViewById(R.id.radio_button_fa);
        mEFLRadioButton = (RadioButton) view.findViewById(R.id.radio_button_efl);

        mRadioButtonID.add(R.id.radio_button_overview);
        mRadioButtonID.add(R.id.radio_button_epl);
        mRadioButtonID.add(R.id.radio_button_ucl);
        mRadioButtonID.add(R.id.radio_button_fa);
        mRadioButtonID.add(R.id.radio_button_efl);

        mViewPager = view.findViewById(R.id.view_pager_data);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
//                if (mPrevRadioButton != null) {
//                    mPrevRadioButton.setChecked(false);
//                } else {
//                    mRadioGroup.check(R.id.radio_button_overview);
//
//                    mBottomNavigationView.getMenu().getItem(0).setChecked(false);
//                }
                mRadioGroup.check(mRadioButtonID.get(position));
                currentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        ViewPagerAdapter viewpagerAdapter = new ViewPagerAdapter(mFragmentManager);
        viewpagerAdapter.addFragment(mOverviewDataFragment);
        viewpagerAdapter.addFragment(mEPLDataFragment);
        viewpagerAdapter.addFragment(mUCLDataFragment);
        viewpagerAdapter.addFragment(mFADataFragment);
        viewpagerAdapter.addFragment(mEFLDataFragment);

        mRadioGroup = view.findViewById(R.id.radio_group_data);
        mRadioGroup.check(R.id.radio_button_overview);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_button_overview:
                        mViewPager.setCurrentItem(0, false);
                        break;
                    case R.id.radio_button_epl:
                        mViewPager.setCurrentItem(1, false);
                        break;
                    case R.id.radio_button_ucl:
                        mViewPager.setCurrentItem(2, false);
                        break;
                    case R.id.radio_button_fa:
                        mViewPager.setCurrentItem(3, false);
                        break;
                    case R.id.radio_button_efl:
                        mViewPager.setCurrentItem(4, false);
                        break;
                }
            }
        });
        mViewPager.setAdapter(viewpagerAdapter);
        return view;
    }

    public void loadDataFromCache() {
        SharedPreferences preferences = getActivity().getApplicationContext().getSharedPreferences("cache", Context.MODE_PRIVATE);
        String statisticsJSONObjectString = preferences.getString("statistics_json_object", "");
        if (!statisticsJSONObjectString.isEmpty()) {
            try {
                this.updateStatisticsData(new JSONObject(statisticsJSONObjectString));
                this.mDataLoaded = true;
            } catch (JSONException e) {
                Toast.makeText(getContext(), "数据预加载失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void updateStatisticsData(JSONObject statJSONObject) {
        try {
            JSONObject allJSONObject = statJSONObject.getJSONObject("all");
            mOverviewDataFragment.setPlayerTableData(allJSONObject.getJSONArray("player"));

            JSONObject eplJSONObject = statJSONObject.getJSONObject("epl");
            mEPLDataFragment.setTeamTableData(eplJSONObject.getJSONArray("team"));
            mEPLDataFragment.setPlayerTableData(eplJSONObject.getJSONArray("player"));

            JSONObject uclJSONObject = statJSONObject.getJSONObject("ucl");
            mUCLDataFragment.setTeamTableData(uclJSONObject.getJSONArray("team"));
            mUCLDataFragment.setPlayerTableData(uclJSONObject.getJSONArray("player"));

            JSONObject faJSONObject = statJSONObject.getJSONObject("fa");
            mFADataFragment.setTeamStatTableData(faJSONObject.getJSONArray("team"));
            mFADataFragment.setPlayerStatTableData(faJSONObject.getJSONArray("player"));

            JSONObject eflJSONObject = statJSONObject.getJSONObject("efl");
            mEFLDataFragment.setTeamStatTableData(eflJSONObject.getJSONArray("team"));
            mEFLDataFragment.setPlayerStatTableData(eflJSONObject.getJSONArray("player"));
        } catch (JSONException e) {
            Toast.makeText(getContext(), "数据错误", Toast.LENGTH_SHORT).show();
        }
    }

//    public SwipyRefreshLayout getSwipeRefreshLayoutData() {
//        return mSwipyRefreshLayout;
//    }

    public Boolean isDataLoaded() {
        return mDataLoaded;
    }

    public void setDataLoadedFlag(Boolean mDataLoaded) {
        this.mDataLoaded = mDataLoaded;
    }

}
package com.example.citizens.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.citizens.R;
import com.example.citizens.adapter.ViewPagerAdapter;
import com.example.citizens.utils.MyViewPager;

import java.lang.reflect.Array;
import java.util.ArrayList;
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

    private MyViewPager mViewPager;
    private RadioGroup mRadioGroup;
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
}
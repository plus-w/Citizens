package com.example.citizens.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.citizens.R;
import com.example.citizens.adapter.ViewPagerAdapter;
import com.example.citizens.fragment.MatchFragment;
import com.example.citizens.fragment.NewsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    final private NewsFragment mNewsFragment = NewsFragment.newInstance("新闻");
    final private MatchFragment mMatchFragment = MatchFragment.newInstance("赛况");
    final private MatchFragment mDataFragment = MatchFragment.newInstance("数据");
    final private MatchFragment mInfoFragment = MatchFragment.newInstance("资料");
    final private FragmentManager mFragmentManager = getSupportFragmentManager();
    private FloatingActionButton mFAB;
    private BottomNavigationView mBottomNavigationView;
    private ViewPager mViewPager;

    MenuItem mPrevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setTitleTextColor(getColor(R.color.white));

        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);

        mViewPager = findViewById(R.id.view_pager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                if (mPrevMenuItem != null) {
                    mPrevMenuItem.setChecked(false);
                } else {
                    mBottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                mBottomNavigationView.getMenu().getItem(position).setChecked(true);
                mPrevMenuItem = mBottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        ViewPagerAdapter viewpagerAdapter = new ViewPagerAdapter(mFragmentManager);
        viewpagerAdapter.addFragment(mNewsFragment);
        viewpagerAdapter.addFragment(mMatchFragment);
        viewpagerAdapter.addFragment(mDataFragment);
        viewpagerAdapter.addFragment(mInfoFragment);
        mViewPager.setAdapter(viewpagerAdapter);

        mFAB = findViewById(R.id.fab_refresh);
        mFAB.setOnClickListener(new FloatingActionButton.OnClickListener(){

            @Override
            public void onClick(View v) {
                RotateAnimation rotateAnimation = new RotateAnimation(0, 360f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);

                rotateAnimation.setInterpolator(new LinearInterpolator());
                rotateAnimation.setDuration(500);
                rotateAnimation.setRepeatCount(2);

                findViewById(R.id.fab_refresh).startAnimation(rotateAnimation);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint(getString(R.string.search_hint_zh));
        ImageView icon = searchView.findViewById(androidx.appcompat.R.id.search_button);
        icon.setImageResource(R.drawable.baseline_search_white_24);
//        icon.setColorFilter(getColor(R.color.blue));
        searchView.setOnSearchClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Search", Toast.LENGTH_SHORT).show();
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Toast.makeText(MainActivity.this, "Close search", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("Query text finish: " + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("Query text change:" + newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_news:
//                        Toast.makeText(MainActivity.this, "新闻", Toast.LENGTH_SHORT).show();
//                mFragmentManager.beginTransaction().hide(mActiveFragment).show(mNewsFragment).commit();
//                mActiveFragment = mNewsFragment;
//                mViewPager
                mViewPager.setCurrentItem(0, false);
                break;
            case R.id.action_match:
//                        Toast.makeText(MainActivity.this, "赛况", Toast.LENGTH_SHORT).show();
//                mFragmentManager.beginTransaction().hide(mActiveFragment).show(mMatchFragment).commit();
//                mActiveFragment = mMatchFragment;
                mViewPager.setCurrentItem(1, false);
                break;
            case R.id.action_data:
//                        Toast.makeText(MainActivity.this, "资料", Toast.LENGTH_SHORT).show();
//                mFragmentManager.beginTransaction().hide(mActiveFragment).show(mDataFragment).commit();
//                mActiveFragment = mDataFragment;
                mViewPager.setCurrentItem(2, false);
                break;
            case R.id.action_info:
//                        Toast.makeText(MainActivity.this, "资料", Toast.LENGTH_SHORT).show();
//                mFragmentManager.beginTransaction().hide(mActiveFragment).show(mInfoFragment).commit();
//                mActiveFragment = mInfoFragment;
                mViewPager.setCurrentItem(3, false);
                break;
        }
        return true;
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
////        Toast.makeText(MainActivity.this, "Back pressed", Toast.LENGTH_SHORT).show();
////        MainActivity.this.finish();
//    }
}
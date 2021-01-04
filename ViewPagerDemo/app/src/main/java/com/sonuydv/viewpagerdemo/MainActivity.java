package com.sonuydv.viewpagerdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private String[] msgList={"Tab1","Tab2","Tab3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init actionbar(toolbar)
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        //init tab layout
        TabLayout tabLayout=findViewById(R.id.tabLayout);

        //init views
        this.mViewPager=findViewById(R.id.viewPager);

        //init pageAdapter
        //behaviour how many fragment should be in resume state
        /*
        * By Default view pager horizotally behaviour added swiping left ritght will
        * change the pages
        * */
        mPagerAdapter=new PagerAdapter(getSupportFragmentManager(),msgList.length, Arrays.asList(msgList));
        mViewPager.setAdapter(mPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        //setting current page as first item of fragment which should be displayed by default
        mViewPager.setCurrentItem(0);

    }
}
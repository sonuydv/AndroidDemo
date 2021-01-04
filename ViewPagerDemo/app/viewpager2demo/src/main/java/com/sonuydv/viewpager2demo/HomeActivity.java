package com.sonuydv.viewpager2demo;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class HomeActivity extends AppCompatActivity   {

    private ViewPager2 mViewPager;
    private PagerAdapterRecyclerView mPagerAdapter;
    private PagerFragmentAdapter mFragmentAdapter;
    private List<String> mData;

    private ViewPager2.OnPageChangeCallback onPageChangeCallback=new ViewPager2.OnPageChangeCallback() {

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            showToast("Page Position : "+position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //init viewpager
        mViewPager=findViewById(R.id.viewPager);
        mData=getData();

        //init and setup viewpager with recycler view adapter
//        initAndSetupWithRecylerViewAdapter();

        //init and setup with fragmentAdapter to use fragement as pages
         initAndSetupWithFragmentAdapter();




    }



    //init and setup viewpager with fragment adapter to show fragment as pages
    private void initAndSetupWithFragmentAdapter(){
        mFragmentAdapter=new PagerFragmentAdapter(this,mData);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setCurrentItem(0);

        //Handling page change event callback
        mViewPager.registerOnPageChangeCallback(onPageChangeCallback);
    }

    //init Pager
    private void initAndSetupWithRecylerViewAdapter(){
        //init PagerAdapter(Adapters are basically used to provide pages or item views(with data binding) to the ViewPager
        //Or recyclerView
        mPagerAdapter=new PagerAdapterRecyclerView(this,mData,mViewPager);

        mViewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        mViewPager.setAdapter(mPagerAdapter);
    }
    private List<String> getData(){
        List<String> list = new ArrayList<>();
        list.add("First Screen");
        list.add("Second Screen");
        list.add("Third Screen");
        list.add("Fourth Screen");
        return list;
    }

    private void showToast(String msg){
        Toast.makeText(HomeActivity.this,msg,Toast.LENGTH_SHORT).show();
    }

}
package com.sonuydv.viewpagerdemo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {
    private List<String> mMsgList;

    public PagerAdapter(@NonNull FragmentManager fm, int behavior, List<String> msgList) {
        super(fm, behavior);
        this.mMsgList=msgList;
    }

    //Must return the item which should be displayed on the viewpager
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(mMsgList.get(position));
    }


    @Override
    public int getCount() {
        return mMsgList.size();
    }

    @Nullable

    //Must return the title of the position item
    @Override
    public CharSequence getPageTitle(int position) {
        return mMsgList.get(position);
    }
}

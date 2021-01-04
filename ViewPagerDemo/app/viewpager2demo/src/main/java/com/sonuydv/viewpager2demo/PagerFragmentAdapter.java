package com.sonuydv.viewpager2demo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PagerFragmentAdapter extends FragmentStateAdapter {
private List<String> mData;

    public PagerFragmentAdapter(@NonNull FragmentActivity fragmentActivity, List<String> data) {
        super(fragmentActivity);
        this.mData=data;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return Page.newInstance(mData.get(position),"");
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}

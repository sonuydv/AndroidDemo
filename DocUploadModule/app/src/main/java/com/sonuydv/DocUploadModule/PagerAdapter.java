package com.sonuydv.DocUploadModule;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PagerAdapter extends FragmentStateAdapter {
private List<DocumentDisplayFragment> mPagesList;
private List<String> mPageData;

    public PagerAdapter(@NonNull FragmentActivity fragmentActivity,List<DocumentDisplayFragment> pagesList) {
        super(fragmentActivity);
        this.mPagesList=pagesList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return DocumentDisplayFragment.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return mPagesList.size();
    }

    public void updatePages(List<DocumentDisplayFragment> pageList){
        this.mPagesList.clear();
        this.mPagesList=pageList;
        notifyDataSetChanged();
    }

    public void updatePageData(List<String> pageDataList){
        this.mPageData=pageDataList;
    }
}

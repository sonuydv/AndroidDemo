package com.sonuydv.viewpager2demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

public class PagerAdapterRecyclerView extends RecyclerView.Adapter<PagerAdapterRecyclerView.ViewHolder>{
    private LayoutInflater mLayoutInflater;
    private List<String> mData;
    private ViewPager2 mViewPager;

    PagerAdapterRecyclerView(Context context, List<String> data, ViewPager2 viewPager2){
      this.mLayoutInflater=LayoutInflater.from(context);
      this.mData=data;
      this.mViewPager=viewPager2;
    }

    //This method provides view Items to the ViewPager or RecyclerView
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=mLayoutInflater.inflate(R.layout.fragment_page,parent,false);
        return new ViewHolder(view);
    }

    //this method binds data to the view which should be shown on the page or view
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
     holder.mTextView.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView=itemView.findViewById(R.id.textView);
        }
    }
}

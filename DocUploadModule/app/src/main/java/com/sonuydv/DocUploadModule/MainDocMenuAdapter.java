package com.sonuydv.DocUploadModule;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sonuydv.DocUploadModule.data.docsupload.DocGroupTabData;
import com.sonuydv.navdrawerwithcustommenu.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

public class MainDocMenuAdapter extends RecyclerView.Adapter<MainDocMenuAdapter.MainDocItemViewHolder> {
    private Context mContext;
    private List<DocGroupTabData> mMainDocMenuList;
    private MainDocItemSelectedListener mListener;
    private ViewPager2 mViewPager;

    public MainDocMenuAdapter(Context context, List<DocGroupTabData> docGroupTabDataList){
        this.mContext=context;
        this.mMainDocMenuList =docGroupTabDataList;
    }

    @NonNull
    @Override
    public MainDocItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainDocItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.doc_main_menu_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainDocItemViewHolder holder, int position) {
        final DocGroupTabData tabData=mMainDocMenuList.get(position);
        holder.itemTitle.setText(mMainDocMenuList.get(position).getTitle());
        Resources resources = holder.itemTitle.getContext().getResources();
        switch (tabData.getStatus()){
            case DocGroupTabData.SKIPPED:
                holder.itemLayout.setBackground(resources.getDrawable(R.drawable.rectangle_dark_grey_solid));
                holder.itemTitle.setTextColor(resources.getColor(R.color.white));
                holder.viewSeparator.setBackgroundColor(resources.getColor(R.color.white));
                holder.imageViewItemStatus.setImageDrawable(resources.getDrawable(R.drawable.ic_skipped));
                break;
            case DocGroupTabData.PENDING:
                holder.itemLayout.setBackgroundColor(resources.getColor(R.color.white));
                holder.itemTitle.setTextColor(resources.getColor(R.color.textColor));
                holder.viewSeparator.setBackgroundColor(resources.getColor(R.color.textColor));
                holder.imageViewItemStatus.setImageDrawable(resources.getDrawable(R.drawable.ic_pending));
                break;

            case DocGroupTabData.IN_PROGRESS:
                holder.itemLayout.setBackground(resources.getDrawable(R.drawable.rectangle_axis_color_solid));
                holder.itemTitle.setTextColor(resources.getColor(R.color.white));
                holder.viewSeparator.setBackgroundColor(resources.getColor(R.color.white));
                holder.imageViewItemStatus.setImageDrawable(resources.getDrawable(R.drawable.ic_in_progress));
                break;

            case DocGroupTabData.COMPLETED:
                if(tabData.getTitle().equalsIgnoreCase(holder.itemView.getResources().getString(R.string.frag_data_submission))){
                    holder.itemLayout.setBackground(resources.getDrawable(R.drawable.rectangle_success_solid));
                    holder.itemTitle.setTextColor(resources.getColor(R.color.white));
                    holder.viewSeparator.setBackgroundColor(resources.getColor(R.color.white));
                    holder.imageViewItemStatus.setImageDrawable(resources.getDrawable(R.drawable.ic_form_completed));
                }
                else {
                    holder.itemLayout.setBackground(resources.getDrawable(R.drawable.rectangle_dark_grey_solid));
                    holder.itemTitle.setTextColor(resources.getColor(R.color.white));
                    holder.viewSeparator.setBackgroundColor(resources.getColor(R.color.white));
                    holder.imageViewItemStatus.setImageDrawable(resources.getDrawable(R.drawable.ic_completed));
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mMainDocMenuList.size();
    }

    public void updateDataList(List<DocGroupTabData> dataList){
        mMainDocMenuList.clear();
        mMainDocMenuList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void setMainDocItemSelectedListener(MainDocItemSelectedListener listener){
        this.mListener=listener;
    }


    public void setUpWithViewPager(ViewPager2 viewPager){
       this.mViewPager=viewPager;
    }

    class MainDocItemViewHolder extends RecyclerView.ViewHolder{
        private TextView itemTitle;
        private RelativeLayout itemLayout;
        private View viewSeparator;
        private ImageView imageViewItemStatus;

        public MainDocItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitle =itemView.findViewById(R.id.nav_menu_title_tv);
            itemLayout=itemView.findViewById(R.id.rl_background);
            viewSeparator=itemView.findViewById(R.id.view_separator);
            imageViewItemStatus=itemView.findViewById(R.id.iv_status);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  if(mListener!=null)
                      mListener.onMainDocItemSelected(getAdapterPosition());
                  if(mViewPager!=null)
                      mViewPager.setCurrentItem(getAdapterPosition());
                }
            });
        }
    }



    public  interface MainDocItemSelectedListener {
        void onMainDocItemSelected(int position);
    }
}

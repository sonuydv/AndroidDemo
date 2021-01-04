package com.sonuydv.DocUploadModule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sonuydv.navdrawerwithcustommenu.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DocTypeListAdapter  extends RecyclerView.Adapter<DocTypeListAdapter.DocTypeItemViewHolder>{
    private OnDocTypeItemSelectedListener mItemClickListener;
    private List<String> mDocItemList;

    public DocTypeListAdapter(List<String> mDocItemList) {
        this.mDocItemList = mDocItemList;
    }

    @NonNull
    @Override
    public DocTypeItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DocTypeItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.doc_type_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DocTypeItemViewHolder holder, int position) {
        holder.mDocTypeItemTitle.setText(mDocItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDocItemList.size();
    }

    public void setOnDocTypeItemSelectedListener(OnDocTypeItemSelectedListener listener){
        this.mItemClickListener=listener;
    }

    public void updateDataList(List<String> dataList){
        this.mDocItemList=dataList;
    }

    class DocTypeItemViewHolder extends RecyclerView.ViewHolder{
        private TextView mDocTypeItemTitle;

        public DocTypeItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mDocTypeItemTitle=itemView.findViewById(R.id.doc_type_title_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mItemClickListener!=null)
                        mItemClickListener.onDocTypeItemSelected(getAdapterPosition());
                }
            });
        }
    }

    interface OnDocTypeItemSelectedListener{
        void onDocTypeItemSelected(int position);
    }
}

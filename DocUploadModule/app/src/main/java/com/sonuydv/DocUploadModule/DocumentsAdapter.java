package com.sonuydv.DocUploadModule;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sonuydv.navdrawerwithcustommenu.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DocumentsAdapter extends RecyclerView.Adapter<DocumentsAdapter.DocumentViewHolder> {
    private List<String> dataList;
    private DocumentItemClickListener listener;
    private boolean isEditable;


//    public DocumentAdapter(/*List<String> pathList,*/ boolean isEditable, DocumentItemClickListener listener){
//        this.dataList = new ArrayList<>();
//        this.listener = listener;
//        this.isEditable = isEditable;
//        this.handler = new Handler();
//    }

    public DocumentsAdapter(List<String> pathList, boolean isEditable, DocumentItemClickListener listener){
        this.dataList = pathList;
        this.listener = listener;
        this.isEditable = isEditable;
    }

    @NonNull
    @Override
    public DocumentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DocumentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_document_preview, parent, false));
    }


    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        listener = null;
    }

    @Override
    public void onBindViewHolder(final DocumentViewHolder holder, final int position) {
        holder.ic_delete.setVisibility(isEditable ? View.VISIBLE : View.GONE);
        Picasso.get().load(new File(dataList.get(position))).resize(240, 250).into(holder.iv_document, new Callback() {
            @Override
            public void onSuccess() {
                holder.pb_loading.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                holder.pb_loading.setVisibility(View.GONE);
                if(e instanceof FileNotFoundException){
                    dataList.remove(position);
                    notifyItemRemoved(position);
                }
            }
        });

        if(holder.ic_delete.getVisibility() == View.VISIBLE) {
            holder.ic_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener == null) return;
                    listener.deleteImage(holder.getAdapterPosition());
                }
            });
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener == null) return;
                listener.previewImage(dataList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        if(dataList == null) dataList = new ArrayList<>();
        return dataList.size();
    }

    public void updateImgList(List<String> imgPathList){
        this.dataList.clear();
        this.dataList.addAll(imgPathList);
        notifyDataSetChanged();
    }

    static class DocumentViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv_document;
        private View ic_delete;
        private View pb_loading;

        DocumentViewHolder(View itemView) {
            super(itemView);
            iv_document = itemView.findViewById(R.id.image);
            ic_delete = itemView.findViewById(R.id.delete);
            pb_loading = itemView.findViewById(R.id.pb_loading);
        }
    }


//    public void addAll(List<String> pathList){
//        if(dataList == null) dataList = new ArrayList<>();
//        this.dataList.addAll(pathList);
//        notifyDataSetChanged();
//    }
//
//    public void removeItem(int position){
//        if(dataList.isEmpty()) return;
//        dataList.remove(position);
//        notifyItemRemoved(position);
//    }

    interface DocumentItemClickListener {
        void deleteImage(int position);

        void previewImage(String path);
    }

}

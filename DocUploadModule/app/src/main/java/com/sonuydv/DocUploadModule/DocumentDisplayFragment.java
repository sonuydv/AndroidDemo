package com.sonuydv.DocUploadModule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sonuydv.DocUploadModule.data.docsupload.Document;
import com.sonuydv.navdrawerwithcustommenu.R;
import com.sonuydv.navdrawerwithcustommenu.databinding.FragmentDocumentDisplayBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

public class DocumentDisplayFragment extends Fragment implements View.OnClickListener,MainView.DocumentDisplayView {
    public static final String PARAM_1="position";
    private String mTitleData;
    private FragmentDocumentDisplayBinding mBinding;

    private DocumentsAdapter mDocumentAdapter;

    private Document mCurrentDoc;
    private List<String> mDocImgList;
    private MainViewPresenter mPresenter;

    public  static DocumentDisplayFragment newInstance(int position){
        DocumentDisplayFragment fragment=new DocumentDisplayFragment(null);
        Bundle bundle=new Bundle();
        bundle.putString(PARAM_1,""+position);
        fragment.setArguments(bundle);
        return fragment;
    }

    public DocumentDisplayFragment(MainViewPresenter presenter){
        mPresenter=presenter;
        mDocImgList=new ArrayList<>();
        mCurrentDoc=new Document();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_document_display,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.rvImages.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.image_grid_count)));
        mBinding.fbTick.setOnClickListener(this);
        mBinding.fbCamera.setOnClickListener(this);
        mBinding.fbSkip.setOnClickListener(this);
        assert getArguments() != null;
        if(getArguments()!=null){
            mTitleData=getArguments().getString(PARAM_1);
            mBinding.layoutTitle.pageTitleTv.setText(mTitleData);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        //Get current doc data on fragment start
        this.mCurrentDoc=mPresenter.getCurrentDoc();
       mBinding.layoutTitle.pageTitleTv.setText(mCurrentDoc.docName==null?"Title":mCurrentDoc.docName);

    }

    @Override
    public void onStop() {
        //Set modified data of current doc
        mPresenter.onPageStop(mDocImgList);
        super.onStop();
    }

    @Override
    public void refreshFragment(Document document) {
        if(document!=null){
            mCurrentDoc=document;
            mBinding.layoutTitle.pageTitleTv.setText(mCurrentDoc.docName);
            updateImageListView(mCurrentDoc.docImgBase64List);
        }

    }

    @Override
    public void updateImageListView(List<String> imgList) {
       mDocumentAdapter.updateImgList(imgList);
    }

    @Override
    public void onClick(View v) {

    }

    private void toggleButtons(){
        if(mCurrentDoc.isSkippable){
            if(mCurrentDoc.docImgBase64List.size() < 1){
                mBinding.fbSkip.setVisibility(View.VISIBLE);
                mBinding.fbTick.setVisibility(View.GONE);
            }
            else {
                mBinding.fbSkip.setVisibility(View.GONE);
                mBinding.fbTick.setVisibility(View.VISIBLE);
            }
        }
        else {
            mBinding.fbSkip.setVisibility(View.GONE);
            mBinding.fbTick.setVisibility(mDocumentAdapter.getItemCount()>=mCurrentDoc.minDocCount?View.VISIBLE:View.GONE);
        }

        mBinding.fbCamera.setVisibility(mDocumentAdapter.getItemCount() >= mCurrentDoc.maxDocCount ? View.GONE : View.VISIBLE);

        mBinding.tvMinCount.setText(String.format("Min: %s", mCurrentDoc.minDocCount));
        mBinding.tvMaxCount.setText(String.format("Max: %s", mCurrentDoc.maxDocCount));
    }


    private void showToast(String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}

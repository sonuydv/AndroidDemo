package com.sonuydv.DocUploadModule;

import com.sonuydv.DocUploadModule.data.docsupload.DocGroupTabData;
import com.sonuydv.DocUploadModule.data.docsupload.Document;
import com.sonuydv.DocUploadModule.data.docsupload.DocumentData;

import java.util.ArrayList;
import java.util.List;


public class MainViewPresenter implements DocTypeListAdapter.OnDocTypeItemSelectedListener,MainDocMenuAdapter.MainDocItemSelectedListener, DocumentsAdapter.DocumentItemClickListener {

    private MainView mView;
    private MainView.DocumentDisplayView mDocDisplayView;

    private List<DocumentData> mDocDataList;
    private int tmpCurrentDocGroup;
    private int tmpCurrentDocType;
    private int currentDocGroup=-1;
    private int currentDocType=-1;

    public MainViewPresenter(MainView mView, MainView.DocumentDisplayView docDisplayView) {
        this.mView = mView;
        this.mDocDisplayView=docDisplayView;
    }

    public void onViewInit(){
        mView.updateMainDocList(getDocGroupTabData(mDocDataList));
    }

    @Override
    public void onMainDocItemSelected(int position) {
        tmpCurrentDocGroup=position;
        mView.closeDrawer();
        mView.showChooseDocItemDialog(getDocTypeTitles(mDocDataList,position));
    }

    @Override
    public void onDocTypeItemSelected(int position) {
        tmpCurrentDocType=position;
        mView.hideChooseDocItemDialog();
        mView.showDocFieldFillDialog();
    }

    public void onFieldDoneBtnClick(){
       mView.hideDocFieldFillDialog();
        currentDocGroup=tmpCurrentDocGroup;
        currentDocType=tmpCurrentDocType;
       mView.reloadFragment(currentDocType);
       mDocDisplayView.refreshFragment(mDocDataList.get(currentDocGroup).docList.get(currentDocType));
    }

    //Fragment call back
    @Override
    public void deleteImage(int position) {

    }

    //fragment call back
    @Override
    public void previewImage(String path) {

    }

    //Get selected document data to document display fragment
    public Document getCurrentDoc(){
        if(currentDocGroup==-1){
            return new Document();
        } else{
            return mDocDataList.get(currentDocGroup).docList.get(currentDocType);
        }
    }

    //Set modified document data on fragment replaced
    public void onPageStop(List<String> docImgList){
        if(currentDocGroup!=-1)
        mDocDataList.get(currentDocGroup).docList.get(currentDocType).docImgBase64List=docImgList;
    }

    public void setDocData(List<DocumentData> docDataList){
        this.mDocDataList=docDataList;
    }


    private List<String> getDocTypeTitles(List<DocumentData> documentDataList,int docGroup){
        List<String> docTypeTitles=new ArrayList<>(documentDataList.size());
        for(Document document:documentDataList.get(docGroup).docList){
            docTypeTitles.add(document.docName);
        }
        return docTypeTitles;
    }

    private List<DocGroupTabData> getDocGroupTabData(List<DocumentData> documentDataList){
        List<DocGroupTabData> docGroupTabDataList=new ArrayList<>(documentDataList.size());
        for(DocumentData documentData:documentDataList){
            docGroupTabDataList.add(new DocGroupTabData(documentData.docTypeLabel,1));
        }
        return docGroupTabDataList;
    }


}

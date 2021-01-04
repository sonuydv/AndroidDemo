package com.sonuydv.DocUploadModule;

import com.sonuydv.DocUploadModule.data.docsupload.DocGroupTabData;
import com.sonuydv.DocUploadModule.data.docsupload.Document;

import java.util.List;

public interface MainView {
    void showMessage(String msg);
    void showProgress(String msg);
    void hideProgress();
    void showChooseDocItemDialog(List<String> docTitles);
    void hideChooseDocItemDialog();
    void showDocFieldFillDialog();
    void hideDocFieldFillDialog();
    void closeDrawer();
    void initPages(int count);
    void updatePageDataList(List<String> pageDataList);
    void setCurrentPage(String titleData);
    void updateMainDocList(List<DocGroupTabData> docGroupTabDataList);
    void reloadFragment(int position);
    interface DocumentDisplayView{
        void refreshFragment(Document document);
        void updateImageListView(List<String> imgList);
    }
}

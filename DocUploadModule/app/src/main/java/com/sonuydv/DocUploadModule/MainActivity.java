package com.sonuydv.DocUploadModule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.sonuydv.navdrawerwithcustommenu.R;
import com.sonuydv.DocUploadModule.data.docsupload.DocGroupTabData;
import com.sonuydv.DocUploadModule.data.docsupload.DocListResponse;
import com.sonuydv.DocUploadModule.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MainView  {

    private int pageSize;
    private MainViewPresenter mPresenter;

    private DrawerLayout mDrawerLayout;
    private ViewPager2 mFragmentViewPager;
    private PagerAdapter mPagerAdapter;
    private List<DocumentDisplayFragment> mPagesList;
    private DocumentDisplayFragment mFragment;


    private RecyclerView mMainDocListView;
    private MainDocMenuAdapter mMainDocMenuListAdapter;

    private NavigationView mNavView;

    //Doc Type Variables
    private Dialog mDocTypeChoiceDialog;
    private DocTypeListAdapter mDocTypeListAdapter;

    //Doc field variables
    private Dialog mDocFieldDialog;

    private AppCompatDialog mProgressDialog;

    private ViewPager2.OnPageChangeCallback mOnPageChangeCallback=new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            //ToDo
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(AppUtils.isTablet(this)? R.layout.activity_main_tablet:R.layout.activity_main_mobile);
//        setContentView(R.layout.activity_main_tablet);

        mPresenter=new MainViewPresenter(this);

        //Init views
        mDrawerLayout=findViewById(R.id.drawer_layout);
        mMainDocListView=findViewById(R.id.main_doc__menu_list);
//        mFragmentViewPager=findViewById(R.id.fragment_view_pager);
        mNavView=findViewById(R.id.nav_view);

        initDocTypeChoiceDialog();
        initDocFieldDialog();
        initProgressDialog();

        //Init and setup main document menu Adapter
        mMainDocMenuListAdapter =new MainDocMenuAdapter(this,new ArrayList<DocGroupTabData>());
        mMainDocMenuListAdapter.setMainDocItemSelectedListener(mPresenter);
        mMainDocListView.setAdapter(mMainDocMenuListAdapter);

        //Setting up navigation default
        mNavView.setNavigationItemSelectedListener(this);

        //Init and setup fragment
         mFragment=new DocumentDisplayFragment(mPresenter);
         FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
         ft.replace(R.id.fragment_container,mFragment);
         ft.commit();


        //Disable view pager swipe input gesture to change page
//        mFragmentViewPager.setUserInputEnabled(false);

        //Init pager adapter
//        mPagerAdapter=new PagerAdapter(this, new ArrayList<Page>());
//        mFragmentViewPager.setAdapter(mPagerAdapter);
//        mFragmentViewPager.registerOnPageChangeCallback(mOnPageChangeCallback);

        //Setting up viewpager with menu click listener to change page accordingly
        mMainDocMenuListAdapter.setUpWithViewPager(mFragmentViewPager);

        //Notifying presenter that views are initialized
        setDocumentData();
        mPresenter.onViewInit();

    }


    //Navigation View Item Selected callback
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_dashboard:
                closeDrawer();
                showMessage("Navigating home...");
                break;
            case R.id.nav_logout:
                closeDrawer();
                showMessage("Logging out please wait..");
                break;
        }
        return false;
    }

    @Override
    public void setCurrentPage(String titleData) {

    }

    @Override
    public void updatePageDataList(List<String> pageDataList) {

    }

    @Override
    public void reloadFragment(int position) {
     final FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
     ft.detach(mFragment);
     ft.attach(mFragment);
     ft.commit();
    }

    @Override
    public void updateMainDocList(List<DocGroupTabData> docGroupTabDataList) {
         mMainDocMenuListAdapter.updateDataList(docGroupTabDataList);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(MainActivity.this,msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress(String msg) {
       mProgressDialog.show();
    }

    @Override
    public void hideProgress() {
       mProgressDialog.hide();
    }

    @Override
    public void showChooseDocItemDialog(List<String> docTitles) {
        mDocTypeListAdapter.updateDataList(docTitles);
        mDocTypeChoiceDialog.show();
    }
    @Override
    public void hideChooseDocItemDialog() {
        mDocTypeChoiceDialog.hide();
    }

    @Override
    public void showDocFieldFillDialog() {
      mDocFieldDialog.show();
    }

    @Override
    public void hideDocFieldFillDialog() {
      mDocFieldDialog.hide();
    }


    @Override
    public void closeDrawer() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
            mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void initPages(int count) {
        mPagesList=new ArrayList<>(count);
        for(int i=0;i<count;i++){
            mPagesList.add(DocumentDisplayFragment.newInstance(count));
        }
//        mPagerAdapter.updatePages(mPagesList);
    }



    private void initDocTypeChoiceDialog(){
        mDocTypeChoiceDialog=new Dialog(this);
        mDocTypeChoiceDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDocTypeChoiceDialog.setContentView(R.layout.dialog_doc_type_layout);
        ((TextView)mDocTypeChoiceDialog.findViewById(R.id.doc_item_dialog_title)).setText("Select Document Type");
        RecyclerView recyclerView=mDocTypeChoiceDialog.findViewById(R.id.doc_item_list);
        mDocTypeListAdapter=new DocTypeListAdapter(new ArrayList<String>());
        mDocTypeListAdapter.setOnDocTypeItemSelectedListener(mPresenter);
        assert recyclerView != null;
        recyclerView.setAdapter(mDocTypeListAdapter);

    }

private void initDocFieldDialog(){
        mDocFieldDialog=new Dialog(this);
        mDocFieldDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDocFieldDialog.setContentView(R.layout.dialog_doc_fields);
        mDocFieldDialog.findViewById(R.id.btn_field_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onFieldDoneBtnClick();
            }
        });
}

private void initProgressDialog(){
        mProgressDialog=new AppCompatDialog(this);
        mProgressDialog.setContentView(R.layout.dialog_progress);
}

public void setDocumentData(){
    DocListResponse docListResponse=new DocListResponse();
    Gson gson=new Gson();
    String jsonFileString = AppUtils.getJsonFromAssets(getApplicationContext(), "jsonData.json");
    docListResponse=gson.fromJson(jsonFileString,DocListResponse.class);
    mPresenter.setDocData(docListResponse.getDocDataList());
}


}
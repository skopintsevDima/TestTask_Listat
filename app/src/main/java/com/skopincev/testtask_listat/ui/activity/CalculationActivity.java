package com.skopincev.testtask_listat.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.skopincev.testtask_listat.R;
import com.skopincev.testtask_listat.model.Data;
import com.skopincev.testtask_listat.presenter.CalculationPresenter;
import com.skopincev.testtask_listat.presenter.CalculationPresenterImpl;
import com.skopincev.testtask_listat.ui.adapter.ThreadListAdapter;
import com.skopincev.testtask_listat.view.CalculationView;

import java.util.ArrayList;

public class CalculationActivity extends AppCompatActivity implements CalculationView {

    private RecyclerView mList;
    private ThreadListAdapter mListAdapter;

    private CalculationPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);

        mPresenter = new CalculationPresenterImpl();
        mPresenter.attach(this);

        initList();
    }

    private void initList() {
        mList = (RecyclerView) findViewById(R.id.rv_list);
        mListAdapter = new ThreadListAdapter(this, new ArrayList<Data>());
        mList.setLayoutManager(new LinearLayoutManager(this));
        mList.setAdapter(mListAdapter);
    }

    @Override
    public void displayData(final Data data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                addItemToList(data);
            }
        });
    }

    private void addItemToList(Data data) {
        mListAdapter.addItem(data);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPresenter != null){
            mPresenter.pauseDisplaying();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null){
            mPresenter.resumeDisplaying();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null){
            mPresenter.finishCalculation();
            mPresenter.detach();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calculation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mi_start_calculation:{
                mListAdapter.clear();
                mPresenter.startCalculation();
                break;
            }
        }
        return true;
    }
}

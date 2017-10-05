package com.skopincev.testtask_listat.ui.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.skopincev.testtask_listat.R;
import com.skopincev.testtask_listat.model.Data;
import com.skopincev.testtask_listat.presenter.CalculationPresenter;
import com.skopincev.testtask_listat.presenter.CalculationPresenterImpl;
import com.skopincev.testtask_listat.view.CalculationView;

public class CalculationActivity extends AppCompatActivity implements CalculationView {

    private Button btnStart;

    private CalculationPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);

        mPresenter = new CalculationPresenterImpl();
        mPresenter.attach(this);

        btnStart = (Button) findViewById(R.id.btn_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.startCalculation();
            }
        });
    }

    @Override
    public void displayData(final Data data) {
        //TODO: remove mock
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(CalculationActivity.this, data.toString(), Toast.LENGTH_LONG).show();
            }
        });
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
}

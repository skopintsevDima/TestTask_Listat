package com.skopincev.testtask_listat.presenter;

import android.util.Log;

import com.skopincev.testtask_listat.BundleConst;
import com.skopincev.testtask_listat.model.Data;
import com.skopincev.testtask_listat.view.CalculationView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by skopi on 04.10.2017.
 */

public class StoringThreadImpl extends Thread implements StoringThread {

    private static final String TAG = StoringThreadImpl.class.getSimpleName();

    private String mConnectionState = BundleConst.CONNECTION_CLOSED;
    private List<Data> mDataBank = new ArrayList<>();
    private CalculationView mView;

    public StoringThreadImpl(CalculationView view){
        mView = view;
    }

    private void displayStoredData() {
        for (Data data: mDataBank){
            displayData(data);
        }
    }

    @Override
    public void connect() {
        if (mConnectionState.equals(BundleConst.CONNECTION_ESTABLISHED) ||
                mConnectionState.equals(BundleConst.CONNECTION_CLOSED)){
            mConnectionState = BundleConst.CONNECTION_OPENED;
            displayStoredData();
        } else {
            Log.d(TAG, "View: NO CONNECTION");
        }
    }

    @Override
    public void disconnect() {
        if (mConnectionState.equals(BundleConst.CONNECTION_OPENED)){
            mConnectionState = BundleConst.CONNECTION_CLOSED;
        } else {
            Log.d(TAG, "View: DISCONNECTED");
        }
    }

    @Override
    public void pullData(Data data) {
        String isDisplayed = displayData(data);
        if (isDisplayed.equals(BundleConst.RESULT_FAIL)){
            mDataBank.add(data);
        }
    }

    @Override
    public String displayData(Data data) {
        String isDisplayed = BundleConst.RESULT_FAIL;
        if (mConnectionState.equals(BundleConst.CONNECTION_OPENED)){
            mView.displayData(data);
            isDisplayed = BundleConst.RESULT_SUCCESS;
        }
        return isDisplayed;
    }
}

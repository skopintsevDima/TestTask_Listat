package com.skopincev.testtask_listat.presenter;

import android.util.Log;

import com.skopincev.testtask_listat.BundleConst;
import com.skopincev.testtask_listat.model.Data;
import com.skopincev.testtask_listat.view.CalculationView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skopi on 04.10.2017.
 */

public class StoringThreadImpl extends Thread implements StoringThread {

    private static final String TAG = StoringThreadImpl.class.getSimpleName();

    /**
     * Used for describing state of connection between View and Presenter
     */
    private String mConnectionState = BundleConst.CONNECTION_CLOSED;
    private List<Data> mDataBank = new ArrayList<>();
    private CalculationPresenter mPresenter;

    public StoringThreadImpl(CalculationPresenter presenter){
        mPresenter = presenter;
    }

    /**
     * Sends all data that was been stored while connection was closed
     */
    private void displayStoredData() {
        if (mDataBank.size() > 0) {
            Log.d(TAG, "displayStoredData: Start displaying");
            for (Data data : mDataBank) {
                mPresenter.displayData(data);
                Log.d(TAG, "displayStoredData: Data displayed from thread " + data.getCalculationThreadId());
            }
        } else {
            Log.d(TAG, "displayStoredData: Store is empty");
        }
    }

    @Override
    public void connect() {
        if (mConnectionState.equals(BundleConst.CONNECTION_ESTABLISHED) ||
                mConnectionState.equals(BundleConst.CONNECTION_CLOSED)){
            mConnectionState = BundleConst.CONNECTION_OPENED;
            Log.d(TAG, "connect: View connected");
            displayStoredData();
        } else {
            Log.d(TAG, "connect: View not found");
        }
    }

    @Override
    public void disconnect() {
        if (mConnectionState.equals(BundleConst.CONNECTION_OPENED)){
            mConnectionState = BundleConst.CONNECTION_CLOSED;
            Log.d(TAG, "disconnect: View disconnected");
        } else {
            Log.d(TAG, "disconnect: No connection");
        }
    }

    @Override
    public void pullData(Data data) {
        if (mConnectionState.equals(BundleConst.CONNECTION_OPENED)) {
            mPresenter.displayData(data);
            Log.d(TAG, "pullData: Data displayed from thread " + data.getCalculationThreadId());
        } else {
            mDataBank.add(data);
            Log.d(TAG, "pullData: Data stored from thread " + data.getCalculationThreadId());
        }
    }

    @Override
    public void stopPullingData() {
        if (isAlive()){
            Log.d(TAG, "stopPullingData: Storing thread stopped");
            stop();
        }
    }
}

package com.skopincev.testtask_listat.presenter;

import android.util.Log;

import com.skopincev.testtask_listat.BundleConst;
import com.skopincev.testtask_listat.model.Data;

/**
 * Created by skopi on 04.10.2017.
 */

public class BusThreadImpl extends Thread implements BusThread {

    private static final String TAG = BusThreadImpl.class.getSimpleName();

    /**
     * Used for controlling access to Bus from another threads
     */
    private int mBusState = BundleConst.STATE_CLOSED;
    private StoringThread mStoringThread;

    public BusThreadImpl(StoringThread storingThread){
        mStoringThread = storingThread;
    }

    @Override
    public synchronized String pullNumber(int number, int threadId) {
        if (mBusState == BundleConst.STATE_OPENED){
            mBusState = BundleConst.STATE_CLOSED;

            Data data = new Data(number, threadId);
            String isSent;
            //Try to send data to Store until was sent
            do {
                isSent = sendData(data);
                Log.d(TAG, "pullNumber: " + data.toString() + " sending: " + isSent);
                try {
                    if (isSent.equals(BundleConst.RESULT_FAIL)) {
                        sleep(BundleConst.TIME_TO_WAIT);
                    }
                } catch (InterruptedException e) {
                    Log.d(TAG, "pullNumber: " + e.getMessage());
                }
            } while (!isSent.equals(BundleConst.RESULT_SUCCESS));

            mBusState = BundleConst.STATE_OPENED;
            return BundleConst.RESULT_SUCCESS;
        }
        return BundleConst.RESULT_FAIL;
    }

    @Override
    public String sendData(Data data) {
        String isSended = BundleConst.RESULT_FAIL;
        if (mStoringThread != null){
            mStoringThread.pullData(data);
            isSended = BundleConst.RESULT_SUCCESS;
        }
        return isSended;
    }

    @Override
    public void stopPullingNumbers() {
        if (isAlive()){
            stop();
            mStoringThread.stopPullingData();
        }
    }

    @Override
    public void run() {
        super.run();
        mBusState = BundleConst.STATE_OPENED;
        mStoringThread.run();
    }
}

package com.skopincev.testtask_listat.presenter;

import android.util.Log;

import com.skopincev.testtask_listat.BundleConst;
import com.skopincev.testtask_listat.model.Data;

/**
 * Created by skopi on 04.10.2017.
 */

public class BusThreadImpl extends Thread implements BusThread {

    private static final String TAG = BusThreadImpl.class.getSimpleName();

    private int mBusState = BundleConst.STATE_CLOSED;
    private StoringThread mStoringThread;

    public BusThreadImpl(StoringThread storingThread){
        mStoringThread = storingThread;
    }

    @Override
    public String pullNumber(int number, int threadId) {
        if (mBusState == BundleConst.STATE_OPENED){
            mBusState = BundleConst.STATE_CLOSED;

            Data data = new Data(number, threadId);
            String isSended;
            do {
                isSended = sendData(data);
                Log.d(TAG, data.toString() + " sended: " + isSended);
                try {
                    if (isSended.equals(BundleConst.RESULT_FAIL))
                        sleep(BundleConst.TIME_TO_WAIT);
                } catch (InterruptedException e) {
                    Log.d(TAG, e.getMessage());
                }
            } while (!isSended.equals(BundleConst.RESULT_SUCCESS));

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
    public void run() {
        super.run();
        mBusState = BundleConst.STATE_OPENED;
        mStoringThread.run();
    }
}

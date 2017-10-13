package com.skopincev.testtask_listat.model;

import android.util.Log;

import com.skopincev.testtask_listat.BundleConst;
import com.skopincev.testtask_listat.presenter.BusThread;

import java.util.Random;

/**
 * Created by skopi on 04.10.2017.
 */

public class CalculationThreadImpl extends Thread implements CalculationThread {

    private static final String TAG = CalculationThreadImpl.class.getSimpleName();

    private int mThreadId;

    private int mMin;
    private int mMax;

    private int mState;

    private BusThread mBus;

    public CalculationThreadImpl(int threadId, int min, int max, BusThread bus){
        mThreadId = threadId;
        mMin = min;
        mMax = max;
        mState = BundleConst.STATE_CREATED;
        mBus = bus;
    }

    @Override
    public void run() {
        super.run();
        for (int num = mMin; num < mMax; num++){
            if (isPrime(num)){
                String isSended;
                do {
                    isSended = sendNumber(num);
                    Log.d(TAG, "Prime number " + num + " from Thread " + mThreadId + " sended: " + isSended);
                    try {
                        if (isSended.equals(BundleConst.RESULT_FAIL))
                            sleep(BundleConst.TIME_TO_WAIT);
                    } catch (InterruptedException e) {
                        Log.d(TAG, e.getMessage());
                    }
                } while (!isSended.equals(BundleConst.RESULT_SUCCESS));
            }
        }
        mState = BundleConst.STATE_FINISHED;
    }

    private boolean isPrime(int num) {
        //TODO: use faster algorithm
        for(long i = 2; i <= (int) Math.sqrt(num); i++) {
            if (num % i == 0)
                return false;
        }
        return true;
    }

    @Override
    public void startCalculating() {
        start();
        mState = BundleConst.STATE_STARTED;
    }

    @Override
    public void stopCalculating() {
        stop();
    }

    @Override
    public String sendNumber(int number) {
        String isSended = mBus.pullNumber(number, mThreadId);
        return isSended;
    }
}

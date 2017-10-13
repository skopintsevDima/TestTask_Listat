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

    /**
     * Describe calculation state
     */
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
                String isSent;
                //Try to send number to Bus until was sent
                do {
                    isSent = sendNumber(num);
                    Log.d(TAG, "run: Prime number " + num + " from Thread " + mThreadId + " sending: " + isSent);
                    try {
                        if (isSent.equals(BundleConst.RESULT_FAIL)) {
                            sleep(BundleConst.TIME_TO_WAIT);
                        }
                    } catch (InterruptedException e) {
                        Log.d(TAG, "run: " + e.getMessage());
                    }
                } while (!isSent.equals(BundleConst.RESULT_SUCCESS));
            }
        }
        mState = BundleConst.STATE_FINISHED;
    }

    /**
     * Checks number for primary
     * @param num is number for checking
     * @return
     */
    private boolean isPrime(int num) {
        Log.d(TAG, "isPrime: checking number " + num + " for primary started");

        //TODO: use faster algorithm
        for(long i = 2; i <= (int) Math.sqrt(num); i++) {
            if (num % i == 0) {
                Log.d(TAG, "isPrime: number " + num + " is not prime");
                return false;
            }
        }
        Log.d(TAG, "isPrime: number " + num + " is prime");
        return true;
    }

    @Override
    public void startCalculating() {
        start();
        mState = BundleConst.STATE_STARTED;

        Log.d(TAG, "startCalculating: thread " + mThreadId + " started calculating");
    }

    @Override
    public void stopCalculating() {
        stop();

        Log.d(TAG, "stopCalculating: thread " + mThreadId + " stopped calculating");

    }

    /**
     * Try to send number to Bus
     * @param number is number for sending
     * @return
     */
    @Override
    public String sendNumber(int number) {
        String isSent = mBus.pullNumber(number, mThreadId);
        return isSent;
    }
}

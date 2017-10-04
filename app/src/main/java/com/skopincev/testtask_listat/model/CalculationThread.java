package com.skopincev.testtask_listat.model;

/**
 * Created by skopi on 04.10.2017.
 */

public class CalculationThread extends Thread {

    private int mThreadId;
    private int mMin;
    private int mMax;


    public CalculationThread(int threadId, int min, int max){
        mThreadId = threadId;
        mMin = min;
        mMax = max;
    }

    @Override
    public void run() {
        super.run();
    }
}

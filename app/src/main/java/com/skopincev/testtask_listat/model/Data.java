package com.skopincev.testtask_listat.model;

import com.skopincev.testtask_listat.presenter.BusThread;
import com.skopincev.testtask_listat.presenter.StoringThread;
import com.skopincev.testtask_listat.view.CalculationView;

import java.util.UUID;

/**
 * Created by skopi on 04.10.2017.
 * Hold data about prime number.
 * Used for sending between {@link BusThread} and {@link StoringThread},
 * and between {@link StoringThread} and {@link CalculationView}
 */

public class Data {

    private UUID mId;
    private int mPrimeNumber;
    private int mCalculationThreadId;

    public int getPrimeNumber() {
        return mPrimeNumber;
    }

    public int getCalculationThreadId() {
        return mCalculationThreadId;
    }

    public Data(int primeNumber, int calculationThreadId){
        mId = UUID.randomUUID();
        mPrimeNumber = primeNumber;
        mCalculationThreadId = calculationThreadId;
    }

    @Override
    public String toString() {
        return "Data: " +
                "id = " + mId.toString() + ", " +
                "number = " + mPrimeNumber + ", " +
                "threadId = " + mCalculationThreadId + ";";
    }
}

package com.skopincev.testtask_listat.model;

import java.util.Date;
import java.util.UUID;

/**
 * Created by skopi on 04.10.2017.
 */

public class Data {

    private UUID mId;
    private int mPrimeNumber;
    private int mCalculationThreadId;

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

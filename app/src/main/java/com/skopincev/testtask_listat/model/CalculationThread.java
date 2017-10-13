package com.skopincev.testtask_listat.model;

/**
 * Created by skopi on 04.10.2017.
 */

public interface CalculationThread {
    void startCalculating();
    void stopCalculating();
    String sendNumber(int number);
}

package com.skopincev.testtask_listat.presenter;

import com.skopincev.testtask_listat.model.Data;

import java.util.Date;

/**
 * Created by skopi on 04.10.2017.
 */

public interface BusThread {
    void run();
    String pullNumber(int number, int threadId);
    String sendData(Data data);
}


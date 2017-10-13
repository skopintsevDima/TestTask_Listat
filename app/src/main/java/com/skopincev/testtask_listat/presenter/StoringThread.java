package com.skopincev.testtask_listat.presenter;

import com.skopincev.testtask_listat.model.Data;

/**
 * Created by skopi on 04.10.2017.
 */

public interface StoringThread {
    void run();
    void connect();
    void disconnect();
    void pullData(Data data);
    void stopPullingData();
}

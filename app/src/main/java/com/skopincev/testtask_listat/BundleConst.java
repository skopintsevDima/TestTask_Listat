package com.skopincev.testtask_listat;

import com.skopincev.testtask_listat.model.CalculationThreadImpl;
import com.skopincev.testtask_listat.presenter.BusThread;
import com.skopincev.testtask_listat.presenter.BusThreadImpl;
import com.skopincev.testtask_listat.presenter.StoringThread;

/**
 * Created by skopi on 04.10.2017.
 */

public interface BundleConst {
    /**
     * State of connections between {@link StoringThread} and {@link BusThread}
     */
    String CONNECTION_ESTABLISHED = "Established";
    String CONNECTION_OPENED = "Opened";
    String CONNECTION_CLOSED = "Closed";
    String CONNECTION_REOPENING = "Reopening...";

    /**
     * State of {@link CalculationThreadImpl}
     */
    int STATE_CREATED = 1;
    int STATE_STARTED = 2;
    int STATE_FINISHED = 3;

    /**
     * State of {@link BusThreadImpl}
     */
    int STATE_OPENED = 1;
    int STATE_CLOSED = 2;

    /**
     * Sending result description
     */
    String RESULT_SUCCESS = "Successful";
    String RESULT_FAIL = "Failure";

    /**
     * How much wait for repeat in millis
     */
    int TIME_TO_WAIT = 100;
}

package com.skopincev.testtask_listat.presenter;

import com.skopincev.testtask_listat.model.Data;
import com.skopincev.testtask_listat.view.CalculationView;

import java.util.List;

/**
 * Created by skopi on 04.10.2017.
 */

public interface CalculationPresenter {
    void attach(CalculationView view);
    void detach();

    void startCalculation();
    void pauseDisplaying();
    void resumeDisplaying();
    void finishCalculation();
}

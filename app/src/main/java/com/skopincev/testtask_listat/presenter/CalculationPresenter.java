package com.skopincev.testtask_listat.presenter;

import com.skopincev.testtask_listat.model.Data;
import com.skopincev.testtask_listat.view.CalculationView;

import java.util.List;

/**
 * Created by skopi on 04.10.2017.
 * Describe presenter's functionality
 */

public interface CalculationPresenter {
    void attach(CalculationView view);
    void detach();

    void displayData(Data data);

    void startCalculation();
    void pauseDisplaying();
    void resumeDisplaying();
    void finishCalculation();
}

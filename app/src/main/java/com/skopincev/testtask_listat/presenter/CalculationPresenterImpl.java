package com.skopincev.testtask_listat.presenter;

import com.skopincev.testtask_listat.model.CalculationThread;
import com.skopincev.testtask_listat.model.CalculationThreadImpl;
import com.skopincev.testtask_listat.view.CalculationView;

import java.util.List;

/**
 * Created by skopi on 04.10.2017.
 */

public class CalculationPresenterImpl implements CalculationPresenter {

    private List<CalculationThread> mCalculators;

    private CalculationView mCalculationView;
    private BusThread mBusThread;
    private StoringThread mStoringThread;

    public CalculationPresenterImpl(){
    }

    @Override
    public void attach(CalculationView view) {
        mCalculationView = view;
        mStoringThread = new StoringThreadImpl(view);
        mBusThread = new BusThreadImpl(mStoringThread);
    }

    @Override
    public void detach() {
        mCalculationView = null;
    }

    private void initCalculators() {

        CalculationThread calculationThread3 = new CalculationThreadImpl(3, 20, 30, mBusThread);
        calculationThread3.startCalculating();

        CalculationThread calculationThread4 = new CalculationThreadImpl(4, 30, 40, mBusThread);
        calculationThread4.startCalculating();

        CalculationThread calculationThread1 = new CalculationThreadImpl(1, 1, 10, mBusThread);
        calculationThread1.startCalculating();

        CalculationThread calculationThread2 = new CalculationThreadImpl(2, 10, 20, mBusThread);
        calculationThread2.startCalculating();

    }

    @Override
    public void startCalculation() {
        mBusThread.run();
        initCalculators();
    }

    @Override
    public void pauseDisplaying() {
        // FIXME: 05.10.2017
        mStoringThread.disconnect();
    }

    @Override
    public void resumeDisplaying() {
        // FIXME: 05.10.2017
        mStoringThread.connect();
    }

    @Override
    public void finishCalculation() {

    }
}

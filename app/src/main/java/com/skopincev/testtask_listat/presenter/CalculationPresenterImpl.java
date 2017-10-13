package com.skopincev.testtask_listat.presenter;

import android.content.res.AssetManager;
import android.icu.util.RangeValueIterator;
import android.util.Log;

import com.skopincev.testtask_listat.BundleConst;
import com.skopincev.testtask_listat.model.CalculationThread;
import com.skopincev.testtask_listat.model.CalculationThreadImpl;
import com.skopincev.testtask_listat.model.Data;
import com.skopincev.testtask_listat.ui.activity.CalculationActivity;
import com.skopincev.testtask_listat.view.CalculationView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.icu.util.RangeValueIterator.*;

/**
 * Created by skopi on 04.10.2017.
 */

public class CalculationPresenterImpl implements CalculationPresenter {

    private static final String TAG = CalculationPresenterImpl.class.getSimpleName();

    private class ThreadData{

        public int id;
        public int min;
        public int max;

        public ThreadData(int id, int min, int max){
            this.id = id;
            this.min = min;
            this.max = max;
        }
    }

    private List<CalculationThread> mCalculators;

    private CalculationView mCalculationView;
    private BusThread mBusThread;
    private StoringThread mStoringThread;

    public CalculationPresenterImpl(){

    }

    @Override
    public void attach(CalculationView view) {
        mCalculationView = view;
        mStoringThread = new StoringThreadImpl(this);
        mBusThread = new BusThreadImpl(mStoringThread);
    }

    @Override
    public void detach() {
        mCalculationView = null;
    }

    private List<ThreadData> readThreads() {
        List<ThreadData> threads = new ArrayList<>();

        AssetManager assetManager = ((CalculationActivity)mCalculationView).getAssets();
        byte[] buffer = null;
        InputStream inputStream;

        //Read XML file
        try {
            inputStream = assetManager.open(BundleConst.THREADS_FILENAME);
            int size = inputStream.available();
            buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
        } catch (IOException e) {
            Log.d(TAG, "readThreads: " + e.getMessage());
        }

        //Parse XML string
        if (buffer != null) {
            String xmlText = new String(buffer);

            while (xmlText.contains("<interval>")){
                //Read thread parameters
                int idTagStart = xmlText.indexOf("<id>");
                int idTagEnd = xmlText.indexOf("</id>");

                int threadId = Integer.parseInt(xmlText.substring(idTagStart + 4, idTagEnd).trim());

                int minTagStart = xmlText.indexOf("<low>");
                int minTagEnd = xmlText.indexOf("</low>");

                int min = Integer.parseInt(xmlText.substring(minTagStart + 5, minTagEnd).trim());

                int maxTagStart = xmlText.indexOf("<high>");
                int maxTagEnd = xmlText.indexOf("</high>");

                int max = Integer.parseInt(xmlText.substring(maxTagStart + 6, maxTagEnd).trim());

                //Create ThreadData with current thread params
                ThreadData thread = new ThreadData(threadId, min, max);
                threads.add(thread);

                //Delete current interval from xml file
                int intervalTagStart = xmlText.indexOf("<interval>");
                int intervalTagEnd = xmlText.indexOf("</interval>");

                xmlText = new StringBuilder(xmlText)
                        .delete(intervalTagStart, intervalTagEnd + 11)
                        .toString();
            }
        }

        return threads;
    }

    private void initCalculators() {
        List<ThreadData> threads = readThreads();

        for (ThreadData thread: threads){
            CalculationThread calculationThread = new CalculationThreadImpl(
                    thread.id,
                    thread.min,
                    thread.max,
                    mBusThread);
            calculationThread.startCalculating();
        }
    }

    private void stopCalculators() {
        for (CalculationThread calculationThread: mCalculators){
            if (calculationThread != null){
                calculationThread.stopCalculating();
            }
        }
    }

    @Override
    public void startCalculation() {
        mBusThread.run();
        initCalculators();
    }

    @Override
    public void displayData(Data data) {
        mCalculationView.displayData(data);
    }

    @Override
    public void pauseDisplaying() {
        mStoringThread.disconnect();
    }

    @Override
    public void resumeDisplaying() {
        mStoringThread.connect();
    }

    @Override
    public void finishCalculation() {
        stopCalculators();
        mBusThread.stopPullingNumbers();
    }
}

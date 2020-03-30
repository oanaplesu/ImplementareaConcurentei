package com.company;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class BakersPool {
    private ThreadPoolExecutor poolType1;
    private ThreadPoolExecutor poolType2;
    private ThreadPoolExecutor poolType3;

    public BakersPool() {
        poolType1 = (ThreadPoolExecutor) Executors.newFixedThreadPool(ProgramOptions.NUMBER_OF_BAKERS_TYPE1);
        poolType2 = (ThreadPoolExecutor) Executors.newFixedThreadPool(ProgramOptions.NUMBER_OF_BAKERS_TYPE2);
        poolType3 = (ThreadPoolExecutor) Executors.newFixedThreadPool(ProgramOptions.NUMBER_OF_BAKERS_TYPE3);
    }

    public void destroy() {
        poolType1.shutdownNow();
        poolType2.shutdownNow();
        poolType3.shutdownNow();
    }

    // blocks until cake preparation is ready
    boolean prepareCake(CakeOrder order) {
        boolean prepared = true;
        Future<?> base        = poolType1.submit(new BakerThread(order.getBasePrepMSec()));
        Future<?> cream       = poolType2.submit(new BakerThread(order.getCreamPrepMSec()));
        Future<?> decorations = poolType3.submit(new BakerThread(order.getDecorationsPrepMSec()));

        try {
            base.get();
            cream.get();
            decorations.get();
        } catch (InterruptedException | ExecutionException e) {
            prepared = false;
        }

        return prepared;
    }

}

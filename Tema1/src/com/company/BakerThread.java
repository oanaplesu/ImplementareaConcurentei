package com.company;

public class BakerThread implements Runnable {
    private long prepTimeMSec;

    public BakerThread(long prepTimeMSec) {
        this.prepTimeMSec = prepTimeMSec;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(prepTimeMSec);
        } catch (InterruptedException ignored) {
        }
    }
}

package com.company;

import java.util.concurrent.atomic.AtomicBoolean;

public class DeliveryManThread extends Thread {
    private int id;
    private AtomicBoolean running = new AtomicBoolean(true);
    private DeliveryQueue deliveryQueue;

    public DeliveryManThread(int id, DeliveryQueue deliveryQueue) {
        this.id = id;
        this.deliveryQueue = deliveryQueue;
        System.out.println("[DeliveryManThread #" + id + "] created");
    }

    public void stopThread() {
        running.set(false);
    }

    public boolean deliver(long deliverMin) {
        long deliveryMSec = deliverMin * 60 * 1000;

        try {
            Thread.sleep(deliveryMSec);
            return true;
        } catch (InterruptedException ignored) {
        }

        return false;
    }

    @Override
    public void run() {
        while (running.get()) {
            CakeOrder order = deliveryQueue.getNextOrder();
            if(order != null && running.get()) {
                System.out.println("[DeliveryManThread #" + id + "] Am preluat comanda '" + order.getName() + "'");
                if(deliver(order.getDeliveryMin())) {
                    System.out.println("[DeliveryManThread #" + id + "] Am terminat de livrat comanda '" + order.getName() + "'");
                }
            }
        }
    }
}

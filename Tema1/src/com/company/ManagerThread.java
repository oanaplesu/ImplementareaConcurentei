package com.company;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class ManagerThread extends Thread {
    private int id;
    private AtomicBoolean running;
    private OrdersQueue ordersQueue;
    private DeliveryQueue deliveryQueue;
    BakersPool bakersPool;

    public ManagerThread(int id, OrdersQueue ordersQueue, DeliveryQueue deliveryQueue, BakersPool bakersPool) {
        this.id = id;
        this.running = new AtomicBoolean(true);
        this.ordersQueue = ordersQueue;
        this.deliveryQueue = deliveryQueue;
        this.bakersPool = bakersPool;
        System.err.println("[ManagerThread #" + id + "] created");
    }

    public void stopThread() {
        running.set(false);
    }

    @Override
    public void run() {
        while(running.get()) {
            CakeOrder order = ordersQueue.tryGetNextOrder(5, TimeUnit.SECONDS);
            if (order == null) {
                continue;
            }

            System.err.println("[ManagerThread #" + id + "] Am preluat comanda '" + order.getName() + "'");
            boolean prepared = bakersPool.prepareCake(order);
            if(prepared && running.get()) {
                System.err.println("[ManagerThread #" + id + "] A fost preparata comanda '" + order.getName() + "'");
                deliveryQueue.addNewOrder(order);
                System.err.println("[ManagerThread #" + id + "] A fost data spre livrare comanda '" + order.getName() + "'");
            }
        }
    }
}

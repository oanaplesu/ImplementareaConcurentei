package com.company;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class OrdersQueue {
    ArrayBlockingQueue<CakeOrder> orders;

    public OrdersQueue() {
        orders = new ArrayBlockingQueue<>(ProgramOptions.ORDER_QUEUE_SIZE);
    }

    public boolean tryAddNewOrder(CakeOrder order, int timeout, TimeUnit unit) {
        try {
            return orders.offer(order, timeout, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    public void addNewOrder(CakeOrder order) throws InterruptedException {
        orders.put(order);
    }

    public CakeOrder tryGetNextOrder(int timeout, TimeUnit unit) {
        try {
            return orders.poll(timeout, unit);
        } catch (InterruptedException e) {
            return null;
        }
    }

    public CakeOrder getNextOrder() {
        try {
            return orders.take();
        } catch (InterruptedException e) {
            return null;
        }
    }
}

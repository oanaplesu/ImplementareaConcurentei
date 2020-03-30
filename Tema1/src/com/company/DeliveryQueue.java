package com.company;

public class DeliveryQueue {
    CakeOrder order = null;

    final Object queueIsEmpty = new Object();
    final Object queueIsFull  = new Object();
    final Object addMutex     = new Object();
    final Object getMutex     = new Object();


    public DeliveryQueue() {
    }

    public boolean addNewOrder(CakeOrder newOrder) {
        synchronized (addMutex) {
            synchronized (queueIsFull) {
                setOrder(newOrder);
                queueIsFull.notify();
            }

            try {
                synchronized (queueIsEmpty) {
                    while (!isQueueEmpty()) {
                        queueIsEmpty.wait();
                    }
                }
                return true;
            } catch (InterruptedException ignored) {
            }

            return false;
        }
    }

    public CakeOrder getNextOrder() {
        synchronized (getMutex) {
            try {
                synchronized (queueIsFull) {
                    while (!isQueueFull()) {
                        queueIsFull.wait();
                    }
                }
            } catch (InterruptedException e) {
                return null;
            }

            CakeOrder currentOrder;

            synchronized (queueIsEmpty) {
                currentOrder = getOrder();
                queueIsEmpty.notify();
            }

            return currentOrder;
        }
    }

    private synchronized boolean isQueueEmpty() {
        return this.order == null;
    }

    private synchronized boolean isQueueFull() {
        return this.order != null;
    }

    private synchronized void setOrder(CakeOrder order) {
        this.order = order;
    }

    private synchronized CakeOrder getOrder() {
        CakeOrder currentOrder = this.order;
        this.order = null;
        return currentOrder;
    }
}

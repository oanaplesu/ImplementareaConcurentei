package com.company;

import java.util.ArrayList;

public class BakerySimulation {
    private OrdersQueue                  ordersQueue;
    private DeliveryQueue                deliveryQueue;
    private BakersPool                   backersPool;
    private ArrayList<ManagerThread>     managerThreads;
    private ArrayList<DeliveryManThread> deliveryManThreads;

    public BakerySimulation() {
        this.ordersQueue        = new OrdersQueue();
        this.deliveryQueue      = new DeliveryQueue();
        this.backersPool        = new BakersPool();
        this.managerThreads     = new ArrayList<>();
        this.deliveryManThreads = new ArrayList<>();
    }

    private void startThreads() {
        // start manager threads
        for(int i = 0; i < ProgramOptions.NUMBER_OF_MANAGERS; i++) {
            ManagerThread newThread = new ManagerThread(i, ordersQueue, deliveryQueue, backersPool);
            managerThreads.add(newThread);
            newThread.start();
        }

        //start delivery man threads
        for(int i = 0; i < ProgramOptions.NUMBER_OF_DELIVERY_MANS; i++) {
            DeliveryManThread newThread = new DeliveryManThread(i, deliveryQueue);
            deliveryManThreads.add(newThread);
            newThread.start();
        }
    }

    private void stopThreads() {
        //stop manager threads
        for(int i = 0; i < ProgramOptions.NUMBER_OF_MANAGERS; i++) {
            managerThreads.get(i).interrupt();
            managerThreads.get(i).stopThread();
        }

        //stop delivery man threads
        for(int i = 0; i < ProgramOptions.NUMBER_OF_DELIVERY_MANS; i++) {
            deliveryManThreads.get(i).interrupt();
            deliveryManThreads.get(i).stopThread();
        }

        backersPool.destroy();
    }

    private void waitThreads() {
        // wait for the manager threads to stop
        for(int i = 0; i < ProgramOptions.NUMBER_OF_MANAGERS; i++) {
            try {
                managerThreads.get(i).join();
            } catch (InterruptedException ignored) {
            }
        }

        // wait for the delivery man threads to stop
        for(int i = 0; i < ProgramOptions.NUMBER_OF_DELIVERY_MANS; i++) {
            try {
                deliveryManThreads.get(i).join();
            } catch (InterruptedException ignored) {
            }
        }
    }

    public void start() {
        startThreads();

        PhoneResponsible phoneResponsible = new PhoneResponsible(ordersQueue);
        phoneResponsible.start();
        try {
            phoneResponsible.join();
        } catch (InterruptedException ignored) {
        }

        stopThreads();
        waitThreads();
    }
}

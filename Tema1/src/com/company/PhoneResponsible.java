package com.company;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class PhoneResponsible extends Thread {
    final static int NEW_ORDER_OPT = 1;
    final static int STOP_OPT      = 2;
    private OrdersQueue ordersQueue;

    public PhoneResponsible(OrdersQueue ordersQueue) {
        this.ordersQueue = ordersQueue;
    }

    private void readOrder() {
        Scanner in = new Scanner(System.in);
        String name;
        long basePrepMSec;
        long creamPrepMSec;
        long decorationsPrepMSec;
        long deliveryMin;

        System.out.print("Nume: ");
        name = in.nextLine();
        System.out.print("Timp preparare blat(msec): ");
        basePrepMSec = in.nextLong();
        System.out.print("Timp preparare crema(msec): ");
        creamPrepMSec = in.nextLong();
        System.out.print("Timp preparare decoratiuni(msec): ");
        decorationsPrepMSec = in.nextLong();
        System.out.print("Timp livrare comanda(min): ");
        deliveryMin = in.nextLong();

        CakeOrder order = new CakeOrder(name, basePrepMSec, creamPrepMSec, decorationsPrepMSec, deliveryMin);
        boolean added = ordersQueue.tryAddNewOrder(order, 5, TimeUnit.SECONDS);
        if(added) {
            System.out.println("Comanda a fost plasata cu succes!");
        } else {
            System.out.print("Momentan nu avem personal disponibil pentru a se ocupa de comanda dumneavoastra." +
                    "Asteptati pana cand cineva este disponibil? (da/nu) :");
            String answer = in.next();
            if(answer.equals("da")) {
                try {
                    System.out.println("Asteptati..");
                    ordersQueue.addNewOrder(order);
                    System.out.println("Comanda a fost plasata cu succes!");
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    @Override
    public void run() {
        Scanner in = new Scanner(System.in);
        boolean stop = false;

        while(!stop) {
            System.out.println("Optiuni disponibile:");
            System.out.println("1. Introduceti o noua comanda");
            System.out.println("2. Terminati executia");
            System.out.print("Optiune: ");
            int opt = in.nextInt();

            switch (opt) {
                case NEW_ORDER_OPT:
                    readOrder();
                    break;
                case STOP_OPT:
                    stop = true;
                    break;
                default:
                    System.out.println("Introduceti o optiune valida");
                    break;
            }
        }
    }
}

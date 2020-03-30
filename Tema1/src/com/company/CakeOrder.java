package com.company;

public class CakeOrder {
    private String name;
    private long basePrepMSec;
    private long creamPrepMSec;
    private long decorationsPrepMSec;
    private long deliveryMin;

    public CakeOrder(String name, long basePrepMSec, long creamPrepMSec, long decorationsPrepMSec, long deliveryMin) {
        this.name = name;
        this.basePrepMSec = basePrepMSec;
        this.creamPrepMSec = creamPrepMSec;
        this.decorationsPrepMSec = decorationsPrepMSec;
        this.deliveryMin = deliveryMin;
    }

    public String getName() {
        return name;
    }

    public long getBasePrepMSec() {
        return basePrepMSec;
    }

    public long getCreamPrepMSec() {
        return creamPrepMSec;
    }

    public long getDecorationsPrepMSec() {
        return decorationsPrepMSec;
    }

    public long getDeliveryMin() {
        return deliveryMin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBasePrepMSec(long basePrepMSec) {
        this.basePrepMSec = basePrepMSec;
    }

    public void setCreamPrepMSec(long creamPrepMSec) {
        this.creamPrepMSec = creamPrepMSec;
    }

    public void setDecorationsPrepMSec(long decorationsPrepMSec) {
        this.decorationsPrepMSec = decorationsPrepMSec;
    }

    public void setDeliveryMin(long deliveryMin) {
        this.deliveryMin = deliveryMin;
    }
}

package com.study.my.bike;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public final class BikeShop {
    private String shopName;

    /**
     * the remaining count of bike in this shop
     */
    private ArrayList<BikeBean> bikes;

    /**
     * the lock to wait for the factory
     */
    private CountDownLatch countDownLatch;

    public BikeShop(final ArrayList<BikeBean> bikes, final String shopName) {
        this.bikes = bikes;
        this.shopName = shopName;
        this.countDownLatch = new CountDownLatch(1);
    }

    /**
     * sell a bike
     * @return
     */
    public boolean sellOneBike() {
        if (bikes != null && bikes.size() > 0) {
            bikes.remove(bikes.size() - 1);
            Log.d("sellOneBike", shopName + ": One bike has been selled.");
            return true;
        } else {
            Log.d("sellOneBike", shopName + ": No bike can be selled.");
            return false;
        }
    }

    public void sellBike(final BikeStore bikeStore) {
        new Thread() {
            @Override
            public void run() {
                for (; ; ) {
                    sellOneBike();
                    if (bikes == null || bikes.size() <= 0) {
                        ArrayList bikesInStore = bikeStore.getBikes(5, BikeShop.this);
                        if (bikes == null || bikes.size() <= 0) { // if there is no bikes in the shop
                            Log.d("BikeShop->sellBike", shopName + ": There is no bike now. ");
                            waitForCreate();
                            bikesInStore = bikeStore.getBikes(5, BikeShop.this);
                            bikes.addAll(bikesInStore);
                        } else {
                            bikes.addAll(bikesInStore);
                        }
                    }

                    try {
                        Random random = new Random();
                        int timeLong = random.nextInt(10); // get a random number (>=0 and < 10)
                        sleep(timeLong + 1);
                    } catch (InterruptedException e) {
                        Log.e(shopName + ": sellBike - Exception:", e.getMessage());
                    }

                }
            }
        }.start();
    }

    public void requestBikes(final BikeStore bikeStore) {
        new Thread() {
            @Override
            public void run() {
                for (; ;) {
                    if (bikes == null || bikes.size() <= 0) {
                        bikes = new ArrayList<>();
                    }

                    Log.d("BikeShop->requestBikes", shopName + ": Try to request 5 bikes from store");
                    ArrayList bikesInStore = bikeStore.getBikes(5, BikeShop.this);
                    if (bikesInStore == null || bikesInStore.size() <= 0) { // if there is no bikes in the store
                        Log.d("BikeShop->requestBikes", shopName + ": Now there is no bike in store. Please wait!");
                        //waitForCreate();
                        //bikesInStore = bikeStore.getBikes(5);
                        //bikes.addAll(bikesInStore);
                    } else {
                        bikes.addAll(bikesInStore);
                        getNotified();
                    }

                    try {
                        sleep(20000);
                    } catch (InterruptedException e) {
                        Log.e("askBikes - Exception:", e.getMessage());
                    }
                }
            }
        }.start();
    }


    /**
     * wait for the factory to create
     */
    public void waitForCreate() {
        try {
            Log.d("BikeStore->waitCreate", "wait for bikes. The factory is creating.");
            countDownLatch = new CountDownLatch(1);
            countDownLatch.await();
        } catch (InterruptedException e) {
            //do something
        }
    }

    /**
     * wait for the factory to create
     */
    public void getNotified() {
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
    }
}

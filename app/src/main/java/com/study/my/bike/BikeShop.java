package com.study.my.bike;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public final class BikeShop {

    /**
     * the remaining count of bike in this shop
     */
    private ArrayList<BikeBean> bikes;

    public BikeShop(final ArrayList<BikeBean> bikes) {
        this.bikes = bikes;
    }

    /**
     * sell a bike
     * @return
     */
    public boolean sellOneBike() {
        if (bikes != null && bikes.size() > 0) {
            bikes.remove(bikes.size() - 1);
            Log.d("sellOneBike", "One bike has been selled.");
            return true;
        } else {
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
                        bikes = bikeStore.getBikes(5);
                        if (bikes == null || bikes.size() <= 0) { // if there is no bikes in the shop
                            bikeStore.waitCreate();
                        }
                    }

                    try {
                        Random random = new Random();
                        int timeLong = random.nextInt();
                        sleep(timeLong);
                    } catch (InterruptedException e) {
                        Log.e("sellBike - Exception:", e.getMessage());
                    }

                }
            }
        }.start();
    }

    public void askBikes(final BikeStore bikeStore) {
        new Thread() {
            @Override
            public void run() {
                for (; ;) {
                    if (bikes == null || bikes.size() <= 0) {
                        bikes = new ArrayList<>();
                    }
                    ArrayList bikesInStore = bikeStore.getBikes(5);
                    if (bikesInStore == null || bikesInStore.size() <= 0) { // if there is no bikes in the store
                        bikeStore.waitCreate();
                        bikesInStore = bikeStore.getBikes(5);
                        bikes.addAll(bikesInStore);
                    } else {
                        bikes.addAll(bikesInStore);
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
}

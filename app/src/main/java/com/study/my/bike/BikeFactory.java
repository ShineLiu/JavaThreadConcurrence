package com.study.my.bike;

import android.util.Log;

import java.util.UUID;

public class BikeFactory {
    /**
     * create a bike and store it
     */
    public static void createBike(final BikeStore store) {
        new Thread() {
            @Override
            public void run() {
                for (;;) {
                    String id = UUID.randomUUID().toString();
                    BikeBean bike = new BikeBean(id);
                    bike.setName(id.toUpperCase());

                    store.storeBike(bike);
                    Log.d("BikeFactory->createBike", "create a bike.");
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        Log.e("createBike - Exception:", e.getMessage());
                    }
                }
            }
        }.start();
    }
}

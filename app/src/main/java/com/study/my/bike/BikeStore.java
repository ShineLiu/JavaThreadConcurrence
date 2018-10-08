package com.study.my.bike;

import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class BikeStore {
    /**
     * all bikes in the store
     */
    private ArrayList<BikeBean> bikeBeans;

    /**
     * the lock to wait for the factory
     */
    private CountDownLatch countDownLatch;

    public BikeStore() {
        if (bikeBeans == null) {
            bikeBeans = new ArrayList<>();
        }
        if (countDownLatch == null) {
            countDownLatch = new CountDownLatch(1);
        }
    }

    /**
     * get the bike count in the store
     * @return
     */
    public int getBikeCount() {
        if (bikeBeans == null) {
            return 0;
        }
        return bikeBeans.size();
    }

    /**
     * store a bike here
     * @param bikeBean the bike to store
     */
    synchronized public void storeBike(final BikeBean bikeBean) {
        if (bikeBeans == null) {
            bikeBeans = new ArrayList<>();
        }
        bikeBeans.add(bikeBean);
    }

    /**
     * store a bike here
     * @param count
     */
    synchronized public ArrayList<BikeBean> getBikes(final int count) {
        if (bikeBeans == null || bikeBeans.size() <= 0) {
            return null;
        }
        ArrayList<BikeBean> bikes = new ArrayList<>();
        int availableBikes = bikeBeans.size() <= count ? bikeBeans.size() : count;
        for (int i = bikeBeans.size() - 1; i>= bikeBeans.size() - availableBikes; i --) {
            bikes.add(bikeBeans.remove(i));
        }

        Log.d("BikeStore->getBikes", "get " + availableBikes + " bikes from the store");
        return bikes;
    }

    /**
     * wait for the factory to create
     */
    public void waitCreate() {
        try {
            Log.d("BikeStore->waitCreate", "wait for bikes. The factory is creating.");
            countDownLatch.await();
        } catch (InterruptedException e) {
            //do something
        }
    }

    /**
     * wait for the factory to create
     */
    public void tellAll() {
        countDownLatch.countDown();
    }
}

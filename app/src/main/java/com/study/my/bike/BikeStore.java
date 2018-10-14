package com.study.my.bike;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;

public class BikeStore {
    /**
     * all bikes in the store
     */
    private ArrayList<BikeBean> bikeBeans;
    private LinkedList<BikeShop> bikeShops;

    public BikeStore() {
        if (bikeBeans == null) {
            bikeBeans = new ArrayList<>();
        }

        if (bikeShops == null) {
            bikeShops = new LinkedList<>();
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

        if (bikeShops != null) {
            bikeShops.removeFirst().getNotified();
        }
    }

    /**
     * store a bike here
     * @param count
     */
    synchronized public ArrayList<BikeBean> getBikes(final int count, final BikeShop bikeShop) {
        if (bikeBeans == null || bikeBeans.size() <= 0) {
            if (bikeShops != null && !bikeShops.contains(bikeShop)) {
                bikeShops.add(bikeShop);
            }
            return null;
        }
        ArrayList<BikeBean> bikes = new ArrayList<>();
        int bikeSize = bikeBeans.size();
        int availableBikes = bikeSize <= count ? bikeSize : count;
        Log.d("BikeStore->getBikes", "get " + availableBikes + " bikes from the store");
        for (int i = bikeSize - 1; i >= bikeSize - availableBikes && i > 0; i --) {
            bikes.add(bikeBeans.remove(i));
        }

        //Log.d("BikeStore->getBikes", "get " + availableBikes + " bikes from the store");
        return bikes;
    }
}

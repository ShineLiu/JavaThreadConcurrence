package com.study.my.mystudy;

import android.util.Log;

import com.study.my.bike.BikeBean;
import com.study.my.bike.BikeFactory;
import com.study.my.bike.BikeShop;
import com.study.my.bike.BikeStore;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void testProduceAndSellBikes() {
        produceAndSellBikes();
    }

    private void produceAndSellBikes() {
        final BikeStore bikeStore = new BikeStore();
        final BikeShop bikeShopA = new BikeShop(new ArrayList<BikeBean>(), "ShopA");
        final BikeShop bikeShopB = new BikeShop(new ArrayList<BikeBean>(), "ShopB");

        new Thread() {
            @Override
            public void run() {
                Log.d("Run", "Bike factory start to create bikes.");
                BikeFactory.createBike(bikeStore);
                try {
                    sleep(8000);
                } catch (InterruptedException e) {
                    Log.e("createBike - Exception:", e.getMessage());
                }

                bikeShopA.requestBikes(bikeStore);
                bikeShopA.sellBike(bikeStore);

                bikeShopB.requestBikes(bikeStore);
                bikeShopB.sellBike(bikeStore);
            }
        }.start();
    }
}
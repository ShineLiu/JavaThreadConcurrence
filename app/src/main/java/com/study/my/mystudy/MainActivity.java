package com.study.my.mystudy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.study.my.bike.BikeBean;
import com.study.my.bike.BikeFactory;
import com.study.my.bike.BikeShop;
import com.study.my.bike.BikeStore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

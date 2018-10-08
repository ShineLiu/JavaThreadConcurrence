package com.study.my.bike;

public final class BikeBean {

    /**
     * the bike's id
     */
    private String id;

    /**
     * the bike's name
     */
    private String name;

    /**
     * the bike's price
     */
    private float price;

    public BikeBean() {
    }

    public BikeBean(String id) {
        this.id = id;
    }

    /**
     * get the name of this bike
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * set the name of this bike
     * @return
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get the price of this bike
     * @return
     */
    public float getPrice() {
        return price;
    }

    /**
     * set the price of this bike
     * @return
     */
    public void setPrice(float price) {
        this.price = price;
    }
}

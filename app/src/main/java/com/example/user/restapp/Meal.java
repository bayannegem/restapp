package com.example.user.restapp;

/**
 * Created by USER on 25/03/2018.
 */

public class Meal {
    private String name;
    private String desc;
    private String price;
    private String image;
    private int calories;

    public Meal() {
    }

    public Meal(String name, String desc, String price, String image, int calories) {
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.image = image;
        this.calories = calories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", price='" + price + '\'' +
                ", image='" + image + '\'' +
                ", calories=" + calories +
                '}';
    }
}

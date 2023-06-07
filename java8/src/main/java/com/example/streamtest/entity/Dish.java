package com.example.streamtest.entity;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/3/10 10:59
 * @注释
 */
public class Dish {

    private final String name;
    private final boolean vegetarian;
    private final int calories;
    private final Type type;


    public Dish(String name, boolean vegetarian, int calories, Type type) {
        this.name = name;
        this.vegetarian = vegetarian;
        this.calories = calories;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public int getCalories() {
        return calories;
    }

    public Type getType() {
        return type;
    }

    public enum Type {MEAT,FISH,OTHER}


    public CaloricLevel getCaloricLevel(){
        if (this.getCalories() <= 400) {
            return CaloricLevel.DIET;
        } else if (this.getCalories() <= 700) {
            return CaloricLevel.NORMAL;
        } else {
            return CaloricLevel.FAT;
        }
    }
}

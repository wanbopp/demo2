package com.example.demo.model.po;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/3/10 10:59
 * @注释
 */
@Data
@NoArgsConstructor
public class Dish {

    private  String name;
    private  boolean vegetarian;
    private  int calories;
    private  Type type;


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


}

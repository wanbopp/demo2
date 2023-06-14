package com.example.streamtest.optional;

import java.util.Optional;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/6/13 19:17
 * @注释
 */
public class Car {
    private Optional<Insurance> insurance;
    public Optional<Insurance> getInsurance() { return insurance; }
}

package com.example.streamtest.defaultmethod;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/6/12 19:55
 * @注释 旋转接口
 */
public interface Rotatable {

    void setRotationAngle(int angleInDegrees);

    int getRotationAngle();

    //rotateBy方法是一个默认方法，它会将角度加到当前的旋转角度上
    default void rotateBy(int angleInDegrees){
        setRotationAngle((getRotationAngle() + angleInDegrees) % 360);
    }
}

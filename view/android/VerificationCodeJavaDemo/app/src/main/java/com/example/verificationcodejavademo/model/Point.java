package com.example.verificationcodejavademo.model;

import java.io.Serializable;

/**
 * Date:2020/5/18
 * author:wuyan
 */
public class Point implements Serializable {
    private double x;
    private double y;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}

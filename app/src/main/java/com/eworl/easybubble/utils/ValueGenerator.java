package com.eworl.easybubble.utils;

import android.util.Log;

/**
 * Created by root on 3/2/17.
 */

public class ValueGenerator {
    private String TAG = ValueGenerator.class.getCanonicalName();
    private int count;
    private Coordinate center;
    private double angleDifference;
    private int radius = 200;

    public ValueGenerator(Coordinate center, int width, int count) {
        this.count = count;
        this.center = center;
        angleDifference = 360 / count;
        calculateRadius(width);
    }

    private void calculateRadius(int width) {
        radius = (int) ((count * width) / (3.14 * 1.3));
    }

    public Coordinate getCoordinatesFor(int index) {
        Coordinate coordinate = new Coordinate();
        double x = center.getX() + radius * Math.cos(getAngleFor(index));
        double y = center.getY() + radius * Math.sin(getAngleFor(index));
        coordinate.set(x, y);
        return coordinate;
    }


    private Double getAngleFor(int index) {
        Double angle = Math.toRadians(angleDifference * index);
        Log.d(TAG, "getAngleFor: " + index + " is " + angle);
        return angle;
    }

}

package com.eworl.easybubble.utils;

import android.content.Context;
import android.util.Log;

import com.eworl.easybubble.R;

/**
 * Created by root on 3/2/17.
 */

public class ValueGenerator {
    private String TAG = ValueGenerator.class.getCanonicalName();
    private int count;
    private double angleDifference;
    private int radius;
    private int subBubbleWidth;
    private Context context;
    private float SEPRATION_FRACTION = 1.2f;
    private int masterBubbleWidth;

    public ValueGenerator(Context context, int count) {
        this.context = context;
        this.count = count;
        angleDifference = 360 / count;
        calculateRadius();
    }

    private void calculateRadius() {
        subBubbleWidth = (int) context.getResources().getDimension(R.dimen.sub_bubble_size);
        masterBubbleWidth = (int) context.getResources().getDimension(R.dimen.master_bubble_size);
        radius = (int) ((count * subBubbleWidth) / (3.14 * SEPRATION_FRACTION));
    }

    public Coordinate getCoordinatesFor(int index) {
        Coordinate coordinate = new Coordinate();
        double x = 50+radius + radius * Math.cos(getAngleFor(index));
        double y = 50+radius + radius * Math.sin(getAngleFor(index));
        coordinate.set(x, y);
        return coordinate;
    }

    private Double getAngleFor(int index) {
        Double angle = Math.toRadians(angleDifference * index);
        Log.d(TAG, "getAngleFor: " + index + " is " + angle);
        return angle;
    }

    public int getRadius() {
        return radius;
    }

    public int getSubBubbleWidth() {
        return subBubbleWidth;
    }

    public int getMasterBubbleWidth() {
        return masterBubbleWidth;
    }
}

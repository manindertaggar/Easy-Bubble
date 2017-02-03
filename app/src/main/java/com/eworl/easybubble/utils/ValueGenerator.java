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

    public ValueGenerator(Context context, int count) {
        this.context = context;
        this.count = count;
        angleDifference = 360 / count;

        calculateRadius();
    }

    private void calculateRadius() {
        subBubbleWidth = (int) context.getResources().getDimension(R.dimen.sub_bubble_size);
        radius = (int) ((count * subBubbleWidth) / (3.14 * 1.3));
    }

    public Coordinate getCoordinatesFor(int index) {
//        int centerX = subBubbleWidth / 2;
//        int centerY = subBubbleWidth / 2;

        int centerX = 0;
        int centerY = 0;

        Coordinate coordinate = new Coordinate();
        double x = centerX + radius * Math.cos(getAngleFor(index));
        double y = centerY + radius * Math.sin(getAngleFor(index));
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
}

package com.eworl.easybubble.utils;

import android.content.Context;
import android.util.Log;
import android.webkit.CookieManager;

import com.eworl.easybubble.R;
import com.eworl.easybubble.bubbles.SubBubble;
import com.eworl.easybubble.eventBus.RotateSubBubbleEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    public Double angle;
    private  float diffY;
    public ValueGenerator(Context context, int count) {
        this.context = context;
        this.count = count;
        angleDifference = 360 / count;
        calculateRadius();
        EventBus.getDefault().register(this);
    }

    private void calculateRadius() {
        subBubbleWidth = (int) context.getResources().getDimension(R.dimen.sub_bubble_size);
        masterBubbleWidth = (int) context.getResources().getDimension(R.dimen.master_bubble_size);
        radius = (int) ((count * subBubbleWidth) / (3.14 * SEPRATION_FRACTION));
    }

    public Coordinate getCoordinatesFor(int index) {
        Coordinate coordinate = new Coordinate();
        double x = radius + radius * Math.cos(getAngleFor(index));
        double y = radius + radius * Math.sin(getAngleFor(index));
        Log.d("double x"+x, "double y "+y);
        coordinate.set(x, y);
        return coordinate;
    }
    public Coordinate getUpdatedCoordinatesFor(int i) {
        Coordinate coordinate = new Coordinate();
        double x = radius + radius * Math.cos(updatedAngleFor(i));
        double y = radius + radius * Math.sin(updatedAngleFor(i));
        Log.d("double updatedx"+x, "double updatedy "+y);
        coordinate.set(x, y);
        return coordinate;
    }
//    public Coordinate getRotationCoordinatesFor(int i) {
//        Coordinate coordinate = new Coordinate();
//        double x = radius + radius * Math.cos(rotationAngleFor(i));
//        double y = radius + radius * Math.sin(rotationAngleFor(i));
//        Log.d("double updatedx"+x, "double updatedy "+y);
//        coordinate.set(x, y);
//        return coordinate;
//    }

    private Double getAngleFor(int index) {
         angle = Math.toRadians((angleDifference * index));
        Log.d(TAG, "getAngleFor: " + index + " is " + angle);
        return angle;
    }

    private Double updatedAngleFor(int i) {

        angle = Math.toRadians((angleDifference*i)-diffY);
        Log.d(TAG, "getAngleFor: " + " is " + angle);
        return angle;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(RotateSubBubbleEvent event) {
      diffY =   event.diffY();

    }

//    private Double rotationAngleFor(int i) {
//        angle = Math.toRadians((angleDifference*i)+subBubble.getDiffY());
//        Log.d(TAG, "getAngleFor: " + " is " + angle);
//        return angle;
//    }

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

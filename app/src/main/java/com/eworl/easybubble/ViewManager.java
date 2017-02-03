package com.eworl.easybubble;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by root on 3/2/17.
 */

public class ViewManager {
    private WindowManager windowManager;
    private Context context;
    private Display display;
    private Point size = new Point();
    private int screenHeight, screenWidth;

    public ViewManager(Context context) {
        this.context = context;
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        calculateScreenDimentions();
    }

    private void calculateScreenDimentions() {
        display = windowManager.getDefaultDisplay();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
    }


    public void addView(View view, WindowManager.LayoutParams layoutParams) {
        windowManager.addView(view, layoutParams);
    }

    public void removeView(View view) {
        windowManager.removeView(view);
    }

    public void updateViewLayout(View view, WindowManager.LayoutParams layoutParams) {
        windowManager.updateViewLayout(view, layoutParams);
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int screenHeightPercentage() {
        int screenHeightPercent = getScreenHeight() / 100;
        return screenHeightPercent;

    }

    public int screenWidthPercentage() {
        int screenWidthPercent = getScreenWidth() / 100;
        return screenWidthPercent;
    }
}

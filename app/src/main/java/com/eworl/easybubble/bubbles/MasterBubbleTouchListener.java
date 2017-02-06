package com.eworl.easybubble.bubbles;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;

import com.eworl.easybubble.ViewManager;
import com.eworl.easybubble.eventBus.ToggleMasterBubbleEvent;
import com.eworl.easybubble.utils.ValueGenerator;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by root on 3/2/17.
 */

public class MasterBubbleTouchListener implements View.OnTouchListener {
    private static final String TAG = MasterBubbleTouchListener.class.getCanonicalName();
    private MasterBubble masterBubble;
    private View fmContentViewLayout;
    private ViewManager viewManager = ViewManager.getRunningInstance();
    private long startTime, endTime;
    private int statusBarHeight = 48;
    private  int tempRadius = 40;
    private WindowManager.LayoutParams fmContentViewParams;
    private float pointerX, pointerY;
    private int radius, screenWidth, screenHeight;


    public MasterBubbleTouchListener(MasterBubble masterBubble) {
        this.masterBubble = masterBubble;
        fmContentViewLayout = masterBubble.getView();
        fmContentViewParams = (WindowManager.LayoutParams) fmContentViewLayout.getLayoutParams();
        ValueGenerator valueGenerator = masterBubble.getValueGenerator();
        radius = valueGenerator.getRadius();
        Log.d(TAG, "masterBubbleRadius: "+radius);
        ViewManager viewManager = ViewManager.getRunningInstance();
        screenWidth = viewManager.getScreenWidth();
        screenHeight = viewManager.getScreenHeight();


    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                performeActionDown(motionEvent);
                break;
            case MotionEvent.ACTION_MOVE:
                performeActionMove(motionEvent);
                break;
            case MotionEvent.ACTION_UP:
                performeActionUp(motionEvent);
                break;
        }
        return false;
    }

    private void performeActionMove(MotionEvent motionEvent) {
        if(masterBubble.isOpen){
            masterBubble.close();
        }

        pointerX = motionEvent.getRawX();
        pointerY = motionEvent.getRawY();
        WindowManager.LayoutParams  fmContentViewParams = (WindowManager.LayoutParams) fmContentViewLayout.getLayoutParams();
        fmContentViewParams.x = (int) pointerX -tempRadius;
        fmContentViewParams.y = (int) pointerY - statusBarHeight-tempRadius;
        viewManager.updateViewLayout(fmContentViewLayout, fmContentViewParams);
    }

    private void performeActionDown(MotionEvent motionEvent) {
        EventBus.getDefault().post(new ToggleMasterBubbleEvent());

        startTime = System.currentTimeMillis();

    }

    private void performeActionUp(MotionEvent motionEvent) {
        endTime = System.currentTimeMillis();

        if (endTime - startTime < 200) {
            masterBubbleClickListener();
        }

//        if (pointerX < (screenWidth / 2)) {
//            ObjectAnimator objectAnimator = new ObjectAnimator();
//            objectAnimator.setDuration(500);
//            float init = fmContentViewParams.x;
//            objectAnimator.setFloatValues(init, 0f);
//            objectAnimator.setInterpolator(new OvershootInterpolator());
//            objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    float value = (float) animation.getAnimatedValue();
//                    float current = (int) value;
//                    fmContentViewParams.x = (int) current;
//                    fmContentViewParams.y = (int) (pointerY - statusBarHeight - radius);
//                    viewManager.updateViewLayout(masterBubbleView, fmContentViewParams);
//                }
//            });
//            objectAnimator.start();
//        } else {
//            ObjectAnimator objectAnimator = new ObjectAnimator();
//            objectAnimator.setDuration(500);
//            float initial = fmContentViewParams.x;
//            float finalV = (float) (screenWidth - 2 * radius);
//            objectAnimator.setFloatValues(initial, finalV);
//            objectAnimator.setInterpolator(new OvershootInterpolator());
//            objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    float value = (float) animation.getAnimatedValue();
//                    float current = (int) value;
//                    fmContentViewParams.x = (int) current;
//                    fmContentViewParams.y = (int) (pointerY - statusBarHeight - radius);
//                    viewManager.updateViewLayout(masterBubbleView, fmContentViewParams);
//
//                }
//            });
//
//            objectAnimator.start();
//        }


    }

    private void masterBubbleClickListener() {
        masterBubble.toggle();

    }


}

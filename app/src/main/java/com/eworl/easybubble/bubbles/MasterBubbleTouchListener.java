package com.eworl.easybubble.bubbles;

import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.eworl.easybubble.ViewManager;
import com.eworl.easybubble.eventBus.ToggleMasterBubbleEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by root on 3/2/17.
 */

public class MasterBubbleTouchListener implements View.OnTouchListener {
    private static final String TAG = MasterBubbleTouchListener.class.getCanonicalName();
    private MasterBubble masterBubble;
    private View masterBubbleView;
    private ViewManager viewManager = ViewManager.getRunningInstance();

    public MasterBubbleTouchListener(MasterBubble masterBubble) {
        this.masterBubble = masterBubble;
        masterBubbleView = masterBubble.getView();
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
        EventBus.getDefault().post(new ToggleMasterBubbleEvent());

        float x = motionEvent.getRawX();
        float y = motionEvent.getRawY();
        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) masterBubbleView.getLayoutParams();
        layoutParams.x = (int) x;
        layoutParams.y = (int) y;
        viewManager.updateViewLayout(masterBubbleView, layoutParams);
    }

    private void performeActionUp(MotionEvent motionEvent) {
    }

    private void performeActionDown(MotionEvent motionEvent) {
    }


}

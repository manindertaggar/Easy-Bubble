package com.eworl.easybubble.bubbles;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by root on 3/2/17.
 */

public class MasterBubbleTouchListener implements View.OnTouchListener {
    private static final String TAG = MasterBubbleTouchListener.class.getCanonicalName();
    private MasterBubble masterBubble;

    public MasterBubbleTouchListener(MasterBubble masterBubble) {
        this.masterBubble = masterBubble;
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

    }

    private void performeActionUp(MotionEvent motionEvent) {
    }

    private void performeActionDown(MotionEvent motionEvent) {

    }
}

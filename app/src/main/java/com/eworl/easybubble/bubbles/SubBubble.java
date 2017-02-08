package com.eworl.easybubble.bubbles;

import android.content.Context;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.eworl.easybubble.R;
import com.eworl.easybubble.eventBus.ToggleMasterBubbleEvent;
import com.eworl.easybubble.utils.Coordinate;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by root on 3/2/17.
 */

public class SubBubble {
    private FrameLayout fmContentView, fmSubBubbleView;
    private ImageView ivIcon;
    private Context context;
    private int iconId;
    private Coordinate coordinates;
    private long startTime, endTime;

    public SubBubble(Context context) {
        this.context = context;
        intializeViews();
        setListeners();
    }


    private void intializeViews() {
        fmContentView = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.layout_sub_bubble, null);
        fmSubBubbleView = (FrameLayout) fmContentView.findViewById(R.id.fmSubBubbleView);
        ivIcon = (ImageView) fmContentView.findViewById(R.id.ivIcon);
    }

    private void setListeners() {
        fmSubBubbleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        performeActionDown(motionEvent);

                        break;
                    case MotionEvent.ACTION_UP:

                        performActionUp(motionEvent);

                        break;
                    case MotionEvent.ACTION_MOVE:

                        performActionMove(motionEvent);

                        break;
                }
                return false;
            }
        });


    }

    private void performActionMove(MotionEvent motionEvent) {
    }

    private void performActionUp(MotionEvent motionEvent) {
        endTime = System.currentTimeMillis();
        if (endTime - startTime < 200) {
            fmSubBubbleViewOnClick();
        }
    }

    private void performeActionDown(MotionEvent motionEvent) {
        startTime = System.currentTimeMillis();
    }

    private void fmSubBubbleViewOnClick() {
        closeMasterBubble();
        performAction();

    }

    private void closeMasterBubble() {
        //   EventBus.getDefault().post(new ToggleMasterBubbleEvent());
    }

    private void performAction() {
        Toast.makeText(context, "Action Performed", Toast.LENGTH_SHORT).show();
    }

    public void setIcon(int iconId) {
        this.iconId = iconId;
        ivIcon.setImageResource(iconId);
    }

    public View getView() {
        return fmContentView;
    }

    public Coordinate getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinate coordinates) {
        fmContentView.setX((float) coordinates.getX());
        fmContentView.setY((float) coordinates.getY());
        this.coordinates = coordinates;
    }
}

package com.eworl.easybubble.bubbles;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.eworl.easybubble.LayoutParamGenerator;
import com.eworl.easybubble.R;
import com.eworl.easybubble.ViewManager;
import com.eworl.easybubble.eventBus.ToggleMasterBubbleEvent;
import com.eworl.easybubble.utils.Coordinate;
import com.eworl.easybubble.utils.ValueGenerator;

import org.greenrobot.eventbus.EventBus;

import static android.content.ContentValues.TAG;

/**
 * Created by root on 3/2/17.
 */

public class SubBubble {
    private FrameLayout fmContentView, fmSubBubbleView;
    private ImageView ivIcon;
    private Context context;
    private int iconId;
    private Coordinate coordinates;
    private float pointerDownY, pointerDownX;
    private ValueGenerator valueGenerator;
    private MasterBubble masterBubble;
    private long startTime, endTime;
    private int radius;
    private float diffY;
    private FrameLayout.LayoutParams fmContentViewParams;



    public SubBubble(Context context) {
        this.context = context;
        masterBubble = new MasterBubble(context);
        valueGenerator = masterBubble.getValueGenerator();
        radius = valueGenerator.getRadius();
//        fmContentViewParams = (FrameLayout.LayoutParams) fmContentView.getLayoutParams();
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
                        performeActionUp(motionEvent);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        performeActionMove(motionEvent);
                        break;
                }
                return false;
            }
        });

        closeMasterBubble();
        performAction();
    }

    private void performeActionMove(MotionEvent motionEvent) {
        float x =  motionEvent.getRawX();
        float y = motionEvent.getRawY();
        diffY = pointerDownY - y;
        Log.d(TAG, "diffX: " + diffY / 10);


    }

    private void performeActionUp(MotionEvent motionEvent) {
        endTime = System.currentTimeMillis();
        if(endTime-startTime<200){
            fmSubBubbleViewOnClick();
        }
    }

    private void fmSubBubbleViewOnClick() {

//        closeMasterBubble();
                performAction();
    }

    private void performeActionDown(MotionEvent motionEvent) {
        pointerDownX = motionEvent.getRawX();
        pointerDownY = motionEvent.getRawY();
        startTime = System.currentTimeMillis();

    }

    private void closeMasterBubble() {
        EventBus.getDefault().post(new ToggleMasterBubbleEvent());
    }

    private void performAction() {

        updateSubBubble();
        Toast.makeText(context, "Action Performed", Toast.LENGTH_SHORT).show();
    }

    private void updateSubBubble() {
//        masterBubble.updateSubBubble(this);
    }

    public void setIcon(int iconId) {
        this.iconId = iconId;
        ivIcon.setImageResource(iconId);
    }

    public View getView() {
        return fmContentView;
    }

    public FrameLayout getLayout() {
        return fmContentView;
    }


    public ViewGroup.LayoutParams getParams(){

     return fmContentViewParams;
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

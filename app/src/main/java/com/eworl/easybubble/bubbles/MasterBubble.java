package com.eworl.easybubble.bubbles;

import android.animation.Animator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.eworl.easybubble.R;
import com.eworl.easybubble.ViewManager;
import com.eworl.easybubble.eventBus.ToggleMasterBubbleEvent;
import com.eworl.easybubble.utils.Coordinate;
import com.eworl.easybubble.utils.ValueGenerator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Created by root on 3/2/17.
 */

public class MasterBubble {
    private static final String TAG = MasterBubble.class.getCanonicalName();
    private FrameLayout fmContentView, fmMasterBubble, fmOpenView, fmCloseView;
    private View innerRing;
    Boolean isOpen = false;
    private FrameLayout flSubBubbleContainer;
    private Context context;
    private boolean isAnimationOngoing = false;
    private final static int ANIMATION_DURATION = 300;
    //    private final static int ANIMATION_DURATION = 0;
    private final static float BUBBLE_CLOSE_SIZE = 1f;
    private final static float BUBBLE_OPEN_SIZE = .8f;
    private ValueGenerator valueGenerator;
    private ArrayList<SubBubble> subBubblesList = new ArrayList<>();
    private MasterBubbleTouchListener touchListener;
    private ViewGroup.LayoutParams flSubBubbleContainerLayoutParams;
    private int fmContentViewRadius;
    private int screenWidth, screenHeight;
    private final static int STATUS_BAR_HEIGHT = 48;
    private ViewManager viewManager = ViewManager.getRunningInstance();


    public MasterBubble(Context context) {
        this.context = context;

        intializeValueGenerator();
        intializeViews();
        setListeners();
        EventBus.getDefault().register(this);
        ViewManager viewManager = ViewManager.getRunningInstance();
        screenWidth = viewManager.getScreenWidth();
        screenHeight = viewManager.getScreenHeight();

    }

    public void intializeValueGenerator() {
        valueGenerator = new ValueGenerator(context, 8);
        fmContentViewRadius = valueGenerator.getRadius();
    }

    public ValueGenerator getValueGenerator() {
        return valueGenerator;
    }

    private void intializeViews() {
        fmContentView = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.layout_master_bubble, null);
        flSubBubbleContainer = (FrameLayout) fmContentView.findViewById(R.id.flSubBubbleContainer);
        fmMasterBubble = (FrameLayout) fmContentView.findViewById(R.id.fmMasterBubble);
        fmOpenView = (FrameLayout) fmMasterBubble.findViewById(R.id.fmOpenView);
        fmCloseView = (FrameLayout) fmMasterBubble.findViewById(R.id.fmCloseView);
        innerRing = fmMasterBubble.findViewById(R.id.innerRing);


        setSubBubbleContainerDimentions();

    }

    private void setSubBubbleContainerDimentions() {
        flSubBubbleContainerLayoutParams = flSubBubbleContainer.getLayoutParams();
        flSubBubbleContainerLayoutParams.width = valueGenerator.getRadius() * 2 + valueGenerator.getSubBubbleWidth();
        flSubBubbleContainerLayoutParams.height = valueGenerator.getRadius() * 2 + valueGenerator.getSubBubbleWidth();
        flSubBubbleContainer.setLayoutParams(flSubBubbleContainerLayoutParams);
    }

//    private void setFmContentViewDimentions() {
//        WindowManager.LayoutParams fmContentViewParams = (WindowManager.LayoutParams) fmContentView.getLayoutParams();
//        fmContentViewParams.x =;
//    }

    private void setListeners() {

        touchListener = new MasterBubbleTouchListener(this);
        fmMasterBubble.setOnTouchListener(touchListener);
    }

    public void toggle() {
        if (isAnimationOngoing) return;

        if (isOpen)
            close();
        else
            open();
    }

    void close() {
        fmOpenView.clearAnimation();
        fmCloseView.clearAnimation();
        flSubBubbleContainer.setVisibility(View.INVISIBLE);
        isAnimationOngoing = true;
        fmCloseView.animate()
                .setDuration(ANIMATION_DURATION)
                .scaleX(BUBBLE_CLOSE_SIZE)
                .scaleY(BUBBLE_CLOSE_SIZE)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        fmOpenView.setVisibility(View.VISIBLE);
                        isAnimationOngoing = false;
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                })
                .rotation(0);
        isOpen = false;
//        setContentViewCoordinates();

    }

    void open() {
        fmOpenView.clearAnimation();
        fmCloseView.clearAnimation();

        flSubBubbleContainer.setVisibility(View.VISIBLE);
        fmOpenView.setVisibility(View.VISIBLE);
        isAnimationOngoing = true;
        fmCloseView.animate().setDuration(ANIMATION_DURATION)
                .setInterpolator(new OvershootInterpolator())
                .scaleX(BUBBLE_OPEN_SIZE)
                .scaleY(BUBBLE_OPEN_SIZE)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        isAnimationOngoing = false;

                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                }).rotation(45);

        isOpen = true;
//        setContentViewCoordinates();
    }

    private void setContentViewCoordinates() {

        WindowManager.LayoutParams fmContentViewParams = (WindowManager.LayoutParams) fmContentView.getLayoutParams();
        if (isOpen) {
            if (touchListener.getLatestPointerX() < (screenWidth / 2)) {

                fmContentViewParams.x = 0 - fmContentViewRadius;

            } else {
                fmContentViewParams.x = screenWidth - fmContentViewRadius - 100;
            }
            fmContentViewParams.y = touchListener.getLatestPointerY() - fmContentViewRadius - 100;

            viewManager.updateViewLayout(fmContentView, fmContentViewParams);

            Log.d(TAG, "setContentViewCoordinates: " + fmContentViewParams.x + " " + fmContentViewParams.y);
        }
    }


    public View getView() {
        return fmContentView;
    }

    public void addSubBubble(SubBubble subBubble) {

        int index = subBubblesList.size();
        Coordinate coordinate = valueGenerator.getCoordinatesFor(index);
        subBubble.setCoordinates(coordinate);

        flSubBubbleContainer.addView(subBubble.getView());
        subBubblesList.add(subBubble);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ToggleMasterBubbleEvent event) {
        toggle();
    }

    public Context getContext() {
        return context;
    }

    protected void init() {
        int a = fmContentView.getHeight();
        int b = fmContentView.getWidth();
        Toast.makeText(context, "" + a + " " + b, Toast.LENGTH_SHORT).show();
    }

}



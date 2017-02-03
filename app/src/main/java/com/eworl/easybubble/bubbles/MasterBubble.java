package com.eworl.easybubble.bubbles;

import android.animation.Animator;
import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;

import com.eworl.easybubble.R;
import com.eworl.easybubble.eventBus.CloseMasterBubbleEvent;
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
    private Boolean isOpen = false;
    private FrameLayout flSubBubbleContainer;
    private Context context;
    private boolean isAnimationOngoing = false;
    private final static int ANIMATION_DURATION = 300;
    private final static float BUBBLE_CLOSE_SIZE = 1f;
    private final static float BUBBLE_OPEN_SIZE = .8f;
    private ValueGenerator valueGenerator;
    private ArrayList<SubBubble> subBubblesList = new ArrayList<>();
    private MasterBubbleTouchListener touchListener;

    public MasterBubble(Context context) {
        this.context = context;

        intializeValueGenerator();
        intializeViews();
        setListeners();
        EventBus.getDefault().register(this);

    }

    private void intializeValueGenerator() {
        Coordinate center = new Coordinate();
        center.set(366, 726);
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 55, context.getResources().getDisplayMetrics());
        valueGenerator = new ValueGenerator(center, px, 8);
    }

    private void intializeViews() {
        fmContentView = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.layout_master_bubble, null);
        flSubBubbleContainer = (FrameLayout) fmContentView.findViewById(R.id.flSubBubbleContainer);
        fmMasterBubble = (FrameLayout) fmContentView.findViewById(R.id.fmMasterBubble);
        fmOpenView = (FrameLayout) fmMasterBubble.findViewById(R.id.fmOpenView);
        fmCloseView = (FrameLayout) fmMasterBubble.findViewById(R.id.fmCloseView);
        innerRing = fmMasterBubble.findViewById(R.id.innerRing);
    }

    private void setListeners() {

        fmMasterBubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAnimationOngoing) return;

                if (isOpen)
                    close();
                else
                    open();
            }
        });

        touchListener = new MasterBubbleTouchListener(this);
        fmMasterBubble.setOnTouchListener(touchListener);
    }

    private void close() {
        fmOpenView.clearAnimation();
        fmCloseView.clearAnimation();
        flSubBubbleContainer.setVisibility(View.GONE);
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
                }).rotation(0);
        isOpen = false;
    }

    private void open() {
        fmOpenView.clearAnimation();
        fmCloseView.clearAnimation();

        flSubBubbleContainer.setVisibility(View.VISIBLE);
        fmOpenView.setVisibility(View.GONE);
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
    public void onMessageEvent(CloseMasterBubbleEvent event) {
        if (isOpen)
            close();
    }

    public Context getContext() {
        return context;
    }

}


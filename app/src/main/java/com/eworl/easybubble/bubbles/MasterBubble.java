package com.eworl.easybubble.bubbles;

import android.animation.Animator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;

import com.eworl.easybubble.R;

/**
 * Created by root on 3/2/17.
 */

public class MasterBubble {
    private FrameLayout fmContentView, fmMasterBubble, fmOpenView, fmCloseView;
    private View innerRing;
    private Boolean isOpen = false;
    private Context context;
    private boolean isAnimationOngoing = false;
    private final static int ANIMATION_DURATION = 300;
    private final static float BUBBLE_CLOSE_SIZE = 0.8f;
    private final static float BUBBLE_OPEN_SIZE = 1f;

    public MasterBubble(Context context) {
        this.context = context;

        intializeViews();
        setListeners();

    }


    private void intializeViews() {
        fmContentView = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.layout_master_bubble, null);
        fmMasterBubble = (FrameLayout) fmContentView.findViewById(R.id.fmMasterBubble);
        fmOpenView = (FrameLayout) fmMasterBubble.findViewById(R.id.fmOpenView);
        fmCloseView = (FrameLayout) fmMasterBubble.findViewById(R.id.fmCloseView);
        innerRing = fmMasterBubble.findViewById(R.id.innerRing);
    }


    private void setListeners() {
        fmMasterBubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isAnimationOngoing) {
                    return;
                }

                if (isOpen) {
                    close();
                } else {
                    open();
                }
                isOpen = !isOpen;

            }
        });
    }

    private void close() {
        fmOpenView.clearAnimation();
        fmCloseView.clearAnimation();

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
    }


    private void open() {
        fmOpenView.clearAnimation();
        fmCloseView.clearAnimation();

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
    }


    public View getView() {
        return fmContentView;
    }
}


package com.eworl.easybubble;

import android.animation.Animator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;

/**
 * Created by root on 3/2/17.
 */

public class MasterBubble {
    private FrameLayout fmContentView, fmMasterBubble, fmOpenView, fmCloseView;
    private View innerRing;
    private Boolean isOpen = false;
    private Context context;
    private Boolean isAnimationOngoing = false;

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
        View innerRing = fmMasterBubble.findViewById(R.id.innerRing);
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
        fmCloseView.animate().setDuration(300).scaleX(1).scaleY(1).setListener(new Animator.AnimatorListener() {
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
        fmCloseView.animate().setDuration(300).setListener(null).setInterpolator(new OvershootInterpolator()).scaleX(.8f).scaleY(.8f).setListener(new Animator.AnimatorListener() {
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


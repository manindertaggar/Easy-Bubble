package com.eworl.easybubble.bubbles;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.eworl.easybubble.R;

/**
 * Created by root on 3/2/17.
 */

public class SubBubble {
    private FrameLayout fmContentView, fmSubBubbleView;
    private ImageView ivIcon;
    private Context context;

    public SubBubble(Context context) {

        intializeView();
        setListeners();
    }

    private void intializeView() {
        fmContentView = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.layout_sub_bubble, null);
        fmSubBubbleView = (FrameLayout) fmContentView.findViewById(R.id.fmSubBubbleView);
        ivIcon = (ImageView) fmContentView.findViewById(R.id.ivIcon);
    }

    private void setListeners() {

    }



}

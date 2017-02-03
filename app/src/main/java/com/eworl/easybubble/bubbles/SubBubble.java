package com.eworl.easybubble.bubbles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.eworl.easybubble.R;
import com.eworl.easybubble.eventBus.CloseMasterBubbleEvent;
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
        fmSubBubbleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeMasterBubble();
                performAction();
            }
        });
    }

    private void closeMasterBubble() {
        EventBus.getDefault().post(new CloseMasterBubbleEvent());
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

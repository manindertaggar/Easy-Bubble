package com.eworl.easybubble.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.eworl.easybubble.LayoutParamGenerator;
import com.eworl.easybubble.PermissionManager;
import com.eworl.easybubble.R;
import com.eworl.easybubble.ViewManager;
import com.eworl.easybubble.bubbles.MasterBubble;
import com.eworl.easybubble.bubbles.SubBubble;

import java.util.ArrayList;

public class MainActivity extends Activity {
ArrayList<Integer> imageIcons = new ArrayList<>();
    MasterBubble masterBubble;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewManager viewManager = ViewManager.init(this);

        PermissionManager.checkForOverlayPermission(this);

         masterBubble = new MasterBubble(this);
        imageIcons.add(R.drawable.camera);
        imageIcons.add(R.drawable.locked);
        imageIcons.add(R.drawable.mail);
        imageIcons.add(R.drawable.whatsapp);
        imageIcons.add(R.drawable.back);
        imageIcons.add(R.drawable.locked);
        imageIcons.add(R.drawable.whatsapp);
        imageIcons.add(R.drawable.plus);

        for (int i = 0; i < 8; i++) {
            SubBubble subBubble = new SubBubble(this);
            subBubble.setIcon(imageIcons.get(i));
            masterBubble.addSubBubble(subBubble);
        }

        viewManager.addView(masterBubble.getView(), LayoutParamGenerator.getNewLayoutParams());

    }

    }



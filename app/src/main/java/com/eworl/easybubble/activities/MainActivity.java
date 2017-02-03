package com.eworl.easybubble.activities;

import android.app.Activity;
import android.os.Bundle;

import com.eworl.easybubble.LayoutParamGenerator;
import com.eworl.easybubble.PermissionManager;
import com.eworl.easybubble.R;
import com.eworl.easybubble.ViewManager;
import com.eworl.easybubble.bubbles.MasterBubble;
import com.eworl.easybubble.bubbles.SubBubble;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewManager viewManager = ViewManager.init(this);

        PermissionManager.checkForOverlayPermission(this);

        MasterBubble masterBubble = new MasterBubble(this);

        for (int i = 0; i < 8; i++) {
            SubBubble subBubble = new SubBubble(this);
            subBubble.setIcon(R.drawable.plus);
            masterBubble.addSubBubble(subBubble);
        }


        viewManager.addView(masterBubble.getView(), LayoutParamGenerator.getNewLayoutParams());

    }


}

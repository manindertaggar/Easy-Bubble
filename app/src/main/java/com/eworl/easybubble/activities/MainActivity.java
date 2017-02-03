package com.eworl.easybubble.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.eworl.easybubble.R;
import com.eworl.easybubble.bubbles.MasterBubble;
import com.eworl.easybubble.bubbles.SubBubble;

public class MainActivity extends Activity {

    RelativeLayout activityMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMain = (RelativeLayout) findViewById(R.id.activityMain);

        MasterBubble masterBubble = new MasterBubble(this);
        activityMain.addView(masterBubble.getView());


        for (int i = 0; i < 8; i++) {
            SubBubble subBubble = new SubBubble(this);
            subBubble.setIcon(R.drawable.plus);
            masterBubble.addSubBubble(subBubble);
        }

    }


}

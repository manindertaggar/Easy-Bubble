package com.eworl.easybubble.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.eworl.easybubble.R;
import com.eworl.easybubble.bubbles.MasterBubble;
import com.eworl.easybubble.bubbles.SubBubble;

public class MainActivity extends Activity {

    LinearLayout activityMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMain = (LinearLayout) findViewById(R.id.activityMain);

        MasterBubble masterBubble = new MasterBubble(this);
        activityMain.addView(masterBubble.getView());

        SubBubble subBubble = new SubBubble(this);
        subBubble.setIcon(R.drawable.plus);

        masterBubble.addSubBubble(subBubble);

    }


}

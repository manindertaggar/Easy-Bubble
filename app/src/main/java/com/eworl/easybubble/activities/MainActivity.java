package com.eworl.easybubble.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.eworl.easybubble.R;
import com.eworl.easybubble.bubbles.MasterBubble;

public class MainActivity extends Activity {

    LinearLayout activituMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activituMain = (LinearLayout) findViewById(R.id.activityMain);

        MasterBubble masterBubble = new MasterBubble(this);
        activituMain.addView(masterBubble.getView());
    }


}

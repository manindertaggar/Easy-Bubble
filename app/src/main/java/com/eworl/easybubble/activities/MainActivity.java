package com.eworl.easybubble.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eworl.easybubble.LayoutParamGenerator;
import com.eworl.easybubble.PermissionManager;
import com.eworl.easybubble.R;
import com.eworl.easybubble.ViewManager;
import com.eworl.easybubble.bubbles.MasterBubble;
import com.eworl.easybubble.bubbles.SubBubble;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {
    private ArrayList<Integer> imageIcons = new ArrayList<>();
    private MasterBubble masterBubble;
    private Button startServiceButton;
    private ViewManager viewManager;
    private TextView appsListTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        startServiceButton = (Button) findViewById(R.id.button);
        appsListTV = (TextView) findViewById(R.id.appsList);

        int flags = PackageManager.GET_META_DATA |
                PackageManager.GET_SHARED_LIBRARY_FILES |
                PackageManager.GET_UNINSTALLED_PACKAGES;

        PackageManager pm = getPackageManager();
        List<ApplicationInfo> applications = pm.getInstalledApplications(flags);
//        List<ApplicationInfo> installedApps = new ArrayList<ApplicationInfo>();
        for (ApplicationInfo appInfo : applications) {
            if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 1) {

                Log.d(TAG, "installedApps: "+applications);
                // System application
            } else {
                // Installed by user
            }
        }






        viewManager = ViewManager.init(this);

        PermissionManager.checkForOverlayPermission(this);

        masterBubble = new MasterBubble(this);
        imageIcons.add(R.drawable.camera);
        imageIcons.add(R.drawable.locked);
        imageIcons.add(R.drawable.plus);
        imageIcons.add(R.drawable.whatsapp);
        imageIcons.add(R.drawable.back);
        imageIcons.add(R.drawable.locked);
        imageIcons.add(R.drawable.whatsapp);
        imageIcons.add(R.drawable.mail);

        for (int i = 0; i < 8; i++) {
            SubBubble subBubble = new SubBubble(this);
            subBubble.setIcon(imageIcons.get(i));
            masterBubble.addSubBubble(subBubble);

        }


    }
    public void startServive(View view) {
        viewManager.addView(masterBubble.getView(), LayoutParamGenerator.getNewLayoutParams());
        finish();
    }

}



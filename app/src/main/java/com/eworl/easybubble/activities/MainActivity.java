package com.eworl.easybubble.activities;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.eworl.easybubble.eventBus.ItemListEvevt;
import com.eworl.easybubble.utils.ItemObject;
import com.eworl.easybubble.LayoutParamGenerator;
import com.eworl.easybubble.PermissionManager;
import com.eworl.easybubble.R;
import com.eworl.easybubble.Adapter.RecyclerViewAdapter;
import com.eworl.easybubble.ViewManager;
import com.eworl.easybubble.bubbles.MasterBubble;
import com.eworl.easybubble.bubbles.SubBubble;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {
    private ArrayList<Integer> imageIcons = new ArrayList<>();
    private ArrayList<String> appsList = new ArrayList<>();
    private MasterBubble masterBubble;
    private Button startServiceButton;
    private ViewManager viewManager;
    private TextView appsListTV;
    private ImageView imageIcon;
    String appName;
    Drawable appIcon;
    int listCount;
    private LinearLayoutManager lLayout;
    List<ItemObject> allItems;
    List<ItemObject> rowListItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        startServiceButton = (Button) findViewById(R.id.button);

        getInstalledApplication(this);
        rowListItem = getAllItemList();
        lLayout = new LinearLayoutManager(this);

        RecyclerView rView = (RecyclerView)findViewById(R.id.recyclerView);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);

        RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(MainActivity.this, rowListItem);
        rView.setAdapter(rcAdapter);







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
//        finish();
    }


    public void getInstalledApplication(Context context) {
        PackageManager packageManager = context.getPackageManager();
        List<ApplicationInfo> apps = packageManager.getInstalledApplications(0);
        List<ApplicationInfo> appInfoList = new ArrayList();
        for (ApplicationInfo info : apps) {
            if (packageManager.getLaunchIntentForPackage(info.packageName) != null) {
                appInfoList.add(info);

            }
        }
        Collections.sort(appInfoList, new ApplicationInfo.DisplayNameComparator(packageManager));
        Log.d(TAG, "getInstalledApplication: " + appInfoList);
        int listCount = appInfoList.size();
        Log.d(TAG, "count: "+listCount);
         allItems = new ArrayList<ItemObject>();
        for (int i = 0; i < listCount; i++) {
            appName = (String) packageManager.getApplicationLabel(appInfoList.get(i));
            appIcon = packageManager.getApplicationIcon(appInfoList.get(i));
            Bitmap myLogo = ((BitmapDrawable) appIcon).getBitmap();

            allItems.add(new ItemObject(appName, appIcon, R.drawable.plus));

            Log.d(TAG, "label: " + myLogo);
        }
        EventBus.getDefault().post(new ItemListEvevt(allItems));


    }

    public List<ItemObject> getAllItemList(){


        return allItems;
    }

}





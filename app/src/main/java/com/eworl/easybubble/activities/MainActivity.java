package com.eworl.easybubble.activities;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.eworl.easybubble.eventBus.ItemListEvent;
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
    private ArrayList<String> appsList = new ArrayList<>();
    private MasterBubble masterBubble;
    private Button startServiceButton;
    private ViewManager viewManager;
    private String appName,packageName;
    private Drawable appIcon;
    private int listCount;
    private LinearLayoutManager lLayout;
    private List<ItemObject> allItems;
    private List<ItemObject> rowListItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        startServiceButton = (Button) findViewById(R.id.button);

        getInstalledApplication(this);
        rowListItem = getAllItemList();
        lLayout = new LinearLayoutManager(this);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(lLayout);

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, rowListItem);
        recyclerView.setAdapter(recyclerViewAdapter);


        viewManager = ViewManager.init(this);

        PermissionManager.checkForOverlayPermission(this);

        masterBubble = new MasterBubble(this);


        for (int i = 0; i < 8; i++) {
            SubBubble subBubble = new SubBubble(this);
            subBubble.setIcon(allItems.get(i).getAppIcon());
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
            packageName =appInfoList.get(i).packageName;
            allItems.add(new ItemObject(appName, appIcon,R.drawable.green_square,R.drawable.plus,packageName));
//            programDao_object.insert(new program(appName,,R.drawable.green_square,R.drawable.plus,packageName));
            Log.d(TAG, "appName: " + appName);
        }
        EventBus.getDefault().post(new ItemListEvent(allItems));
    }

    public List<ItemObject> getAllItemList(){

        return allItems;
    }

}





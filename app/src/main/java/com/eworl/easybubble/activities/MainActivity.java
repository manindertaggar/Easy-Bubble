package com.eworl.easybubble.activities;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eworl.easybubble.Adapter.RvAdapter;
import com.eworl.easybubble.RecyclerViewListeners.Listener;
import com.eworl.easybubble.db.DaoMaster;
import com.eworl.easybubble.db.DaoSession;
import com.eworl.easybubble.db.Program;
import com.eworl.easybubble.db.ProgramDao;
import com.eworl.easybubble.eventBus.BubbleServiceIsRunning;
import com.eworl.easybubble.LayoutParamGenerator;
import com.eworl.easybubble.PermissionManager;
import com.eworl.easybubble.R;
import com.eworl.easybubble.ViewManager;
import com.eworl.easybubble.bubbles.MasterBubble;
import com.eworl.easybubble.bubbles.SubBubble;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity implements Listener {

    private MasterBubble masterBubble;
    private Button startServiceButton;
    private ViewManager viewManager;
    private String appName, packageName;
    private Drawable icon;
    private GridLayoutManager glmAllApps;
    private GridLayoutManager glmSelectedApps;
    private List<Program> allItems;
    private List<Program> rowListItem;
    private final String DB_NAME = "logs-db";
    private boolean onClick = false;
    public TextView textEmptyListTop, textEmptyListBottom;
    public RecyclerView rvSelectedApps, rvAllApps;
    private ProgramDao programDao_object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        textEmptyListTop = (TextView) findViewById(R.id.TVSelectedItemListEmpty);
        textEmptyListBottom = (TextView) findViewById(R.id.TVAllItemListEmpty);

        startServiceButton = (Button) findViewById(R.id.button);
        loadActivity();

    }

    public void startServive(View view) {
        if (onClick) {
            stopService();
            onClick = false;
        } else {
            loadActivity();
            viewManager.addView(masterBubble.getView(), LayoutParamGenerator.getNewLayoutParams());
            EventBus.getDefault().post(new BubbleServiceIsRunning());
            onClick = true;
        }

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
        Log.d(TAG, "count: " + listCount);
        allItems = new ArrayList<Program>();
        for (int i = 0; i < listCount; i++) {
            appName = (String) packageManager.getApplicationLabel(appInfoList.get(i));
            icon = packageManager.getApplicationIcon(appInfoList.get(i));

            Bitmap img = ((BitmapDrawable) icon).getBitmap();
            Log.d(TAG, "bitmap: " + img);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            img.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] imageInByte = stream.toByteArray();
            String appIcon = Base64.encodeToString(imageInByte, Base64.DEFAULT);

            packageName = appInfoList.get(i).packageName;
            allItems.add(new Program(appName, appIcon, packageName, false));

            Log.d(TAG, "packageName: " + packageName);
        }

    }

    public List<Program> getAllItemList() {

        return allItems;
    }

    public ProgramDao setupDb() {
        DaoMaster.DevOpenHelper masterHelper = new DaoMaster.DevOpenHelper(this, DB_NAME, null); //create database db file if not exist
        SQLiteDatabase db = masterHelper.getWritableDatabase();  //get the created database db file
        DaoMaster master = new DaoMaster(db);//create masterDao
        DaoSession masterSession = master.newSession(); //Creates Session session

        return masterSession.getProgramDao();
    }

    public ProgramDao getProgramDaoInstance() {
        if (programDao_object != null) {
            return programDao_object;
        } else {
            return setupDb();
        }
    }

    private void loadActivity() {

        programDao_object = setupDb();
        List<Program> log_list = programDao_object.queryBuilder()/*.orderDesc(programDao.Properties.Id)*/.build().list();
        Log.d(TAG, "onCreate: " + log_list.size());

        getInstalledApplication(this);

        rowListItem = getAllItemList();

        //default bubbles for initial list------
        for (int i = 0; i < 5; i++) {

            rowListItem.get(i).setIsSelected(true);
            try {
                programDao_object.insert(new Program(rowListItem.get(i).getAppName(), rowListItem.get(i).getAppIcon(), rowListItem.get(i).getPackageName(), true));
                rowListItem.remove(i);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        //already Added bubbles
        for (int i = 0; i < rowListItem.size(); i++) {
            String pak = rowListItem.get(i).getPackageName();
            for (int j = 0; j < log_list.size(); j++) {
                String pak1 = log_list.get(j).getPackageName();
                if (pak.equals(pak1)) {
                    rowListItem.get(i).setIsSelected(true);
                }
            }
        }

        for (int i = 0; i < rowListItem.size(); i++) {
            Log.d(TAG, "listItemBoolean: " + rowListItem.get(i).getIsSelected());
        }
        glmAllApps = new GridLayoutManager(this, 4);
        glmSelectedApps = new GridLayoutManager(this, 4);

        rvSelectedApps = (RecyclerView) findViewById(R.id.rvSelectedAppList);
        rvSelectedApps.setLayoutManager(glmSelectedApps);
        RvAdapter rvAdapter1 = new RvAdapter(MainActivity.this, log_list, this, this);
        rvSelectedApps.setAdapter(rvAdapter1);


        rvAllApps = (RecyclerView) findViewById(R.id.rvAppList);
        rvAllApps.setHasFixedSize(true);
        rvAllApps.setLayoutManager(glmAllApps);
        RvAdapter rvAdapter2 = new RvAdapter(MainActivity.this, rowListItem, this, this);
        rvAllApps.setAdapter(rvAdapter2);


        textEmptyListTop.setOnDragListener(rvAdapter1.getDragInstance());
        textEmptyListTop.setVisibility(View.GONE);
        textEmptyListBottom.setOnDragListener(rvAdapter2.getDragInstance());
        textEmptyListBottom.setVisibility(View.GONE);

        viewManager = ViewManager.init(this);

        PermissionManager.checkForOverlayPermission(this);

        masterBubble = new MasterBubble(this, log_list);

        for (int i = 0; i < log_list.size(); i++) {
            SubBubble subBubble = new SubBubble(this, log_list, masterBubble);
            String img = log_list.get(i).getAppIcon();
            Log.d(TAG, "img:" + img);
            byte[] bitmapdata = Base64.decode(img, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
            Drawable icon = new BitmapDrawable(getResources(), bitmap);
            Log.d(TAG, "onCreate: " + icon);
            subBubble.setIcon(icon);
            masterBubble.addSubBubble(subBubble);
        }

    }

    @Override
    public void setEmptyListTop(boolean visibility) {
        textEmptyListTop.setVisibility(visibility ? View.VISIBLE : View.GONE);
        rvSelectedApps.setVisibility(visibility ? View.GONE : View.VISIBLE);
    }

    @Override
    public void setEmptyListBottom(boolean visibility) {
        textEmptyListBottom.setVisibility(visibility ? View.VISIBLE : View.GONE);
        rvAllApps.setVisibility(visibility ? View.GONE : View.VISIBLE);
    }


    public void stopService() {
        viewManager.removeView(masterBubble.getView());
    }

    public void reStartService() {
        loadActivity();
        viewManager.addView(masterBubble.getView(), LayoutParamGenerator.getNewLayoutParams());
    }

}





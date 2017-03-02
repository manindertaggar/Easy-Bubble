package com.eworl.easybubble.RecyclerViewListeners;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.eworl.easybubble.ViewHolder.RvHolderAllitems;
import com.eworl.easybubble.activities.MainActivity;
import com.eworl.easybubble.db.DaoMaster;
import com.eworl.easybubble.db.DaoSession;
import com.eworl.easybubble.db.program;
import com.eworl.easybubble.db.programDao;
import com.eworl.easybubble.eventBus.BubbleServiceIsRunning;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by Dhankher on 2/23/2017.
 */

public class AllitemViewTouchListener implements View.OnTouchListener {
    private static final String TAG = "AllitemViewTouch";
    private Context context;
    private List<ItemObject> itemList;
    private programDao programDao_object;
    private program program_object;
    private List<program> log_list;
    String log_text;  //Entered text data is save in this variable
    private final String DB_NAME = "logs-db";  //Name of Db file in the Device
    private DaoSession masterSession;
    private RvHolderAllitems holder;
    private static int count;
    private MainActivity mainActivity;
    private boolean bubbleServiceIsRunning = false;

    public AllitemViewTouchListener(Context context, List<ItemObject> itemList, RvHolderAllitems holder, MainActivity mainActivity) {
        this.context = context;
        this.itemList = itemList;
        this.holder = holder;
        this.mainActivity = mainActivity;
        EventBus.getDefault().register(this);
        programDao_object = setupDb();
        log_list = programDao_object.queryBuilder().orderDesc(programDao.Properties.Id).build().list();
        count = log_list.size();
//        insertDefaultList();

    }



    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float x = motionEvent.getRawX();
                float y = motionEvent.getRawY();

              

                break;
        }

        return false;
    }

    private String getFromSQL() {

        //Get the list of all LOGS in Database in descending order

        if (log_list.size() > 0) {  //if list is not null

            for (int i = 0; i < log_list.size(); i++) {
//                Log.d(TAG, "size: " + log_list.size());
                Log.d(TAG, "package Name: " + log_list.get(2).getPackageName());

//            return log_list.get(1).getText();
                return (log_list.get(i)).getAppName();
                //get(0)--> 1st object
                // getText() is the function in LOG class
            }
        }
        return "";
    }

    private void SaveToSQL(program temp_log_object) {
        try {
            programDao_object.insert(temp_log_object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "size: " + log_list.size());

    }

    private void DeleteFromSQL(String pak) {

        //starts here
        QueryBuilder<program> qb = masterSession.getProgramDao().queryBuilder().where(
                programDao.Properties.PackageName.eq(pak)
        );

        List<program> list = qb.list();
        if (list.isEmpty()) {
            Log.e(TAG, "setupDb: no app found with this");
        } else {
            list.get(0).getAppName();
            Log.d(TAG, "appName: " + (list.get(0).getAppName()));
            programDao_object.delete(list.get(0));
            Log.d(TAG, "size: " + log_list.size());
        }
        //ends here


    }

    public programDao setupDb() {
        DaoMaster.DevOpenHelper masterHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null); //create database db file if not exist
        SQLiteDatabase db = masterHelper.getWritableDatabase();  //get the created database db file
        DaoMaster master = new DaoMaster(db);//create masterDao
        masterSession = master.newSession(); //Creates Session session

        return masterSession.getProgramDao();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BubbleServiceIsRunning event) {
        bubbleServiceIsRunning = true;

    }


}

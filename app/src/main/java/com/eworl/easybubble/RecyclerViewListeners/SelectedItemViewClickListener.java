package com.eworl.easybubble.RecyclerViewListeners;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.eworl.easybubble.ViewHolder.RvHolder;
import com.eworl.easybubble.activities.MainActivity;
import com.eworl.easybubble.db.DaoMaster;
import com.eworl.easybubble.db.DaoSession;
import com.eworl.easybubble.db.Program;
import com.eworl.easybubble.db.ProgramDao;
import com.eworl.easybubble.eventBus.BubbleServiceIsRunning;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.List;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by Dhankher on 2/27/2017.
 */

public class SelectedItemViewClickListener implements View.OnClickListener {

    private static final String TAG = "SelectedViewListener";
    private Context context;
    private List<Program> itemList;
    private ProgramDao programDao_object;
    private Program program_object;
    private List<Program> log_list;
    String log_text;  //Entered text data is save in this variable
    private final String DB_NAME = "logs-db";  //Name of Db file in the Device
    private DaoSession masterSession;
    private RvHolder holder;
    private static int count;
    private MainActivity mainActivity;
    private boolean bubbleServiceIsRunning = false;

    public SelectedItemViewClickListener(Context context, List<Program> itemList, RvHolder holder, MainActivity mainActivity) {
        this.context = context;
        this.itemList = itemList;
        this.holder = holder;
        this.mainActivity = mainActivity;
        EventBus.getDefault().register(this);
        programDao_object = setupDb();
        log_list = programDao_object.queryBuilder()/*.orderDesc(programDao.Properties.Id)*/.build().list();
        count = log_list.size();
//        insertDefaultList();

    }

    @Override
    public void onClick(View view) {
//        getFromSQL();
        if (itemList.get(holder.getAdapterPosition()).getIsSelected()==true) {

            String pak = itemList.get(holder.getAdapterPosition()).getPackageName();
            Log.d(TAG, "pak: " + pak);
            DeleteFromSQL(pak);
//            itemList.get(holder.getAdapterPosition()).setIsSelected(false);
            Toast.makeText(view.getContext(), itemList.get(holder.getAdapterPosition()).getAppName() + " removed from your list", Toast.LENGTH_SHORT).show();
            count = count - 1;
            Log.d(TAG, "count after remove" + count);

            if (bubbleServiceIsRunning) {
                mainActivity.stopService();
                mainActivity.reStartService();
            }

        } else {
//            if (count<15) {
            Log.d(TAG, "count before adding" + count);

            Log.d(TAG, "string: " + itemList.get(holder.getAdapterPosition()).getAppIcon());
            program_object = new Program(itemList.get(holder.getAdapterPosition()).getAppName(),  itemList.get(holder.getAdapterPosition()).getAppIcon(), itemList.get(holder.getAdapterPosition()).getPackageName(),true);// Class Object, Id is auto increment
            SaveToSQL(program_object);
            itemList.get(holder.getAdapterPosition()).setIsSelected(true);
            Toast.makeText(view.getContext(), itemList.get(holder.getAdapterPosition()).getAppName() + " added to your list", Toast.LENGTH_SHORT).show();
//            getFromSQL();
            count = count + 1;
            Log.d(TAG, "count after adding" + count);

            if (bubbleServiceIsRunning) {
                mainActivity.stopService();
                mainActivity.reStartService();
            }


//            } else {
//                Toast.makeText(context, "You have already selected 8 bubbles", Toast.LENGTH_SHORT).show();
//            }

        }
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

    private void SaveToSQL(Program temp_log_object) {
        try {
            programDao_object.insert(temp_log_object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "size: " + log_list.size());

    }

    private void DeleteFromSQL(String pak) {

        //starts here
        QueryBuilder<Program> qb = masterSession.getProgramDao().queryBuilder().where(
                ProgramDao.Properties.PackageName.eq(pak)
        );

        List<Program> list = qb.list();
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

    public ProgramDao setupDb() {
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



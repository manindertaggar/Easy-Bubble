package com.eworl.easybubble.ViewHolder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.eworl.easybubble.R;
import com.eworl.easybubble.db.DaoMaster;
import com.eworl.easybubble.db.DaoSession;
import com.eworl.easybubble.db.program;
import com.eworl.easybubble.db.programDao;
import com.eworl.easybubble.utils.ItemObject;

import java.io.ByteArrayOutputStream;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by Dhankher on 2/23/2017.
 */

public class ViewClickListener implements View.OnClickListener {
    private static final String TAG = "ViewClickListener";
    private Context context;
    private List<ItemObject> itemList;
    private programDao programDao_object;
    private program program_object;
    private List<program> log_list;
    private boolean onClick = false;
    String log_text;  //Entered text data is save in this variable
    private final String DB_NAME = "logs-db";  //Name of Db file in the Device
    private DaoSession masterSession;
    private RecyclerViewHolders holder;
    private static int count;

    public ViewClickListener(Context context, List<ItemObject> itemList, RecyclerViewHolders holder){
        this.context = context;
        this.itemList = itemList;
        this.holder = holder;
        programDao_object = setupDb();
        log_list = programDao_object.queryBuilder().orderDesc(programDao.Properties.Id).build().list();
        count = log_list.size();
    }


    @Override
    public void onClick(View view) {
        getFromSQL();
        if (onClick) {
            itemList.get(holder.getAdapterPosition()).setGreenIcon(R.drawable.green_square);
            holder.addIcon.setImageResource(R.drawable.green_square);
            itemList.get(holder.getAdapterPosition()).setPlusIcon(R.drawable.plus);
            holder.plusIcon.setImageResource(R.drawable.plus);
            String pak = itemList.get(holder.getAdapterPosition()).getPackagename();
            Log.d(TAG, "pak: " + pak);
            DeleteFromSQL(pak);

            count = count - 1;
            Log.d(TAG, "count after remove" + count);
            onClick = false;
        } else {
            if (count < 9) {
                Log.d(TAG, "count before adding" + count);
                itemList.get(holder.getAdapterPosition()).setGreenIcon(R.drawable.red_square);
                holder.addIcon.setImageResource(R.drawable.red_square);
                itemList.get(holder.getAdapterPosition()).setPlusIcon(R.drawable.cross);
                holder.plusIcon.setImageResource(R.drawable.cross);
                Bitmap img = ((BitmapDrawable) itemList.get(holder.getAdapterPosition()).getAppIcon()).getBitmap();
                Log.d(TAG, "bitmap: " + img);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                img.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] imageInByte = stream.toByteArray();
                String image = Base64.encodeToString(imageInByte, Base64.DEFAULT);
                Log.d(TAG, "string: " + image);
                program_object = new program(null, itemList.get(holder.getAdapterPosition()).getAppName(), image, itemList.get(holder.getAdapterPosition()).getGreenIcon(), itemList.get(holder.getAdapterPosition()).getPlusIcon(), itemList.get(holder.getAdapterPosition()).getPackagename());// Class Object, Id is auto increment
                SaveToSQL(program_object);

                Toast.makeText(view.getContext(), itemList.get(holder.getAdapterPosition()).getAppName() + " added to your list :-)", Toast.LENGTH_SHORT).show();
                getFromSQL();
                count = count + 1;
                Log.d(TAG, "count after adding" + count);
                onClick = true;
            } else {
                Toast.makeText(context, "You have already selected 8 bubbles", Toast.LENGTH_SHORT).show();
            }

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

    public void insertDefaultList() {
        try {
            programDao_object.insert(new program(null, "back", null, R.drawable.back, null, "back"));
            programDao_object.insert(new program(null, "lock", null, R.drawable.locked, null, "lock"));
            programDao_object.insert(new program(null, "home", null, R.drawable.home, null, "home"));

        } catch (Exception E) {
            E.printStackTrace();
        }

    }


}
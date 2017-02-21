package com.eworl.easybubble.ViewHolder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eworl.easybubble.db.DaoMaster;
import com.eworl.easybubble.db.DaoSession;
import com.eworl.easybubble.db.program;
import com.eworl.easybubble.db.programDao;
import com.eworl.easybubble.utils.ItemObject;
import com.eworl.easybubble.R;
import com.eworl.easybubble.utils.SelectedItemObject;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final String TAG = "RecyclerViewHolder";
    public TextView appName;
    public ImageView appIcon;
    public ImageView addIcon;
    public ImageView plusIcon;
    List<ItemObject> itemList;
    List<SelectedItemObject> selectedItemList;
    Context contaxt;
    private programDao programDao_object;
    private program program_object;
    String log_text;  //Entered text data is save in this variable
    private final String DB_NAME = "logs-db";  //Name of Db file in the Device

    public RecyclerViewHolders(View itemView, Context context, List<ItemObject> itemList) {
        super(itemView);
        this.itemList = itemList;
        this.contaxt = context;
        programDao_object = setupDb();
//        EventBus.getDefault().register(context);
        itemView.setOnClickListener(this);
        appName = (TextView) itemView.findViewById(R.id.country_name);
        appIcon = (ImageView) itemView.findViewById(R.id.appIcon);
        addIcon = (ImageView) itemView.findViewById(R.id.addIcon);
        plusIcon = (ImageView) itemView.findViewById(R.id.plusIcon);
    }


    @Override
    public void onClick(View view) {
        selectedItemList = new ArrayList<>();
        selectedItemList.add(new SelectedItemObject("back", R.drawable.back));
        selectedItemList.add(new SelectedItemObject("lock", R.drawable.locked));
        selectedItemList.add(new SelectedItemObject("home", R.drawable.home));
//        itemList.get(getAdapterPosition()).getAddIcon().setImageBitmap(R.drawable.add);//.setBackgroundColor(Color.RED);
//        addIcon.setBackgroundColor(Color.RED);

//        itemList.get(getAdapterPosition()).setAddIcon(R.drawable.facebook);
//        addIcon.setImageResource(R.drawable.facebook);
//        addIcon.setBackgroundColor(Color.RED);
//        Toast.makeText(view.getContext(), "Clicked list Position = " + getPosition(), Toast.LENGTH_SHORT).show();
        log_text = "pawan";
        program_object = new program(null, itemList.get(getAdapterPosition()).getAppName(),null);// Class Object, Id is auto increment
        SaveToSQL(program_object);

//        selectedItemList.add(new SelectedItemObject(itemList.get(getAdapterPosition()).getAppName(), Integer.parseInt(String.valueOf(itemList.get(getAdapterPosition()).getAppIcon()))));
        Toast.makeText(view.getContext(), itemList.get(getAdapterPosition()).getAppName() + "added to your list :-)", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Count of selected items " + selectedItemList.size());
    }

    private String getFromSQL() {
        List<program> log_list = programDao_object.queryBuilder().orderDesc(programDao.Properties.Id).build().list();
        //Get the list of all LOGS in Database in descending order

        if (log_list.size() > 0) {  //if list is not null

            for (int i = 0; i < log_list.size(); i++) {
                Log.d(TAG, "size: " + log_list.size());
//            return log_list.get(1).getText();
                return (log_list.get(i)).getAppName();
                //get(0)--> 1st object
                // getText() is the function in LOG class
            }
        }
        return "";
    }

    private void SaveToSQL(program temp_log_object) {
        programDao_object.insert(temp_log_object);
    }

    private programDao setupDb() {
        DaoMaster.DevOpenHelper masterHelper = new DaoMaster.DevOpenHelper(contaxt, DB_NAME, null); //create database db file if not exist
        SQLiteDatabase db = masterHelper.getWritableDatabase();  //get the created database db file
        DaoMaster master = new DaoMaster(db);//create masterDao
        DaoSession masterSession = master.newSession(); //Creates Session session
        return masterSession.getProgramDao();
    }


//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessageEvent(ItemListEvent evevt) {
//        itemList =   evevt.getAllItems();
//
//        Log.d(TAG, "onMessageEvent: "+itemList.get(1).getAppName());
//}
}
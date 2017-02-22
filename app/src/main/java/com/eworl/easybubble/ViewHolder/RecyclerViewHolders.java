package com.eworl.easybubble.ViewHolder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
import java.util.List;

public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final String TAG = "RecyclerViewHolder";
    public TextView appName;
    public ImageView appIcon;
    public ImageView addIcon;
    public ImageView plusIcon;
    private List<ItemObject> itemList;
    private Context contaxt;
    private programDao programDao_object;
    private program program_object;
    private List<program> log_list;
    String log_text;  //Entered text data is save in this variable
    private final String DB_NAME = "logs-db";  //Name of Db file in the Device
    private boolean onClick = false;

    public RecyclerViewHolders(View itemView, Context context, List<ItemObject> itemList) {
        super(itemView);
        this.itemList = itemList;
        this.contaxt = context;
        programDao_object = setupDb();
        log_list = programDao_object.queryBuilder().orderDesc(programDao.Properties.Id).build().list();
//        EventBus.getDefault().register(context);
        itemView.setOnClickListener(this);
        appName = (TextView) itemView.findViewById(R.id.country_name);
        appIcon = (ImageView) itemView.findViewById(R.id.appIcon);
        addIcon = (ImageView) itemView.findViewById(R.id.greenIcon);
        plusIcon = (ImageView) itemView.findViewById(R.id.plusIcon);
        insertDefaultList();
    }


    @Override
    public void onClick(View view) {
        if (onClick) {
            itemList.get(getAdapterPosition()).setGreenIcon(R.drawable.green_square);
            addIcon.setImageResource(R.drawable.green_square);
            itemList.get(getAdapterPosition()).setPlusIcon(R.drawable.plus);
            plusIcon.setImageResource(R.drawable.plus);

            onClick = false;
        } else {
            if (log_list.size() < 11) {
                itemList.get(getAdapterPosition()).setGreenIcon(R.drawable.red_square);
                addIcon.setImageResource(R.drawable.red_square);
                itemList.get(getAdapterPosition()).setPlusIcon(R.drawable.cross);
                plusIcon.setImageResource(R.drawable.cross);
                Bitmap img = ((BitmapDrawable) itemList.get(getAdapterPosition()).getAppIcon()).getBitmap();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                img.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] imageInByte = stream.toByteArray();
                String image = Base64.encodeToString(imageInByte, Base64.DEFAULT);
                try {
                    program_object = new program(null, itemList.get(getAdapterPosition()).getAppName(), image, itemList.get(getAdapterPosition()).getGreenIcon(), itemList.get(getAdapterPosition()).getPlusIcon(), itemList.get(getAdapterPosition()).getPackagename());// Class Object, Id is auto increment
                    SaveToSQL(program_object);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(view.getContext(), itemList.get(getAdapterPosition()).getAppName() + "added to your list :-)", Toast.LENGTH_SHORT).show();
                getFromSQL();
            } else {
                Toast.makeText(contaxt, "You have already selected 10 bubbles", Toast.LENGTH_SHORT).show();
            }
        }
       onClick = true;
    }
    private String getFromSQL() {

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

  public void insertDefaultList(){
      try {
          programDao_object.insert(new program(null,"back",null,R.drawable.back,null, "back"));
          programDao_object.insert(new program(null,"lock",null,R.drawable.locked,null,"lock"));
          programDao_object.insert(new program(null,"home",null,R.drawable.home,null,"home"));

      }catch (Exception E){
          E.printStackTrace();
      }

    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessageEvent(ItemListEvent evevt) {
//        itemList =   evevt.getAllItems();
//
//        Log.d(TAG, "onMessageEvent: "+itemList.get(1).getAppName());
//}
}
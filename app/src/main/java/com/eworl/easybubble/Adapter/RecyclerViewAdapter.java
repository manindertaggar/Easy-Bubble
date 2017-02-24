package com.eworl.easybubble.Adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eworl.easybubble.ViewHolder.ViewClickListener;
import com.eworl.easybubble.db.DaoSession;
import com.eworl.easybubble.db.program;
import com.eworl.easybubble.db.programDao;
import com.eworl.easybubble.utils.ItemObject;
import com.eworl.easybubble.R;
import com.eworl.easybubble.ViewHolder.RecyclerViewHolders;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    private static final String TAG = "RecyclerViewAdapter";
    private List<ItemObject> itemList;
    private Context context;
    private List<program> log_list;

    public RecyclerViewAdapter(Context context, List<ItemObject> itemList, List<program> log_list) {
        this.itemList = itemList;
        this.context = context;
        this.log_list = log_list;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_list_layout, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView, context, itemList);
        return rcv;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {



//        Log.d(TAG, "onBindViewHolder: "+log_list.get(1).getAppName());

//      if((itemList.get(position).getPackagename()).equals(log_list.get(1).getPackageName())){
//        if(position ==5){
//          holder.appName.setText(itemList.get(position).getAppName());
//          holder.appIcon.setImageDrawable(itemList.get(position).getAppIcon());
//          holder.addIcon.setImageResource(itemList.get(position).getRedIcon());
//          holder.plusIcon.setImageResource(itemList.get(position).getCrossIcon());
//      }else

        holder.appName.setText(itemList.get(position).getAppName());
        holder.appIcon.setImageDrawable(itemList.get(position).getAppIcon());
        holder.addIcon.setImageResource(itemList.get(position).getGreenIcon());
        holder.plusIcon.setImageResource(itemList.get(position).getPlusIcon());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}

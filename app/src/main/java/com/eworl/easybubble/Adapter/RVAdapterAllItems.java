package com.eworl.easybubble.Adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eworl.easybubble.activities.MainActivity;
import com.eworl.easybubble.db.program;
import com.eworl.easybubble.utils.ItemObject;
import com.eworl.easybubble.R;
import com.eworl.easybubble.ViewHolder.RvHolderAllitems;

import java.util.List;

public class RvAdapterAllitems extends RecyclerView.Adapter<RvHolderAllitems> {

    private static final String TAG = "RvAdapterAllitems";
    private List<ItemObject> itemList;
    private Context context;
    private List<program> log_list;
    private MainActivity mainActivity;

    public RvAdapterAllitems(Context context, List<ItemObject> itemList, List<program> log_list, MainActivity mainActivity) {
        this.itemList = itemList;
        this.context = context;
        this.log_list = log_list;
        this.mainActivity = mainActivity;
    }

    @Override
    public RvHolderAllitems onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_list_layout, null);
        RvHolderAllitems rcv = new RvHolderAllitems(layoutView, context, itemList, mainActivity, log_list);
        return rcv;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(RvHolderAllitems holder, int position) {


        Log.d(TAG, "positionss: " + position);
        for (int i = 0; i < itemList.size(); i++) {
            Log.d(TAG, "boolean: " + itemList.get(i).isClicked());
        }

//        if ((itemList.get(position).isClicked()) == true) {
//            holder.appName.setText(itemList.get(position).getAppName());
//            holder.appIcon.setImageDrawable(itemList.get(position).getAppIcon());
//
//        } else {
            holder.appName.setText(itemList.get(position).getAppName());
            holder.appIcon.setImageDrawable(itemList.get(position).getAppIcon());

//        }
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}

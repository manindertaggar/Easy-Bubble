package com.eworl.easybubble.Adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eworl.easybubble.utils.ItemObject;
import com.eworl.easybubble.R;
import com.eworl.easybubble.ViewHolder.RecyclerViewHolders;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    private List<ItemObject> itemList;
    private Context context;

    public RecyclerViewAdapter(Context context, List<ItemObject> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_list_layout, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView,context,itemList);
        return rcv;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.appName.setText(itemList.get(position).getAppName());
        holder.appIcon.setImageDrawable(itemList.get(position).getAppIcon());
        holder.addIcon.setImageBitmap(itemList.get(position).getAddIcon());
        holder.plusIcon.setImageBitmap(itemList.get(position).getPlusIcon());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}

package com.eworl.easybubble.Adapter;

import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.eworl.easybubble.RecyclerViewListeners.AllitemDragListener;
import com.eworl.easybubble.RecyclerViewListeners.Listener;
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
    private Listener mListener;

    public RvAdapterAllitems(Context context, List<ItemObject> itemList, List<program> log_list, MainActivity mainActivity,Listener listener) {
        this.itemList = itemList;
        this.context = context;
        this.log_list = log_list;
        this.mainActivity = mainActivity;
        this.mListener = listener;
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
            holder.appName.setText(itemList.get(position).getAppName());
            holder.appIcon.setImageDrawable(itemList.get(position).getAppIcon());

        holder.flRecycleViewItem.setTag(position);
        holder.flRecycleViewItem.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        ClipData data = ClipData.newPlainText("", "");
                        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            view.startDragAndDrop(data, shadowBuilder, view, 0);
                        } else {
                            view.startDrag(data, shadowBuilder, view, 0);
                        }
                        return true;
                }
                return false;
            }
        });
        holder.flRecycleViewItem.setOnDragListener(new AllitemDragListener(mListener,mainActivity));
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

   public AllitemDragListener getDragInstance() {
        if (mListener != null) {
            return new AllitemDragListener(mListener,mainActivity);
        } else {
            Log.e("ListAdapter", "Listener wasn't initialized!");
            return null;
        }
    }

   public List<ItemObject> getList() {
        return itemList;
    }

   public void updateList(List<ItemObject> list) {
        itemList = list;
    }

}

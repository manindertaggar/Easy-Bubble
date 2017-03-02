package com.eworl.easybubble.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.eworl.easybubble.RecyclerViewListeners.AllitemDragListener1;
import com.eworl.easybubble.RecyclerViewListeners.AllitemViewTouchListener;
import com.eworl.easybubble.RecyclerViewListeners.Listener;
import com.eworl.easybubble.activities.MainActivity;
import com.eworl.easybubble.db.program;
import com.eworl.easybubble.R;

import java.util.List;

public class RvHolderAllitems extends RecyclerView.ViewHolder {
    private static final String TAG = "RecyclerViewHolder";
    public TextView appName;
    public ImageView appIcon;
    private List<ItemObject> itemList;
    private Context context;
    private AllitemViewTouchListener clickListener;
    private MainActivity mainActivity;
    private View itemView;
    private List<program> log_list;
    public FrameLayout flRecycleViewItem;
    private Listener mListener;
    AllitemDragListener1 allitemDragListener1;

    public RvHolderAllitems(View itemView, Context context, List<ItemObject> itemList, MainActivity mainActivity, List<program> log_list, Listener listener) {
        super(itemView);
        this.itemList = itemList;
        this.context = context;
        this.mainActivity = mainActivity;
        this.itemView = itemView;
        this.log_list = log_list;
        this.mListener = listener;

//        clickListener = new AllitemViewTouchListener(context, itemList, this, mainActivity);

        allitemDragListener1 = new AllitemDragListener1(mListener,context,mainActivity);

        itemView.setOnTouchListener(clickListener);
        appName = (TextView) itemView.findViewById(R.id.country_name);
        appIcon = (ImageView) itemView.findViewById(R.id.appIcon);
        flRecycleViewItem = (FrameLayout) itemView.findViewById(R.id.flRecycleItem);


    }

}


package com.eworl.easybubble.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.eworl.easybubble.R;
import com.eworl.easybubble.RecyclerViewListeners.SelectedItemViewClickListener;
import com.eworl.easybubble.activities.MainActivity;
import com.eworl.easybubble.db.program;
import com.eworl.easybubble.utils.ItemObject;

import java.util.List;

/**
 * Created by Dhankher on 2/27/2017.
 */

public class RvHolderSelectedItems extends RecyclerView.ViewHolder{

    private static final String TAG = "RecyclerViewHolder";
    public TextView appName;
    public ImageView appIcon;
    private List<ItemObject> itemList;
    private Context contaxt;
    private SelectedItemViewClickListener clickListener;
    private MainActivity mainActivity;
    private View itemView;
    private List<program> log_list;
    public FrameLayout flRecycleViewItem;

    public RvHolderSelectedItems(View itemView, Context context, List<ItemObject> itemList, MainActivity mainActivity, List<program> log_list) {
        super(itemView);
        this.itemList = itemList;
        this.contaxt = context;
        this.mainActivity = mainActivity;
        this.itemView = itemView;
        this.log_list = log_list;
        clickListener = new SelectedItemViewClickListener(contaxt, itemList, this, mainActivity);
        itemView.setOnClickListener(clickListener);
        appName = (TextView) itemView.findViewById(R.id.country_name);
        appIcon = (ImageView) itemView.findViewById(R.id.appIcon);
        flRecycleViewItem = (FrameLayout) itemView.findViewById(R.id.flRecycleItem);


    }

}

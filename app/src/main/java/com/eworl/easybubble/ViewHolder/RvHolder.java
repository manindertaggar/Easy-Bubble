package com.eworl.easybubble.ViewHolder;

import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eworl.easybubble.R;
import com.eworl.easybubble.RecyclerViewListeners.AllitemDragListener1;
import com.eworl.easybubble.RecyclerViewListeners.AllitemViewTouchListener;
import com.eworl.easybubble.RecyclerViewListeners.Listener;
import com.eworl.easybubble.activities.MainActivity;
import com.eworl.easybubble.db.Program;

import java.util.List;

/**
 * Created by Dhankher on 3/1/2017.
 */

public class RvHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "RecyclerViewHolder";
    public TextView appName;
    public ImageView appIcon;
    private List<ItemObject> itemList;
    private Context context;
    private AllitemViewTouchListener clickListener;
    private MainActivity mainActivity;
    private View itemView;
    private List<Program> log_list;
    public FrameLayout flRecycleViewItem;
    private Listener mListener;
    private AllitemDragListener1 allitemDragListener1;
    private long startTime, endTime, longClickTimeStarted;
    private boolean longClickStarted = false;

    public RvHolder(View itemView, final Context context, List<ItemObject> itemList, MainActivity mainActivity, List<Program> log_list, Listener listener) {
        super(itemView);
        this.itemList = itemList;
        this.context = context;
        this.mainActivity = mainActivity;
        this.itemView = itemView;
        this.log_list = log_list;
        this.mListener = listener;
        appName = (TextView) itemView.findViewById(R.id.country_name);
        appIcon = (ImageView) itemView.findViewById(R.id.appIcon);
        flRecycleViewItem = (FrameLayout) itemView.findViewById(R.id.flRecycleItem);


//        flRecycleViewItem.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//
//                switch (motionEvent.getAction()) {
//
//                    case MotionEvent.ACTION_DOWN:
//                        startTime = System.currentTimeMillis();
//
//                        if (longClickStarted == false) {
//                            longClickTimeStarted = System.currentTimeMillis();
//                            longClickStarted = true;
//                        }
//                        break;
//
//                    case MotionEvent.ACTION_UP:
//                        endTime = System.currentTimeMillis();
//                        longClickStarted = false;
//                        if (endTime - startTime < 400) {
//                            itemViewonClick();
//                        }
//
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        if (longClickStarted) {
//                            long clickDuration = System.currentTimeMillis() - longClickTimeStarted;
//                            if (clickDuration >= 1000) {
////                                itemViewLongClick(view);
//                                Toast.makeText(context, "LONG PRESSED!", Toast.LENGTH_SHORT).show();
//                                longClickStarted = false;
//                            }
//                        }
//
//                }
//                return false;
//
//            }
//
//
//        });


    }

    private void itemViewLongClick(View view) {

        ClipData data = ClipData.newPlainText("", "");
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            view.startDragAndDrop(data, shadowBuilder, view, 0);
        } else {
            view.startDrag(data, shadowBuilder, view, 0);
        }
        allitemDragListener1 = new AllitemDragListener1(mListener, context, mainActivity);
        flRecycleViewItem.setOnDragListener(allitemDragListener1);
    }

    private void itemViewonClick() {
        Toast.makeText(context, "onclick", Toast.LENGTH_SHORT).show();
//        clickListener = new AllitemViewTouchListener(context, itemList, this, mainActivity);
//        itemView.setOnTouchListener(clickListener);
    }

}
package com.eworl.easybubble.RecyclerViewListeners;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;

import com.eworl.easybubble.Adapter.RvAdapterAllitems;
import com.eworl.easybubble.Adapter.RvAdapterSelectedItems;
import com.eworl.easybubble.R;
import com.eworl.easybubble.activities.MainActivity;
import com.eworl.easybubble.db.program;
import com.eworl.easybubble.db.programDao;
import com.eworl.easybubble.utils.ItemObject;

import java.util.List;

/**
 * Created by Dhankher on 2/28/2017.
 */

public class SelecteditemDragListener implements View.OnDragListener {

    private static final String TAG = "SelectedItemsListener";
    private Listener mListener;
    private boolean isDropped = false;
    private Context context;
    private  MainActivity mainActivity;
    private programDao programDaoObject;

    public SelecteditemDragListener(Listener listener, Context context, MainActivity mainActivity) {
        this.mListener = listener;
        this.context = context;
        this.mainActivity = mainActivity;
        programDaoObject = mainActivity.getProgramDaoInstance();

    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        switch (dragEvent.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                break;
            case DragEvent.ACTION_DROP:
                isDropped = true;
                int positionTarget = -1;

                View viewSource = (View) dragEvent.getLocalState();

                if (view.getId() == R.id.flRecycleItem || view.getId() == R.id.TVAllItemListEmpty
                        || view.getId() == R.id.TVSelectedItemListEmpty) {
                    RecyclerView target;

                    if (view.getId() == R.id.TVSelectedItemListEmpty) {
                        target = (RecyclerView) view.getRootView()
                                .findViewById(R.id.rvSelectedAppList);
                    } else if (view.getId() == R.id.TVAllItemListEmpty) {
                        target = (RecyclerView) view.getRootView()
                                .findViewById(R.id.rvAppList);
                    } else {
                        target = (RecyclerView) view.getParent();
                        positionTarget = (int) view.getTag();
                    }

                    RecyclerView source = (RecyclerView) viewSource.getParent();

                    RvAdapterSelectedItems adapterSource = (RvAdapterSelectedItems) source.getAdapter();
                    int positionSource = (int) viewSource.getTag();

                    program list = adapterSource.getList().get(positionSource);
                    Log.d(TAG, "onDrag: "+list.getAppName());
                    List<program> log_list = adapterSource.getList();

                    log_list.remove(positionSource);
                    adapterSource.updateList(log_list);
                    adapterSource.notifyDataSetChanged();

                    RvAdapterAllitems adapterTarget = (RvAdapterAllitems) target.getAdapter();
                    List<ItemObject> itemList = adapterTarget.getList();
                    if (positionTarget >= 0) {
                        String img = list.getAppIcon();
                        byte[] bitmapdata = Base64.decode(img, Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
                        Drawable appIcon = new BitmapDrawable(context.getResources(), bitmap);
                        itemList.add(positionTarget, new ItemObject(list.getAppName(),appIcon,list.getAppIcon(),true));
                    } else {
                        String img = list.getAppIcon();
                        byte[] bitmapdata = Base64.decode(img, Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
                        Drawable appIcon = new BitmapDrawable(context.getResources(), bitmap);
                        itemList.add(new ItemObject(list.getAppName(),appIcon,list.getAppIcon(),true));
                    }
                    adapterTarget.updateList(itemList);
                    adapterTarget.notifyDataSetChanged();
                    view.setVisibility(View.VISIBLE);

                    if (source.getId() == R.id.rvAppList
                            && adapterSource.getItemCount() < 1) {
                        mListener.setEmptyListBottom(true);
                    }

                    if (view.getId() == R.id.TVAllItemListEmpty) {
                        mListener.setEmptyListBottom(false);
                    }

                    if (source.getId() == R.id.rvSelectedAppList
                            && adapterSource.getItemCount() < 1) {
                        mListener.setEmptyListTop(true);
                    }

                    if (view.getId() == R.id.TVSelectedItemListEmpty) {
                        mListener.setEmptyListTop(false);
                    }
                }
                break;
        }

        if (!isDropped) {
            ((View) dragEvent.getLocalState()).setVisibility(View.VISIBLE);
        }

        return true;
    }
}

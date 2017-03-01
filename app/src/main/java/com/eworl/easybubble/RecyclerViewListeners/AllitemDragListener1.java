package com.eworl.easybubble.RecyclerViewListeners;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.DragEvent;
import android.view.View;

import com.eworl.easybubble.Adapter.RvAdapterAllitems;
import com.eworl.easybubble.Adapter.RvAdapterSelectedItems;
import com.eworl.easybubble.R;
import com.eworl.easybubble.activities.MainActivity;
import com.eworl.easybubble.db.program;
import com.eworl.easybubble.db.programDao;
import com.eworl.easybubble.utils.ItemObject;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by Dhankher on 2/28/2017.
 */
public class AllitemDragListener1 implements View.OnDragListener {
    private Listener mListener;
    private boolean isDropped = false;
    private MainActivity mainActivity;
    private programDao programDaoObject;
    private Context context;

    public AllitemDragListener1(Listener listener, Context context, MainActivity mainActivity) {
        this.mListener = listener;
        this.mainActivity = mainActivity;
        this.context = context;
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

                    RvAdapterAllitems adapterSource = (RvAdapterAllitems) source.getAdapter();
                    int positionSource = (int) viewSource.getTag();

                    ItemObject allAppsListItem = adapterSource.getList().get(positionSource);
                    List<ItemObject> itemList = adapterSource.getList();

                    itemList.remove(positionSource);
                    adapterSource.updateList(itemList);
                    adapterSource.notifyDataSetChanged();

                    RvAdapterSelectedItems adapterTarget = (RvAdapterSelectedItems) target.getAdapter();
                    List<program> log_list = adapterTarget.getList();

                    if (positionTarget >= 0) {

                        Bitmap img = ((BitmapDrawable) allAppsListItem.getAppIcon()).getBitmap();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        img.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] imageInByte = stream.toByteArray();
                       String image = Base64.encodeToString(imageInByte, Base64.DEFAULT);

                        log_list.add(positionTarget, new program(null,allAppsListItem.getAppName(),image,allAppsListItem.getPackagename()));

                    } else {
                        Bitmap img = ((BitmapDrawable) allAppsListItem.getAppIcon()).getBitmap();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        img.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] imageInByte = stream.toByteArray();
                        String image = Base64.encodeToString(imageInByte, Base64.DEFAULT);
                        log_list.add(new program(null,allAppsListItem.getAppName(),image,allAppsListItem.getPackagename()));
                    }
                    adapterTarget.updateList(log_list);
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

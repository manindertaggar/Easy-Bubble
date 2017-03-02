package com.eworl.easybubble.RecyclerViewListeners;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;

import com.eworl.easybubble.Adapter.RvAdapterAllitems;
import com.eworl.easybubble.Adapter.RvAdapterSelectedItems;
import com.eworl.easybubble.R;
import com.eworl.easybubble.activities.MainActivity;
import com.eworl.easybubble.db.program;
import com.eworl.easybubble.db.programDao;

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
                    Log.d(TAG, "sourceeeee: "+source);
                    RvAdapterAllitems adapterSource = (RvAdapterAllitems) source.getAdapter();
                    int positionSource = (int) viewSource.getTag();
///////////////////////////////////

//                    program selectedListItem = adapterSource.getList().get(positionSource);
//                    Log.d(TAG, "onDrag: "+selectedListItem.getAppName());
//                    List<program> log_list = adapterSource.getList();
//                    log_list.remove(positionSource);
//                    adapterSource.updateList(log_list);
//                    adapterSource.notifyDataSetChanged();

//                    Log.d(TAG, "list size before deleting: "+log_list.size());
//                    programDaoObject.delete(selectedListItem);
//                    Log.d(TAG, "list size after deleting: "+log_list.size());
//                    adapterSource.updateList(log_list);
//                    adapterSource.notifyDataSetChanged();
//
//
//
//                    RvAdapterAllitems adapterTarget = (RvAdapterAllitems) target.getAdapter();
//                    List<ItemObject> itemList = adapterTarget.getList();
//                    if (positionTarget >= 0) {
//                        String img = selectedListItem.getAppIcon();
//                        byte[] bitmapdata = Base64.decode(img, Base64.DEFAULT);
//                        Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
//                        Drawable appIcon = new BitmapDrawable(context.getResources(), bitmap);
//                        itemList.add(positionTarget, new ItemObject(selectedListItem.getAppName(),appIcon,selectedListItem.getAppIcon(),true));
//                    } else {
//                        String img = selectedListItem.getAppIcon();
//                        byte[] bitmapdata = Base64.decode(img, Base64.DEFAULT);
//                        Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
//                        Drawable appIcon = new BitmapDrawable(context.getResources(), bitmap);
//                        itemList.add(new ItemObject(selectedListItem.getAppName(),appIcon,selectedListItem.getAppIcon(),true));
//                    }
//                    adapterTarget.updateList(itemList);
//                    adapterTarget.notifyDataSetChanged();
//                    view.setVisibility(View.VISIBLE);
//
//                    if (source.getId() == R.id.rvAppList
//                            && adapterSource.getItemCount() < 1) {
//                        mListener.setEmptyListBottom(true);
//                    }
//
//                    if (view.getId() == R.id.TVAllItemListEmpty) {
//                        mListener.setEmptyListBottom(false);
//                    }
//
//                    if (source.getId() == R.id.rvSelectedAppList
//                            && adapterSource.getItemCount() < 1) {
//                        mListener.setEmptyListTop(true);
//                    }
//
//                    if (view.getId() == R.id.TVSelectedItemListEmpty) {
//                        mListener.setEmptyListTop(false);
//                    }
//                }
//                break;
//        }
//
//        if (!isDropped) {
//            ((View) dragEvent.getLocalState()).setVisibility(View.VISIBLE);
//        }
//
//        return true;
//    }
//}
                    ItemObject allAppsListItem = adapterSource.getList().get(positionSource);
                    List<ItemObject> itemList = adapterSource.getList();

                    itemList.remove(positionSource);
                    adapterSource.updateList(itemList);
                    adapterSource.notifyDataSetChanged();

                    RvAdapterSelectedItems adapterTarget = (RvAdapterSelectedItems) target.getAdapter();
                    List<program> log_list = adapterTarget.getList();

                    if (positionTarget >= 0) {

//                        Bitmap img = ((BitmapDrawable) allAppsListItem.getAppIcon()).getBitmap();
//                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                        img.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                        byte[] imageInByte = stream.toByteArray();
//                        String image = Base64.encodeToString(imageInByte, Base64.DEFAULT);

                        log_list.add(positionTarget, new program(null,allAppsListItem.getAppName(), allAppsListItem.getAppIcon(),allAppsListItem.getPackagename()));

                    } else {
//                        Bitmap img = ((BitmapDrawable) allAppsListItem.getAppIcon()).getBitmap();
//                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                        img.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                        byte[] imageInByte = stream.toByteArray();
//                        String image = Base64.encodeToString(imageInByte, Base64.DEFAULT);
                        log_list.add(new program(null,allAppsListItem.getAppName(),allAppsListItem.getAppIcon(),allAppsListItem.getPackagename()));
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
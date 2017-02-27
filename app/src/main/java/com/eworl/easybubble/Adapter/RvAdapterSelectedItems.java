package com.eworl.easybubble.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eworl.easybubble.R;
import com.eworl.easybubble.ViewHolder.RvHolderAllitems;
import com.eworl.easybubble.ViewHolder.RvHolderSelectedItems;
import com.eworl.easybubble.activities.MainActivity;
import com.eworl.easybubble.db.program;
import com.eworl.easybubble.utils.ItemObject;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Dhankher on 2/27/2017.
 */

public class RvAdapterSelectedItems extends RecyclerView.Adapter<RvHolderSelectedItems> {


    private static final String TAG = "RvAdapterSelectedItems";
    private List<ItemObject> itemList;
    private Context context;
    private List<program> log_list;
    private MainActivity mainActivity;

    public RvAdapterSelectedItems(Context context, List<ItemObject> itemList, List<program> log_list, MainActivity mainActivity) {
        this.itemList = itemList;
        this.context = context;
        this.log_list = log_list;
        this.mainActivity = mainActivity;
    }

    @Override
    public RvHolderSelectedItems onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_list_layout, null);
        RvHolderSelectedItems rcv = new RvHolderSelectedItems(layoutView, context, itemList, mainActivity, log_list);
        return rcv;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(RvHolderSelectedItems holder, int position) {


        Log.d(TAG, "positionss: " + position);
        for (int i = 0; i < itemList.size(); i++) {
            Log.d(TAG, "boolean: " + itemList.get(i).isClicked());
        }

        holder.appName.setText(log_list.get(position).getAppName());
        String img = log_list.get(position).getAppIcon();
        byte[] bitmapdata = Base64.decode(img, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
        holder.appIcon.setImageBitmap(bitmap);

    }

    @Override
    public int getItemCount() {
        return this.log_list.size();
    }
}

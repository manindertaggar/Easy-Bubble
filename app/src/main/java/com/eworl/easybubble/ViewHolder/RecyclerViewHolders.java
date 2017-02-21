package com.eworl.easybubble.ViewHolder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eworl.easybubble.utils.ItemObject;
import com.eworl.easybubble.R;

import java.util.List;

public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView appName;
    public ImageView appIcon;
    public ImageView addIcon;
    public ImageView plusIcon;
    List<ItemObject> itemList;
    Context contaxt;

    public RecyclerViewHolders(View itemView, Context context, List<ItemObject> itemList) {
        super(itemView);
        this.itemList = itemList;
        this.contaxt = context;
//        EventBus.getDefault().register(context);
        itemView.setOnClickListener(this);
        appName = (TextView) itemView.findViewById(R.id.country_name);
        appIcon = (ImageView) itemView.findViewById(R.id.appIcon);
        addIcon = (ImageView) itemView.findViewById(R.id.addIcon);
        plusIcon = (ImageView) itemView.findViewById(R.id.plusIcon);
    }

    @Override
    public void onClick(View view) {
//        itemList.get(getAdapterPosition()).getAddIcon().setImageBitmap(R.drawable.add);//.setBackgroundColor(Color.RED);
//        addIcon.setBackgroundColor(Color.RED);

//        itemList.get(getAdapterPosition()).setAddIcon(R.drawable.facebook);
//        addIcon.setImageResource(R.drawable.facebook);
//        addIcon.setBackgroundColor(Color.RED);
//        Toast.makeText(view.getContext(), "Clicked list Position = " + getPosition(), Toast.LENGTH_SHORT).show();
        Toast.makeText(view.getContext(), "Clicked list Position = " + getPosition(), Toast.LENGTH_SHORT).show();

    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessageEvent(ItemListEvA    evt evevt) {
//         allItems =   evevt.getAllItems();
//
//        Log.d(TAG, "onMessageEvent: "+allItems.get(1).getAppName());
//}


}
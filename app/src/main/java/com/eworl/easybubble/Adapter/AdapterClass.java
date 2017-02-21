package com.eworl.easybubble.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.eworl.easybubble.R;
import com.eworl.easybubble.activities.MainActivity;

import java.util.ArrayList;

/**
 * Created by Dhankher on 2/15/2017.
 */
public class AdapterClass extends RecyclerView.Adapter<AdapterClass.viewHolder> {

    Context context;
    ArrayList<Bitmap> appIcons;
    ArrayList<String> appNames;

    public AdapterClass(Context context, ArrayList<Bitmap> appIcons, ArrayList<String> appNames) {
        this.context = context;
        this.appIcons = appIcons;
        this.appNames = appNames;

    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(context).inflate(R.layout.app_list_layout, parent, false);
        return new viewHolder(myView);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        holder.appNameTV.setText(appNames.get(position));
        holder.appIconIV.setImageBitmap(appIcons.get(position));
    }


    @Override
    public int getItemCount() {
        return appNames.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView appNameTV;
        ImageView appIconIV;

        public viewHolder(View itemView) {
            super(itemView);
            appNameTV = (TextView) itemView.findViewById(R.id.appNameTV);
            appIconIV = (ImageView)itemView.findViewById(R.id.appIconIV);
        }

    }
}

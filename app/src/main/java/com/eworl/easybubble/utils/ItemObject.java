package com.eworl.easybubble.utils;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.widget.ImageView;

public class ItemObject {

    private String appName;
    private Drawable appIcon;
    private int addIcon;
    private  int plusIcon;

    public ItemObject(String appName, Drawable appIcon, int addIcon, int plusIcon) {
        this.appName = appName;
        this.appIcon = appIcon;
        this.addIcon = addIcon;
        this.plusIcon = plusIcon;
    }



    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public int getAddIcon() {
        return addIcon;
    }

    public void setAddIcon(int addIcon) {
        this.addIcon = addIcon;
    }
    public int getPlusIcon() {
        return plusIcon;
    }

    public void setPlusIcon(int plusIcon) {
        this.plusIcon = plusIcon;
    }
}
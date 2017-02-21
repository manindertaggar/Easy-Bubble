package com.eworl.easybubble.utils;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.widget.ImageView;

public class ItemObject {

    private String appName;
    private Drawable appIcon;
    private Bitmap addIcon;
    private  Bitmap plusIcon;

    public ItemObject(String appName, Drawable appIcon, Bitmap addIcon,Bitmap plusIcon) {
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

    public Bitmap getAddIcon() {
        return addIcon;
    }

    public void setAddIcon(Bitmap addIcon) {
        this.addIcon = addIcon;
    }
    public Bitmap getPlusIcon() {
        return plusIcon;
    }

    public void setPlusIcon(Bitmap plusIcon) {
        this.plusIcon = plusIcon;
    }
}
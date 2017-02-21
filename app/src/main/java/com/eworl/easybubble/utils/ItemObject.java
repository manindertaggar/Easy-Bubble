package com.eworl.easybubble.utils;


import android.graphics.drawable.Drawable;

public class ItemObject {

    private String appName;
    private Drawable appIcon;
    private int addIcon;

    public ItemObject(String appName, Drawable appIcon, int addIcon) {
        this.appName = appName;
        this.appIcon = appIcon;
        this.addIcon = addIcon;
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
}
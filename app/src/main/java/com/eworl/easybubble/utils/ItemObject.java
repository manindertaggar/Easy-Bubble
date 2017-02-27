package com.eworl.easybubble.utils;


import android.graphics.drawable.Drawable;

public class ItemObject {

    private String appName;
    private Drawable appIcon;
    private String packagename;
    private boolean isClicked;

    public ItemObject(String appName, Drawable appIcon, String packagename, boolean isClicked) {
        this.appName = appName;
        this.appIcon = appIcon;
        this.packagename = packagename;
        this.isClicked = isClicked;
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

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean isClicked) {
        this.isClicked = isClicked;
    }
}
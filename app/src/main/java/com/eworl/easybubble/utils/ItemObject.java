package com.eworl.easybubble.utils;


import android.graphics.drawable.Drawable;

public class ItemObject {

    private String appName;
    private Drawable appIcon;
    private int greenIcon;
    private  int plusIcon;
    private String packagename;

    public ItemObject(String appName, Drawable appIcon, int greenIcon, int plusIcon, String packagename) {
        this.appName = appName;
        this.appIcon = appIcon;
        this.greenIcon = greenIcon;
        this.plusIcon = plusIcon;
        this.packagename = packagename;
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

    public int getGreenIcon() {
        return greenIcon;
    }

    public void setGreenIcon(int greenIcon) {
        this.greenIcon = greenIcon;
    }
    public int getPlusIcon() {
        return plusIcon;
    }

    public void setPlusIcon(int plusIcon) {
        this.plusIcon = plusIcon;
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }
}
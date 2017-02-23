package com.eworl.easybubble.utils;


import android.graphics.drawable.Drawable;

public class ItemObject {

    private String appName;
    private Drawable appIcon;
    private int greenIcon;
    private  int plusIcon;
    private String packagename;
    private int redIcon;
    private int crossIcon;

    public ItemObject(String appName, Drawable appIcon, int greenIcon, int plusIcon, String packagename, int redIcon, int crossIcon) {
        this.appName = appName;
        this.appIcon = appIcon;
        this.greenIcon = greenIcon;
        this.plusIcon = plusIcon;
        this.packagename = packagename;
        this.redIcon = redIcon;
        this.crossIcon = crossIcon;
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

    public int getRedIcon() {
        return redIcon;
    }

    public void setRedIcon(int redIcon) {
        this.redIcon = redIcon;
    }

    public int getCrossIcon() {
        return crossIcon;
    }

    public void setCrossIcon(int crossIcon) {
        this.crossIcon = crossIcon;
    }
}
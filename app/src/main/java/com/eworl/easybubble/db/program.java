package com.eworl.easybubble.db;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "PROGRAM".
 */
public class program {

    private Long id;
    private String appName;
    private Integer appIcon;

    public program() {
    }

    public program(Long id) {
        this.id = id;
    }

    public program(Long id, String appName, Integer appIcon) {
        this.id = id;
        this.appName = appName;
        this.appIcon = appIcon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Integer getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Integer appIcon) {
        this.appIcon = appIcon;
    }

}

package com.eworl.easybubble.activities;

import android.app.ProgressDialog;

/**
 * Created by Dhankher on 3/3/2017.
 */
public class FetchingAppsTask implements Runnable {
    ProgressDialog progress;

    public FetchingAppsTask(ProgressDialog progress) {
        this.progress = progress;
    }

    @Override
    public void run() {



    }
}

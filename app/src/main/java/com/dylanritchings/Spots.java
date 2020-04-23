package com.dylanritchings;

import android.app.Application;
import android.content.Context;

public class Spots extends Application {
    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        Spots.mContext = mContext;
    }
}

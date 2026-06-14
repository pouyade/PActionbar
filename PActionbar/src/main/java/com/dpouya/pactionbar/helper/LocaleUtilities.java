package com.dpouya.pactionbar.helper;

import android.content.Context;
import android.content.res.Configuration;
import android.view.View;

public class LocaleUtilities {
    public static boolean isRTL(Context context) {
        Configuration config = context.getResources().getConfiguration();
        return config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
    }
}

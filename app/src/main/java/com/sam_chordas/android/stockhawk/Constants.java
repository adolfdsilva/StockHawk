package com.sam_chordas.android.stockhawk;

import android.util.Log;

/**
 * Created by Audi on 17/11/16.
 */

public class Constants {

    public static final String TAG = "STOCK_HAWK";
    public static final String SYMBOL = "symbol";

    public static void debug(String msg) {
        if (msg == null)
            msg = "Msg is null";
        Log.d(TAG, msg);
    }
}

package com.openwallet.network.mock;

import android.content.Context;


public final class RetrofitMock {

    private RetrofitMock() {
    }

    public static void init(Context context, String path) {
        MockDataManager.get().init(context.getApplicationContext(), path);
    }
}
package com.openwallet.network.mock;

import okhttp3.Request;


public class SimpleMockInterceptor extends MockInterceptor {

    private final boolean enableMock;

    public SimpleMockInterceptor(boolean enableMock) {
        this.enableMock = enableMock;
    }

    @Override
    public boolean accept(Request request) {
        return enableMock;
    }
}
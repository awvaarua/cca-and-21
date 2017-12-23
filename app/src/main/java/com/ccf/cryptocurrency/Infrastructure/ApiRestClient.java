package com.ccf.cryptocurrency.Infrastructure;

import com.loopj.android.http.*;

import javax.net.ssl.HostnameVerifier;

public class ApiRestClient {
    private static final String BASE_URL = "https://77fdeb9d.ngrok.io/api";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void addHeader(String name, String value) {
        client.addHeader(name, value);
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}

package com.ccf.cryptocurrency.Infrastructure;

import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import javax.net.ssl.HostnameVerifier;

import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiRestClient {
    private static final String BASE_URL = "https://77fdeb9d.ngrok.io/api";
    private static String SESSION = "";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static AsyncHttpClient clientAsync = new AsyncHttpClient();
    private static OkHttpClient client = new OkHttpClient();

    public static void setSession(String session) {
        SESSION = session;
        clientAsync.addHeader("Authoritzation", session);
    }

    public static void addHeaderAsync(String name, String value) {
        clientAsync.addHeader(name, value);
    }

    public static void getAsync(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        clientAsync.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void postAsync(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        clientAsync.post(getAbsoluteUrl(url), params, responseHandler);
    }

    // Not async


    public static JSONArray get(String url) {
        Request request = null;
        if (SESSION != "" && SESSION != null) {
            request = new Request.Builder().url(getAbsoluteUrl(url)).header("Authoritzation", SESSION).build();
        } else {
            request = new Request.Builder().url(getAbsoluteUrl(url)).build();
        }
        try {
            Response response = client.newCall(request).execute();
            return new JSONArray(response.body().string());
        } catch (IOException e) {
            return new JSONArray();
        } catch (JSONException e) {
            System.err.println(e);
            return new JSONArray();
        }
    }

    public static JSONArray post(String url, JSONObject jsonBody) {
        Request request = null;
        RequestBody body = RequestBody.create(JSON, jsonBody.toString());
        if (SESSION != "" && SESSION != null) {
            request = new Request.Builder().url(url).post(body).header("Authoritzation", SESSION).build();
        } else {
            request = new Request.Builder().url(url).build();
        }
        try {
            Response response = client.newCall(request).execute();
            return new JSONArray(response.body().string());
        } catch (IOException e) {
            return new JSONArray();
        } catch (JSONException e) {
            return new JSONArray();
        }
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}

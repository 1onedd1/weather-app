package com.publisher.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class HttpURLConnectionBuilder {
    private HttpURLConnection httpURLConnection;

    private HttpURLConnectionBuilder() {
    }

    public static HttpURLConnectionBuilder builder() {
        return new HttpURLConnectionBuilder();
    }

    public HttpURLConnectionBuilder setURL(String url) {
        try {
            URL u = new URL(url);
            httpURLConnection = (HttpURLConnection) u.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this;
    }

    public HttpURLConnectionBuilder setMethod(String method) {
        try {
            httpURLConnection.setRequestMethod(method);
        } catch (ProtocolException e) {
            e.printStackTrace();
        }

        return this;
    }

    public HttpURLConnectionBuilder setRequestProperty(String key, String value) {
        httpURLConnection.setRequestProperty(key, value);
        return this;
    }

    public HttpURLConnection build() {
        return httpURLConnection;
    }
}
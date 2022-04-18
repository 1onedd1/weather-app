package com.publisher.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    private boolean isConnectionSuccessful() {
        try {
            if(httpURLConnection.getResponseCode() == 200) return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public String ifConnectionSuccessfulGetResponse() {
        if(!isConnectionSuccessful()) return "";

        String output = "";

        try {
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while ((output = bufferedReader.readLine()) != null) {
                stringBuilder.append(output);
            }

            output = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        httpURLConnection.disconnect();

        return output;
    }

    public HttpURLConnection build() {
        return httpURLConnection;
    }
}
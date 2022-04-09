package com.weatherapplication.demo.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.function.Consumer;

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

    public void ifConnectionSuccessfulThen(Consumer<String> consumer) {
        try {
            if (httpURLConnection.getResponseCode() != 200) return;

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String output;
            while ((output = bufferedReader.readLine()) != null) {
                stringBuilder.append(output);
            }
            httpURLConnection.disconnect();

            consumer.accept(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

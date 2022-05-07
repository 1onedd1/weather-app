package com.publisher.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;

public class HttpURLConnectionService {
    public boolean isConnectionSuccessful(HttpURLConnection connection) throws IOException {
        if (connection.getResponseCode() == 200) return true;

        return false;
    }

    public String readResponse(HttpURLConnection connection) throws IOException {
        String output = "";

        if (!isConnectionSuccessful(connection)) throw new ProtocolException();

        InputStream inputStream = connection.getInputStream();
        InputStreamReader streamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(streamReader);
        StringBuilder stringBuilder = new StringBuilder();

        while ((output = bufferedReader.readLine()) != null) {
            stringBuilder.append(output);
        }

        output = stringBuilder.toString();

        return output;
    }
}

package com.cannawen.reader.utility;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtility {

    String LOG_TAG = "NetworkUtility";

    public String getRssString() throws IOException {
        URL url = new URL("http://reader-server.cannawen.com/api/generate-rss");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setReadTimeout(5 * 1000);
        httpURLConnection.setConnectTimeout(5000);
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setDoInput(true);

        httpURLConnection.connect();

        int response = httpURLConnection.getResponseCode();

        String rssString = null;
        if (response == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = httpURLConnection.getInputStream();
            rssString = streamToString(inputStream);
            inputStream.close();
        }

        httpURLConnection.disconnect();
        return rssString;
    }

    private String streamToString(InputStream stream) {
        InputStreamReader isr = new InputStreamReader(stream);
        BufferedReader reader = new BufferedReader(isr);
        StringBuilder response = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error in StreamToString", e);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error in StreamToString", e);
        } finally {

            try {
                stream.close();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error in StreamToString", e);
            } catch (Exception e) {
                Log.e(LOG_TAG, "Error in StreamToString", e);
            }
        }
        return response.toString();

    }
}

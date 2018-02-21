package com.cannawen.reader.utility.BookFetcher;

import android.content.Context;
import android.content.res.AssetManager;
import android.speech.tts.TextToSpeech;

import com.cannawen.reader.model.Book;
import com.cannawen.reader.model.Chapter;
import com.cannawen.reader.utility.FetchBookSuccessListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LocalDotaBookFetcher implements BookFetcher {
    private Context context;
    private TextToSpeech tts;

    public LocalDotaBookFetcher(Context context) {
        this.context = context;
    }

    public void fetchBook(final FetchBookSuccessListener listener) {
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                initBook(listener);
            }
        });
    }

    private void initBook(FetchBookSuccessListener listener) {
        try {
            String folder = "dota";

            AssetManager assetManager = context.getAssets();
            String[] assets = assetManager.list(folder);

            List<Chapter> chapters = new ArrayList<>();
            int chapterIndex = 0;
            for (String asset : assets) {
                InputStream inputStream = assetManager.open(folder + "/" + asset);

                BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder total = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    total.append(line).append('\n');
                }
                String heroName = asset.substring(0, asset.lastIndexOf('.'));
                chapters.add(new Chapter(context, chapterIndex, heroName, total.toString(), tts));

                chapterIndex++;
            }
            listener.fetchBookSuccess(new Book(chapters));
        } catch (IOException e) {
            e.printStackTrace();
            listener.fetchBookFailed();
        }
    }
}

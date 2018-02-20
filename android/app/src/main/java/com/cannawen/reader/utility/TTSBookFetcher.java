package com.cannawen.reader.utility;

import android.content.Context;
import android.content.res.AssetManager;
import android.speech.tts.TextToSpeech;

import com.cannawen.reader.model.book.Book;
import com.cannawen.reader.model.chapter.TTSChapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TTSBookFetcher {
    private Context context;
    private Book book;

    public TTSBookFetcher(Context context, TextToSpeech tts) {
        this.context = context;
        try {
            this.book = initBook(tts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Book getBook() {
        return book;
    }

    private Book initBook(TextToSpeech tts) throws IOException {
        String folder = "dota";

        AssetManager assetManager = context.getAssets();
        String[] assets = assetManager.list(folder);

        List<TTSChapter> chapters = new ArrayList<>();
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
            chapters.add(new TTSChapter(chapterIndex, heroName, total.toString(), tts));

            chapterIndex++;
        }
        return new Book(chapters);
    }
}

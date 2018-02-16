package com.cannawen.reader.utility;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.cannawen.reader.model.Book;
import com.cannawen.reader.model.Chapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BookFetcher {
    private Context context;
    private Book book;

    public BookFetcher(Context context) {
        this.context = context;
        try {
            book = initBook();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Book getBook() {
        return book;
    }

    private Book initBook() throws IOException {
        AssetManager assetManager = context.getAssets();
        String[] assets = assetManager.list("dota");

        List<Chapter> chapters = new ArrayList<>();
        for (String asset : assets) {
            InputStream inputStream = assetManager.open("dota/" + asset);

            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }
            chapters.add(new Chapter(total.toString()));
        }
        return new Book(chapters);
    }
}

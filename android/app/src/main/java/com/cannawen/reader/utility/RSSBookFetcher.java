package com.cannawen.reader.utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.cannawen.reader.model.book.Book;
import com.cannawen.reader.model.chapter.AudioChapter;
import com.pkmmte.pkrss.Article;
import com.pkmmte.pkrss.PkRSS;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class RSSBookFetcher {
    String LOG_TAG = "RSSBookFetcher";
    private Context context;
    private Book book;
    private NetworkUtility networkUtility;

    public RSSBookFetcher(Context context, NetworkUtility networkUtility) {
        this.context = context;
        this.networkUtility = networkUtility;
        initBookFromNetwork();
    }

    public Book getBook() {
        return book;
    }

    @SuppressLint("StaticFieldLeak")
    private void initBookFromNetwork() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    final List<Article> articles  = PkRSS.with(context).load("http://reader-server.cannawen.com/api/generate-rss").get();
                    List<AudioChapter> chapters = new ArrayList<>();
                    for (int i = 0; i < articles.size(); i++) {
                        Article article = articles.get(i);
                        chapters.add(new AudioChapter(i, article.getTitle().trim(), article.getSource().getPath().trim()));
                    }
                    book = new Book(chapters);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();

    }


}

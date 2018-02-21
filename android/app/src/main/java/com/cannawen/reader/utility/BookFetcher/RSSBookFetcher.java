package com.cannawen.reader.utility.BookFetcher;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;

import com.cannawen.reader.model.Book;
import com.cannawen.reader.model.Chapter;
import com.cannawen.reader.utility.FetchBookSuccessListener;
import com.pkmmte.pkrss.Article;
import com.pkmmte.pkrss.PkRSS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RSSBookFetcher implements BookFetcher {
    private Context context;
    private TextToSpeech tts;

    public RSSBookFetcher(Context context) {
        this.context = context;
    }

    @SuppressLint("StaticFieldLeak")
    public void fetchBook(final FetchBookSuccessListener listener) {
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                new AsyncTask<Void, Void, Book>() {
                    @Override
                    protected Book doInBackground(Void... voids) {
                        try {
                            final List<Article> articles = PkRSS.with(context).load("http://reader-server.cannawen.com/api/generate-rss").get();
                            List<Chapter> chapters = new ArrayList<>();
                            for (int i = 0; i < articles.size(); i++) {
                                Article article = articles.get(i);

                                String title = article.getTitle().trim();

                                Uri uri = article.getSource();
                                String description = article.getDescription();

                                if (uri != null && uri.toString().trim().endsWith(".mp3")) {
                                    chapters.add(new Chapter(context, i, title, uri.toString().trim()));
                                } else if (description != null) {
                                    chapters.add(new Chapter(context, i, title, description.trim(), tts));
                                }
                            }
                            return new Book(chapters);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Book book) {
                        super.onPostExecute(book);

                        if (book == null) {
                            listener.fetchBookFailed();
                        } else {
                            listener.fetchBookSuccess(book);
                        }
                    }
                }.execute();
            }
        });
    }
}

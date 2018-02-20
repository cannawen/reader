package com.cannawen.reader.utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.cannawen.reader.model.book.Book;
import com.cannawen.reader.model.chapter.AudioChapter;
import com.pkmmte.pkrss.Article;
import com.pkmmte.pkrss.PkRSS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RSSBookFetcher {
    private Context context;
    private Book book;
    private SuccessListener listener;

    public RSSBookFetcher(Context context, SuccessListener listener) {
        this.context = context;
        this.listener = listener;
        initBookFromNetwork();
    }

    public Book getBook() {
        return book;
    }

    @SuppressLint("StaticFieldLeak")
    private void initBookFromNetwork() {
        new AsyncTask<Void, Void, Book>() {
            @Override
            protected Book doInBackground(Void... voids) {
                try {
                    final List<Article> articles = PkRSS.with(context).load("http://reader-server.cannawen.com/api/generate-rss").get();
                    List<AudioChapter> chapters = new ArrayList<>();
                    for (int i = 0; i < articles.size(); i++) {
                        Article article = articles.get(i);
                        chapters.add(new AudioChapter(i, article.getTitle().trim(), article.getSource().toString().trim()));
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
                    RSSBookFetcher.this.book = book;
                    listener.fetchBookSuccess(book);
                }
            }
        }.execute();

    }

    public interface SuccessListener {
        void fetchBookSuccess(Book book);

        void fetchBookFailed();
    }
}

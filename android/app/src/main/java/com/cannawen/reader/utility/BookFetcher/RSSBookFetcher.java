package com.cannawen.reader.utility.BookFetcher;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

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

    public RSSBookFetcher(Context context) {
        this.context = context;
    }

    @SuppressLint("StaticFieldLeak")
    public void fetchBook(final FetchBookSuccessListener listener) {
        new AsyncTask<Void, Void, Book>() {
            @Override
            protected Book doInBackground(Void... voids) {
                try {
                    final List<Article> articles = PkRSS.with(context).load("http://reader-server.cannawen.com/api/generate-rss").get();
                    List<Chapter> chapters = new ArrayList<>();
                    for (int i = 0; i < articles.size(); i++) {
                        Article article = articles.get(i);
                        chapters.add(new Chapter(context, i, article.getTitle().trim(), article.getSource().toString().trim()));
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
}

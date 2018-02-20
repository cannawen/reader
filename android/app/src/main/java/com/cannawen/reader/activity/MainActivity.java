package com.cannawen.reader.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cannawen.reader.R;
import com.cannawen.reader.adapter.BookAdapter;
import com.cannawen.reader.model.book.BookChangeListener;
import com.cannawen.reader.utility.NetworkUtility;
import com.cannawen.reader.utility.RSSBookFetcher;

public class MainActivity extends AppCompatActivity implements BookChangeListener {

    private RSSBookFetcher bookFetcher;
    private BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookFetcher = new RSSBookFetcher(getApplicationContext(), new NetworkUtility());
        adapter = new BookAdapter(getApplicationContext());

        RecyclerView recyclerView = findViewById(R.id.list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stop(null);
    }

    public void start(View view) {
        if (hasBook()) {
            bookFetcher.getBook().readNow(this);
            adapter.notifyDataSetChanged();
            startedBook();
        }
    }

    public void stop(View view) {
        if (hasBook()) {
            bookFetcher.getBook().stopReading();
            adapter.notifyDataSetChanged();
            finishedBook();
        }
    }

    public void next(View view) {
        if (hasBook()) {
            bookFetcher.getBook().readNextChapter(this);
            adapter.notifyDataSetChanged();
        }
    }

    public void refresh(View view) {
        if (hasBook()) {
            adapter.setBook(bookFetcher.getBook());
        }
    }

    private boolean hasBook() {
        return bookFetcher.getBook() != null;
    }

    @Override
    public void startedBook() {

    }

    @Override
    public void startedChapter(int i) {
        ((TextView) findViewById(R.id.currently_playing)).setText(bookFetcher.getBook().getChapter(i).getTitle());
    }

    @Override
    public void finishedChapter(int i) {
        ((TextView) findViewById(R.id.currently_playing)).setText(null);
        next(null);
    }

    @Override
    public void finishedBook() {
        ((TextView) findViewById(R.id.currently_playing)).setText(null);
    }
}

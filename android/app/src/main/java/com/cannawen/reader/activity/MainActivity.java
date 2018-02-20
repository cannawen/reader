package com.cannawen.reader.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cannawen.reader.R;
import com.cannawen.reader.adapter.BookAdapter;
import com.cannawen.reader.model.book.Book;
import com.cannawen.reader.model.book.BookChangeListener;
import com.cannawen.reader.model.chapter.Chapter;
import com.cannawen.reader.utility.RSSBookFetcher;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BookChangeListener {

    private Book book = new Book(new ArrayList<Chapter>());
    private BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new RSSBookFetcher(getApplicationContext(), new RSSBookFetcher.SuccessListener() {
            @Override
            public void fetchBookSuccess(Book book) {
                MainActivity.this.book = book;
                book.setListener(MainActivity.this);
                adapter.setBook(book);
            }

            @Override
            public void fetchBookFailed() {

            }
        });
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
        book.readNow();
        adapter.notifyDataSetChanged();
    }

    public void stop(View view) {
        book.stopReading();
        adapter.notifyDataSetChanged();
    }

    public void next(View view) {
        book.readNextChapter();
        adapter.notifyDataSetChanged();
    }

    public void refresh(View view) {
    }

    @Override
    public void startedBook() {

    }

    @Override
    public void startedChapter(int i) {
        ((TextView) findViewById(R.id.currently_playing)).setText(book.getChapterTitle(i));
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

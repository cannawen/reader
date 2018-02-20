package com.cannawen.reader.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cannawen.reader.R;
import com.cannawen.reader.adapter.BookAdapter;
import com.cannawen.reader.utility.NetworkUtility;
import com.cannawen.reader.utility.RSSBookFetcher;

public class MainActivity extends AppCompatActivity {

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
        bookFetcher.getBook().readNow();
        adapter.notifyDataSetChanged();
    }

    public void stop(View view) {
        bookFetcher.getBook().stopReading();
        ((TextView) findViewById(R.id.currently_playing)).setText(null);
        adapter.notifyDataSetChanged();
    }

    public void next(View view) {
        bookFetcher.getBook().readNextChapter();
        adapter.notifyDataSetChanged();
    }

    public void refresh(View view) {
        adapter.setBook(bookFetcher.getBook());
    }
}

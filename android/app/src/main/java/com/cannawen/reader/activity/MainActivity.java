package com.cannawen.reader.activity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cannawen.reader.R;
import com.cannawen.reader.adapter.BookAdapter;
import com.cannawen.reader.model.Book;
import com.cannawen.reader.utility.BookFetcher;

import org.w3c.dom.Text;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextToSpeech tts;
    private Book book;
    private BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tts = new TextToSpeech(this, null);
        book = new BookFetcher(getApplicationContext(), tts).getBook();
        adapter = new BookAdapter(getApplicationContext(), book);

        RecyclerView recyclerView = findViewById(R.id.list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        tts.setLanguage(Locale.US);
        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(final String utteranceId) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((TextView)findViewById(R.id.currently_playing)).setText(utteranceId);
                    }
                });
            }

            @Override
            public void onDone(final String utteranceId) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        book.readNextChapter();
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onError(String utteranceId) {
                book.stopReading();
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tts.stop();
        tts.shutdown();
    }

    public void start(View view) {
        book.readNow();
        adapter.notifyDataSetChanged();
    }

    public void stop(View view) {
        book.stopReading();
        ((TextView)findViewById(R.id.currently_playing)).setText(null);
        adapter.notifyDataSetChanged();
    }
}

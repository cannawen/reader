package com.cannawen.reader.activity;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cannawen.reader.R;
import com.cannawen.reader.model.Book;
import com.cannawen.reader.model.Chapter;
import com.cannawen.reader.utility.BookFetcher;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextToSpeech tts;
    private BookFetcher bookFetcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bookFetcher = new BookFetcher();

        tts = new TextToSpeech(this, null);
        tts.setLanguage(Locale.US);
    }

    public void speak(View view) {
        List<Chapter> chapters = bookFetcher.getBook().getChapters();
        for (Chapter chapter : chapters) {
            tts.speak(chapter.getText(), TextToSpeech.QUEUE_ADD, null);
        }
    }
}

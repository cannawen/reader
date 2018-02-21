package com.cannawen.reader.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.cannawen.reader.R;
import com.cannawen.reader.adapter.BookAdapter;
import com.cannawen.reader.adapter.BookAdapterListener;
import com.cannawen.reader.media.PlaybackInfoListener;
import com.cannawen.reader.model.Book;
import com.cannawen.reader.model.Chapter;
import com.cannawen.reader.utility.BookFetcher.BookFetcher;
import com.cannawen.reader.utility.BookFetcher.LocalDotaBookFetcher;
import com.cannawen.reader.utility.BookFetcher.RSSBookFetcher;
import com.cannawen.reader.utility.FetchBookSuccessListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Book book = new Book(new ArrayList<Chapter>());
    private BookAdapter adapter;

    private SeekBar seekbarAudio;

    private boolean mUserIsSeeking = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BookFetcher fetcher = new RSSBookFetcher(getApplicationContext());
//        BookFetcher fetcher = new LocalDotaBookFetcher(getApplicationContext());
        fetcher.fetchBook(new FetchBookSuccessHandler());

        adapter = new BookAdapter(getApplicationContext(), new BookAdapterHandler());

        RecyclerView recyclerView = findViewById(R.id.list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        seekbarAudio = findViewById(R.id.seekbar_audio);
        initializeSeekbar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        book.release();
    }

    public void start(View view) {
        book.play();
    }

    public void stop(View view) {
        book.stop();
    }

    public void pause(View view) {
        book.pause();
    }

    public void next(View view) {
        book.next();
    }

    public void refresh(View view) {
    }


    private void initializeSeekbar() {
        seekbarAudio.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int userSelectedPosition = 0;

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        mUserIsSeeking = true;
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            userSelectedPosition = progress;
                        }
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        mUserIsSeeking = false;
                        book.seekTo(userSelectedPosition);
                    }
                });
    }

    public class BookAdapterHandler implements BookAdapterListener {
        @Override
        public void playChapter(int index) {
            book.play(index);
        }

        @Override
        public int currentChapter() {
            return book.getCurrentChapter();
        }
    }

    public class FetchBookSuccessHandler implements FetchBookSuccessListener {
        @Override
        public void fetchBookSuccess(Book book) {
            MainActivity.this.book = book;
            book.setListener(new PlaybackInfoHandler());
            adapter.setBook(book);
        }

        @Override
        public void fetchBookFailed() {

        }
    }

    public class PlaybackInfoHandler implements PlaybackInfoListener {
        @Override
        public void onDurationChanged(int duration) {
            seekbarAudio.setMax(duration);
        }

        @Override
        public void onPositionChanged(int position) {
            if (!mUserIsSeeking) {
                seekbarAudio.setProgress(position, true);
            }
        }

        @Override
        public void onStateChanged(@State int state) {
            String text = null;
            if (state == State.PLAYING || state == State.PAUSED) {
                text = book.getChapterTitle(book.getCurrentChapter());
            }
            ((TextView) findViewById(R.id.currently_playing)).setText(text);

            adapter.notifyDataSetChanged();
        }

        @Override
        public void onPlaybackCompleted() {
            book.next();
            adapter.notifyDataSetChanged();
        }
    }
}

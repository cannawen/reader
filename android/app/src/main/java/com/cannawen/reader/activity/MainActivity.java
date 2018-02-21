package com.cannawen.reader.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SeekBar;

import com.cannawen.reader.R;
import com.cannawen.reader.adapter.BookAdapter;
import com.cannawen.reader.adapter.BookAdapterListener;
import com.cannawen.reader.media.PlaybackInfoListener;
import com.cannawen.reader.model.Book;
import com.cannawen.reader.model.Chapter;
import com.cannawen.reader.utility.RSSBookFetcher;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BookAdapterListener {

    private Book book = new Book(new ArrayList<Chapter>());
    private BookAdapter adapter;

    private SeekBar seekbarAudio;

    private boolean mUserIsSeeking = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new RSSBookFetcher(getApplicationContext(), new RSSBookFetcher.SuccessListener() {
            @Override
            public void fetchBookSuccess(Book book) {
                MainActivity.this.book = book;
                book.setListener(new PlaybackListener());
                adapter.setBook(book);
            }

            @Override
            public void fetchBookFailed() {

            }
        });
//        TextToSpeech tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
//            @Override
//            public void onInit(int status) {
//
//            }
//        });
//        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
//            @Override
//            public void onStart(String utteranceId) {
//
//            }
//
//            @Override
//            public void onDone(String utteranceId) {
//
//            }
//
//            @Override
//            public void onError(String utteranceId) {
//                Log.d("err", utteranceId);
//            }
//        });
//        book = new TTSBookFetcher(getApplicationContext(), tts).getBook();

        adapter = new BookAdapter(getApplicationContext(), this);
//        adapter.setBook(book);

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

        adapter.notifyDataSetChanged();
    }

    public void stop(View view) {
        book.stop();

        adapter.notifyDataSetChanged();
    }

    public void pause(View view) {
        book.pause();

        adapter.notifyDataSetChanged();
    }

    public void next(View view) {
        book.next();

        adapter.notifyDataSetChanged();
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

    @Override
    public void playChapter(int index) {
        book.play(index);
    }

    @Override
    public int currentChapter() {
        return book.getCurrentChapter();
    }

    public class PlaybackListener extends PlaybackInfoListener {
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
            String stateToString = PlaybackInfoListener.convertStateToString(state);
        }

        @Override
        public void onPlaybackCompleted() {
        }
    }
}

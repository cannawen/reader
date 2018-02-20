package com.cannawen.reader.utility;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.widget.TextView;

import com.cannawen.reader.R;

import java.util.Locale;

public class TTSUtility {
    private TextToSpeech tts;

    public TTSUtility(Context context ) {
        tts = new TextToSpeech(context, null);

        tts.setLanguage(Locale.US);
        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(final String utteranceId) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        ((TextView)findViewById(R.id.currently_playing)).setText(utteranceId);
//                    }
//                });
            }

            @Override
            public void onDone(final String utteranceId) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        next(null);
//                    }
//                });
            }

            @Override
            public void onError(String utteranceId) {
//                book.stopReading();
//                adapter.notifyDataSetChanged();
            }
        });
    }
}

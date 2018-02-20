package com.cannawen.reader.model.chapter;

import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;

public class TTSChapter extends Chapter {
    private String text;
    private TextToSpeech tts;
    private ChapterChangeListener listener;

    public TTSChapter(int index, String title, String text, TextToSpeech tts) {
        this.tts = tts;
        this.index = index;
        this.title = title;
        this.text = text;
    }

    @Override
    public void readNow() {
        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {
                listener.startedChapter(index);
            }

            @Override
            public void onDone(String utteranceId) {
                listener.finishedChapter(index);
            }

            @Override
            public void onError(String utteranceId) {
                listener.finishedChapter(index);
            }
        });
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, title);
    }

    @Override
    public void stopReading() {
        tts.stop();
    }
}

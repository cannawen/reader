package com.cannawen.reader.model.chapter;

import android.speech.tts.TextToSpeech;

public class TTSChapter extends Chapter {
    private String text;
    private TextToSpeech tts;

    public TTSChapter(int index, String title, String text, TextToSpeech tts) {
        this.tts = tts;
        this.index = index;
        this.title = title;
        this.text = text;
    }

    @Override
    public void readNow() {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, title);
    }

    @Override
    public void stopReading() {
        tts.stop();
    }
}

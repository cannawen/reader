package com.cannawen.reader.model;

import android.speech.tts.TextToSpeech;

public class Chapter {
    private int index;
    private String title;
    private String text;

    public Chapter(int index, String title, String text) {
        this.index = index;
        this.title = title;
        this.text = text;
    }

    public void readNow(TextToSpeech tts) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, title);
    }

    public String getTitle() {
        return title;
    }
}

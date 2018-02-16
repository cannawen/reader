package com.cannawen.reader.model;

import android.os.Handler;
import android.speech.tts.TextToSpeech;

import java.util.List;

public class Book {
    private TextToSpeech tts;
    private List<Chapter> chapters;
    private int currentChapter = -1;

    public Book(TextToSpeech tts, List<Chapter> chapters) {
        this.tts = tts;
        this.chapters = chapters;
    }

    public void readNow() {
        currentChapter = 0;
        chapters.get(currentChapter).readNow(tts);
    }

    public void readNextChapter() {
        if (currentChapter != chapters.size() - 1) {
            currentChapter++;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    chapters.get(currentChapter).readNow(tts);
                }
            }, 300);
        } else {
            currentChapter = -1;
        }
    }

    public int numberOfChapters() {
        return chapters.size();
    }

    public Chapter getChapter(int i) {
        return chapters.get(i);
    }

    public int getCurrentChapter() {
        return currentChapter;
    }

    public void playChapter(Chapter chapter) {
        currentChapter = chapters.indexOf(chapter);
        chapter.readNow(tts);
    }

    public void stopReading() {
        currentChapter = -1;
        tts.stop();
    }
}

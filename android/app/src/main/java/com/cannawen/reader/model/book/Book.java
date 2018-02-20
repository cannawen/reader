package com.cannawen.reader.model.book;

import android.os.Handler;

import com.cannawen.reader.model.chapter.Chapter;

import java.util.List;

public class Book {
    protected List<? extends Chapter> chapters;
    private int currentChapter = -1;

    public Book(List<? extends Chapter> chapters) {
        this.chapters = chapters;
    }

    public void readNow() {
        currentChapter = 0;
        chapters.get(currentChapter).readNow();
    }

    public void readNextChapter() {
        if (currentChapter != chapters.size() - 1) {
            currentChapter++;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    chapters.get(currentChapter).readNow();
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
        chapter.readNow();
    }

    public void stopReading() {
        getChapter(currentChapter).stopReading();
        currentChapter = -1;
    }
}

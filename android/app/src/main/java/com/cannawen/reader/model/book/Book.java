package com.cannawen.reader.model.book;

import android.os.Handler;

import com.cannawen.reader.model.chapter.Chapter;
import com.cannawen.reader.model.chapter.ChapterChangeListener;

import java.util.List;

public class Book implements ChapterChangeListener {
    BookChangeListener listener;

    protected List<? extends Chapter> chapters;
    private int currentChapter = -1;

    public Book(List<? extends Chapter> chapters) {
        this.chapters = chapters;
    }

    public void readNow(BookChangeListener listener) {
        this.listener = listener;
        currentChapter = 0;
        chapters.get(currentChapter).readNow(this);
    }

    public void readNextChapter(BookChangeListener listener) {
        this.listener = listener;

        if (currentChapter != chapters.size() - 1) {
            currentChapter++;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    chapters.get(currentChapter).readNow(Book.this);
                }
            }, 300);
        } else {
            currentChapter = -1;
            listener.finishedBook();
        }
    }

    public int numberOfChapters() {
        return chapters.size();
    }

    public Chapter getChapter(int i) {
        return chapters.get(i);
    }

    public int getCurrentChapterIndex() {
        return currentChapter;
    }

    public void playChapter(Chapter chapter) {
        currentChapter = chapters.indexOf(chapter);
        chapter.readNow(this);
    }

    public void stopReading() {
        getChapter(currentChapter).stopReading();
        currentChapter = -1;
    }

    @Override
    public void startedChapter(int i) {
        if (i == 0) {
            listener.startedBook();
        }
        listener.startedChapter(i);
    }

    @Override
    public void finishedChapter(int i) {
        listener.finishedChapter(i);
        if (i == chapters.size() - 1) {
            listener.finishedBook();
        }
    }
}

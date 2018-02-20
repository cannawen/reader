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
        for (Chapter chapter : chapters) {
            chapter.setListener(this);
        }
    }

    public void setListener(BookChangeListener listener) {
        this.listener = listener;
    }

    public void readNow() {
        stopReading();

        currentChapter = 0;
        chapters.get(currentChapter).readNow();
    }

    public void readNextChapter() {
        stopReading();

        if (currentChapter != chapters.size() - 1) {
            currentChapter++;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    playChapter(currentChapter);
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

    public String getChapterTitle(int i) {
        return chapters.get(i).getTitle();
    }

    public int getCurrentChapterIndex() {
        return currentChapter;
    }

    public void playChapter(int chapter) {
        stopReading();

        currentChapter = chapter;
        chapters.get(chapter).readNow();
    }

    public void stopReading() {
        if (currentChapter != -1) {
            chapters.get(currentChapter).stopReading();
        }
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

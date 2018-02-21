package com.cannawen.reader.model;

import com.cannawen.reader.media.PlaybackInfoListener;

import java.util.List;

import lombok.Getter;

public class Book {
    @Getter
    private int currentChapter = -1;

    protected List<Chapter> chapters;
    private PlaybackInfoListener listener;

    public Book(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public int numberOfChapters() {
        return chapters.size();
    }

    public String getChapterTitle(int i) {
        return chapters.get(i).getTitle();
    }

    public void setListener(PlaybackInfoListener listener) {
        this.listener = listener;
    }

    public void release() {
        for (Chapter chapter : chapters) {
            chapter.release();
        }
    }

    private void setCurrentChapterListener(PlaybackInfoListener listener) {
        if (currentChapter >= 0 &&  currentChapter < chapters.size()) {
            chapters.get(currentChapter).setPlaybackInfoListener(listener);
        }
    }

    private Chapter currentChapter() {
        if (currentChapter >= 0 && currentChapter < chapters.size()) {
            return chapters.get(currentChapter);
        } else {
            return null;
        }
    }

    public void play() {
        if (currentChapter == -1) {
            currentChapter = 0;
            setCurrentChapterListener(listener);
        }
        currentChapter().play();
    }

    public void play(int i) {
        if (i != currentChapter) {
            stop();
        }
        if (i >= 0 && i < chapters.size()) {
            currentChapter = i;
            setCurrentChapterListener(listener);
            currentChapter().play();
        } else {
            currentChapter = -1;
        }
    }

    public void stop() {
        if (currentChapter() != null) {
            currentChapter().reset();
        }
        setCurrentChapterListener(null);
        currentChapter = -1;
    }

    public void pause() {
        currentChapter().pause();
    }

    public void next() {
        if (currentChapter() != null) {
            currentChapter().reset();
            setCurrentChapterListener(null);
        }
        currentChapter = currentChapter + 1;
        play(currentChapter);
    }

    public void seekTo(int userSelectedPosition) {
        currentChapter().seekTo(userSelectedPosition);
    }
}

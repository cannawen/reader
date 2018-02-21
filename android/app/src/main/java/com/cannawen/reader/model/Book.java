package com.cannawen.reader.model;

import java.util.List;

public class Book {

    protected List<Chapter> chapters;

    public Book(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public int numberOfChapters() {
        return chapters.size();
    }

    public String getChapterTitle(int i) {
        return chapters.get(i).getTitle();
    }

    public Chapter getChapter(int i) {
        if (i >= 0 && i < chapters.size()) {
            return chapters.get(i);
        } else {
            return null;
        }
    }

    public void release() {
        for (Chapter chapter : chapters) {
            chapter.release();
        }
    }
}

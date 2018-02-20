package com.cannawen.reader.model.book;

public interface BookChangeListener {
    void startedBook();
    void startedChapter(int i);
    void finishedChapter(int i);
    void finishedBook();
}

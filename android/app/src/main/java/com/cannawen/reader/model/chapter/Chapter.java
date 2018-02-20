package com.cannawen.reader.model.chapter;

public abstract class Chapter {
    protected int index;
    protected String title;

    public String getTitle() {
        return title;
    }

    public int getIndex() {
        return index;
    }

    public abstract void readNow(ChapterChangeListener listener);

    public abstract void stopReading();

}

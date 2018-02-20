package com.cannawen.reader.model.chapter;

public abstract class Chapter {
    protected int index;
    protected String title;
    protected ChapterChangeListener listener;

    public String getTitle() {
        return title;
    }

    public int getIndex() {
        return index;
    }

    public void setListener(ChapterChangeListener listener) {
        this.listener = listener;
    }

    public abstract void readNow();

    public abstract void stopReading();

}

package com.cannawen.reader.model.chapter;

public abstract class Chapter {
    protected int index;
    protected String title;

    public String getTitle() {
        return title;
    }

    public abstract void readNow();

    public abstract void stopReading();
}

package com.cannawen.reader.model.chapter;

public abstract class Chapter {
    protected int index;

    public String getTitle() {
        return title;
    }

    protected String title;

    public abstract void readNow();

    public abstract void stopReading();
}

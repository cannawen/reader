package com.cannawen.reader.model.chapter;

public class AudioChapter extends Chapter {

    public AudioChapter(int index, String title, String url) {
        this.index = index;
        this.title = title;
    }

    @Override
    public void readNow(ChapterChangeListener listener) {
        listener.startedChapter(index);
    }

    @Override
    public void stopReading() {

    }
}

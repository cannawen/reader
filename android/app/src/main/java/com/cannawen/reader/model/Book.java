package com.cannawen.reader.model;

import java.util.List;

public class Book {
    private List<Chapter> chapters;

    public Book(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }
}

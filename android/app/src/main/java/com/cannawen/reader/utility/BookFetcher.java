package com.cannawen.reader.utility;

import com.cannawen.reader.model.Book;
import com.cannawen.reader.model.Chapter;

import java.util.Arrays;

public class BookFetcher {
    public Book getBook() {
        return new Book(Arrays.asList(
                new Chapter("Hello"),
                new Chapter("World!")));
    }
}

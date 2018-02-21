package com.cannawen.reader.utility;

import com.cannawen.reader.model.Book;

public interface FetchBookSuccessListener {
    void fetchBookSuccess(Book book);

    void fetchBookFailed();
}
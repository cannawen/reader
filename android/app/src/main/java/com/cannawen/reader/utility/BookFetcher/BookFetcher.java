package com.cannawen.reader.utility.BookFetcher;

import com.cannawen.reader.utility.FetchBookSuccessListener;

public interface BookFetcher {
    public void fetchBook(final FetchBookSuccessListener listener);
}

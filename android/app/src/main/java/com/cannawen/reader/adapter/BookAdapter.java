package com.cannawen.reader.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cannawen.reader.R;
import com.cannawen.reader.model.book.Book;
import com.cannawen.reader.model.book.BookChangeListener;
import com.cannawen.reader.model.chapter.Chapter;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private Context context;
    private Book book;

    public BookAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_chapter, parent, false);
        return new BookAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        boolean currentlyPlaying = position == book.getCurrentChapterIndex();
        holder.configure(position, currentlyPlaying);
    }

    @Override
    public int getItemCount() {
        return book == null ? 0 : book.numberOfChapters();
    }

    public void setBook(Book book) {
        this.book = book;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View background;
        private TextView chapterTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            background = itemView;
            chapterTitle = itemView.findViewById(R.id.list_item_chapter_title);
        }

        public void configure(final int chapter, boolean currentlyPlaying) {
            chapterTitle.setText(book.getChapterTitle(chapter));
            chapterTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    book.playChapter(chapter);
                    notifyDataSetChanged();
                }
            });
            if (currentlyPlaying) {
                background.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            } else {
                background.setBackground(null);
            }
        }
    }
}

package com.cannawen.reader.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cannawen.reader.R;
import com.cannawen.reader.model.Book;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private Context context;
    private BookAdapterListener listener;
    private Book book;

    public BookAdapter(Context context, BookAdapterListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_chapter, parent, false);
        return new BookAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        boolean currentlyPlaying = position == listener.currentChapter();
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
                    listener.playChapter(chapter);
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

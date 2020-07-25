package com.example.googlebookapi;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        public ImageView mThumbnail;
        public TextView mTitle;
        public TextView mAuthors;
        public TextView mPublishedDate;
        public TextView mPageCount;
        public RatingBar mAverageRating;


        public BookViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mThumbnail = itemView.findViewById(R.id.thumbnail);
            mTitle = itemView.findViewById(R.id.title);
            mAuthors = itemView.findViewById(R.id.authors);
            mPublishedDate = itemView.findViewById(R.id.published_date);
            mPageCount = itemView.findViewById(R.id.pageCount);
            mAverageRating = itemView.findViewById(R.id.average_rating);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }

    //**********************************************************************************************

    private ArrayList<Book> mBookList;

    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public BookAdapter(ArrayList<Book> bookList) {
        mBookList = bookList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_cardview,parent,false);
        BookViewHolder bvh = new BookViewHolder(v, mListener);
        return bvh;
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {

        Book currentBook = mBookList.get(position);

        //holder.mThumbnail.setImageURI(uri);
        if (currentBook.getThumbnail() != null){
            Uri uri = Uri.parse(currentBook.getThumbnail());
            Glide.with(holder.itemView.getContext()).load(uri).into(holder.mThumbnail);
        }

        holder.mTitle.setText(currentBook.getTitle());
        holder.mAuthors.setText(currentBook.getAuthors());
        holder.mPublishedDate.setText(currentBook.getPublishedDate());
        holder.mPageCount.setText(currentBook.getPageCount());
        holder.mAverageRating.setRating(Float.parseFloat(currentBook.getAverageRating()));

    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }

    public Book getPosition(int position){
        return mBookList.get(position);
    }

    public void addAll(@NonNull List<Book> collection) {
        mBookList.addAll(collection);
    }

    public void clear() {
        mBookList.clear();
    }

}
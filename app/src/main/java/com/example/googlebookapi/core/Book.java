package com.example.googlebookapi.core;

public class Book {

    String mTitle;
    String mAuthors;
    String mPageCount;
    String mPublishedDate;
    String mAverageRating;
    String mThumbnail;
    String mInfoLink;

    public Book(String title, String authors, String pageCount, String publishedDate, String averageRating, String thumbnail, String infoLink) {
        mTitle = title;
        mAuthors = authors;
        mPageCount = pageCount;
        mPublishedDate = publishedDate;
        mAverageRating = averageRating;
        mThumbnail = thumbnail;
        mInfoLink = infoLink;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getAuthors() {
        return mAuthors;
    }

    public void setAuthors(String mAuthors) {
        this.mAuthors = mAuthors;
    }

    public String getPageCount() {
        return mPageCount;
    }

    public void setPageCount(String mPageCount) {
        this.mPageCount = mPageCount;
    }

    public String getPublishedDate() {
        return mPublishedDate;
    }

    public void setPublishedDate(String mPublishedDate) {
        this.mPublishedDate = mPublishedDate;
    }

    public String getAverageRating() {
        return mAverageRating;
    }

    public void setAverageRating(String mAverageRating) {
        this.mAverageRating = mAverageRating;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(String mThumbnail) {
        this.mThumbnail = mThumbnail;
    }

    public String getInfoLink() {
        return mInfoLink;
    }

    public void setInfoLink(String mInfoLink) {
        this.mInfoLink = mInfoLink;
    }
}

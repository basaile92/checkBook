package com.example.basaile92.listelivre.entity;

import java.io.Serializable;

public class Book implements Serializable {

    private String isbn;
    private String title;
    private String publisher;
    private String year;
    private String summary;
    private int isRead;
    private int isBorrowed;
    private String comment;
    private String photo;

    public Book(String isbn, String title, String publisher, String year, String summary, int isRead, int isBorrowed,  String comment, String photo) {
        this.isbn = isbn;
        this.title = title;
        this.publisher = publisher;
        this.year = year;
        this.summary = summary;
        this.isRead = isRead;
        this.isBorrowed = isBorrowed;
        this.comment = comment;
        this.photo = photo;
    }


    public String getIsbn() {
        return this.isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return this.publisher;
    }
    
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    
    public String getYear() {
        return this.year;
    }
    
    public void setYear(String year) {
        this.year = year;
    }
    
    public String getSummary() {
        return this.summary;
    }
    
    public void setSummary(String summary) {
        this.summary = summary;
    }
    
    public int getIsRead() {
        return this.isRead;
    }
    
    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }
    
    public int getIsBorrowed() {
        return this.isBorrowed;
    }
    
    public void setIsBorrowed(int isBorrowed) {
        this.isBorrowed = isBorrowed;
    }

    public String getComment() {
        return this.comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public String getPhoto() {
        return this.photo;
    }
    
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    
}

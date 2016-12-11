package com.example.basaile92.listelivre.entity;

import java.io.Serializable;

/**
 * A book is identified by a isbn number.
 * It contains a title, a publisher, the year of publication, a summary, a comment and a photo.
 * We can know if a book is already read, or borrowed from someone
 */
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

    /**
     * Constructor of the class Book
     * @param isbn : identification number
     * @param title : book's title
     * @param publisher : book's publisher
     * @param year : year of publication
     * @param summary : book's summary
     * @param isRead : if the book is already read
     * @param isBorrowed : if the book is borrowed
     * @param comment : user's comment of the book
     * @param photo : book's photo
     */
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

    /**
     * @return the book's isbn
     */
    public String getIsbn() {
        return this.isbn;
    }


    /**
     * Set the book's isbn
     * @param isbn : new isbn
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }


    /**
     * @return the book's title
     */
    public String getTitle() {
        return this.title;
    }


    /**
     * Set the book's title
     * @param title : new title of the book
     */
    public void setTitle(String title) {
        this.title = title;
    }


    /**
     * @return the book's publisher
     */
    public String getPublisher() {
        return this.publisher;
    }


    /**
     * Set the book's publisher
     * @param publisher : new publisher of the book
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }


    /**
     * @return the year of publication
     */
    public String getYear() {
        return this.year;
    }


    /**
     * Set the year of publication of the book
     * @param year : new year of publication
     */
    public void setYear(String year) {
        this.year = year;
    }


    /**
     * @return the book's summary
     */
    public String getSummary() {
        return this.summary;
    }


    /**
     * Set the book's summary
     * @param summary : new book's summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }


    /**
     * @return 1 if the book is read, 0 if not
     */
    public int getIsRead() {
        return this.isRead;
    }


    /**
     * Set the attribute isRead
     * @param isRead : 1 if the book is read, 0 if not
     */
    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }


    /**
     * @return 1 if the book is borrowed, 0 if not
     */
    public int getIsBorrowed() {
        return this.isBorrowed;
    }


    /**
     * Set the attribute isBorrowed
     * @param isBorrowed : 1 if the book is borrowed, 0 if not
     */
    public void setIsBorrowed(int isBorrowed) {
        this.isBorrowed = isBorrowed;
    }


    /**
     * @return the book's comment
     */
    public String getComment() {
        return this.comment;
    }


    /**
     * Set the book's comment
     * @param comment : new comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }


    /**
     * @return the book's photo
     */
    public String getPhoto() {
        return this.photo;
    }


    /**
     * Set the book's photo
     * @param photo : a new photo
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    
}

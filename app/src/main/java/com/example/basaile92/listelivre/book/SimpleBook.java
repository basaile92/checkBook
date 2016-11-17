package com.example.basaile92.listelivre.book;

import java.util.ArrayList;

/**
 * Created by Mercier on 27/10/2016.
 */

public class SimpleBook{

    private String isbn;
    private ArrayList<String> authors;
    private String title;
    private String collection;
    private ArrayList<String> types;
    private String publisher;
    private String year;
    private String summary;
    private boolean isRead;
    private boolean isBorrowed;
    private String borrower;
    private String owner;
    private String comment;
    private String photo;


    /**
     * Constructor of a simple book
     * @param authors

     * @param title
     * @param isbn
     * @param summary
     * @param isRead
     * @param isBorrowed
     * @param borrower
     * @param owner
     * @param comment
     * @param photo
     */
    public SimpleBook(String isbn, ArrayList<String> authors, String title, String collection, ArrayList<String> types, String publisher, String year, String summary, boolean isRead, boolean isBorrowed, String borrower, String owner, String comment, String photo){
        this.authors = authors;
        this.title = title;
        this.isbn = isbn;
        this.collection = collection;
        this.types = types;
        this.publisher = publisher;
        this.year = year;
        this.summary = summary;
        this.isRead = isRead;
        this.isBorrowed = isBorrowed;
        this.borrower = borrower;
        this.owner = owner;
        this.comment = comment;
        this.photo = photo;
    }

    /**
     * getter isbn
     * @return a String which describes the isbn of the Book
     */
    public String getIsbn() {
        return this.isbn;
    }

    /**
     * setter isbn
     * @param isbn is the isbn title that you want to set.
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * getter summary
     * @return a String which gives more information about the Book
     */
    public String getSummary(){
        return this.summary;
    }

    /**
     * setter summary
     * @param summary
     */
    public void setSummary(String summary){
        this.summary = summary;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<String> types) {
        this.types = types;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

}

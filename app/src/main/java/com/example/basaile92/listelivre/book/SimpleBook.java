package com.example.basaile92.listelivre.book;

/**
 * Created by Mercier on 27/10/2016.
 */

public class SimpleBook extends Book {

    protected String isbn;
    protected String description;
    protected boolean isRead;
    protected boolean isBorrowed;
    protected String borrower;
    protected String owner;
    protected String comments;
    protected String photo;

    /**
     * Constructor of a simple book
     * @param author
     * @param title
     * @param isbn
     * @param description
     * @param isRead
     * @param isBorrowed
     * @param borrower
     * @param owner
     * @param comments
     * @param photo
     */
    public SimpleBook(String isbn, String author, String title, String description, boolean isRead, boolean isBorrowed, String borrower, String owner, String comments, String photo){
        this.author = author;
        this.title = title;
        this.isbn = isbn;
        this.description = description;
        this.isRead = isRead;
        this.isBorrowed = isBorrowed;
        this.borrower = borrower;
        this.owner = owner;
        this.comments = comments;
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
     * getter description
     * @return a String which gives more information about the Book
     */
    public String getDescription(){
        return this.description;
    }

    /**
     * setter description
     * @param description
     */
    public void setDescription(String description){
        this.description = description;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}

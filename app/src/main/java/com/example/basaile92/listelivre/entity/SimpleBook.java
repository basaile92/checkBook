package com.example.basaile92.listelivre.entity;

import com.example.basaile92.listelivre.manager.TypeManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * SimpleBook contains a Book, a list with all authors, and a list with all types
 */
public class SimpleBook implements Serializable{

    Book book;
    AuthorList authors;
    TypeList types;

    /**
     * Constructor of a simpleBook
     * @param isbn : Book's isbn
     * @param authors : an AuthorList which contains all authors
     * @param title : Book's title
     * @param types : an TypeList which contains all types
     * @param publisher : Book's publisher
     * @param year : Book's year of publication
     * @param summary : Book's summary
     * @param isRead : if the Book is read or not
     * @param isBorrowed : if the Book is borrowed or not
     * @param comment : Book's comment
     * @param photo : Book's photo
     */
    public SimpleBook(String isbn, AuthorList authors, String  title, TypeList types, String publisher, String year, String summary, boolean isRead, boolean isBorrowed, String comment, String photo){
        this.book = new Book(isbn,  title, publisher, year, summary, (isRead? 1 : 0), (isBorrowed? 1 : 0), comment, photo);
        this.authors = authors;
        this.types = types;
    }

    /**
     * Constructor of a simpleBook with a Book already created
     * @param book : pre-filled Book
     * @param authors : AuthorList which contains all Authors
     * @param typebooks : TypeList which contains all Types
     */
    public SimpleBook(Book book, AuthorList authors, List<Typebook> typebooks) {

        this.book = book;
        this.authors = authors;
        this.types = TypeManager.fromTypebookListToTypeList(typebooks);
    }

    /**
     * @return the SimpleBook's Book
     */
    public Book getBook(){
        return this.book;
    }

    /**
     * Set the SimpleBook's Book
     * @param book : a new Book
     */
    public void setBook(Book book){

        this.book = book;
    }

    /**
     * @return an AuthorList which contains all Authors
     */
    public AuthorList getAuthors(){

        return this.authors;
    }


    /**
     * Set the AuthorList
     * @param authors : a new AuthorList
     */
    public void setAuthors(AuthorList authors){

        this.authors = authors;
    }

    /**
     * @return an TypeList which contains all types
     */
    public TypeList getTypes(){

        return this.types;
    }


    /**
     * Set the TypeList
     * @param types : a new TypeList
     */
    public void setTypes(TypeList types){

        this.types = types;
    }

    /**
     * @return the SimpleBook's isbn
     */
    public String getIsbn() {
        return this.book.getIsbn();
    }

    /**
     * Set the SimpleBook's isbn
     * @param isbn : a new isbn
     */
    public void setIsbn(String isbn) {
        this.book.setIsbn(isbn);
    }

    /**
     * @return the SimpleBook's title
     */
    public String getTitle() {
        return this.book.getTitle();
    }

    /**
     * Set the SimpleBook's title
     * @param title : a new title
     */
    public void setTitle(String title) {
        this.book.setTitle(title);
    }

    /**
     * @return the SimpleBook's publisher
     */
    public String getPublisher() {
        return this.book.getPublisher();
    }


    /**
     * Set the SimpleBook's publisher
     * @param publisher : a new publisher
     */
    public void setPublisher(String publisher) {
        this.book.setPublisher(publisher);
    }

    /**
     * @return the SimpleBook's year of publication
     */
    public String getYear() {
        return this.book.getYear();
    }

    /**
     * Set the SimpleBook's year of publication
     * @param year : a new year of publication
     */
    public void setYear(String year) {
        this.book.setYear(year);
    }

    /**
     * @return the SimpleBook's summary
     */
    public String getSummary() {
        return this.book.getSummary();
    }

    /**
     * Set the SimpleBook's summary
     * @param summary : a new summary
     */
    public void setSummary(String summary) {
        this.book.setSummary(summary);
    }

    /**
     * @return 1 if the SimpleBook is read, 0 if not
     */
    public boolean isRead() {
        return (this.book.getIsRead())!=0 ;
    }

    /**
     * Set if the SimpleBook is read or not
     * @param isRead : 1 if the SimpleBook is read, 0 if not
     */
    public void setIsRead(boolean isRead) {
        int res =  isRead? 1 :  0;
        this.book.setIsRead(res);
    }

    /**
     * @return 1 if the SimpleBook is borrowed, 0 if not
     */
    public boolean isBorrowed() {
        return this.book.getIsBorrowed()!=0;
    }

    /**
     * Set if the SimpleBook is Borrowed
     * @param isBorrowed 1 if the SimpleBook is borrowed, 0 if not
     */
    public void setIsBorrowed(boolean isBorrowed) {
        int res = isBorrowed? 1 : 0;
        this.book.setIsBorrowed(res);
    }


    /**
     * @return the SimpleBook's comment
     */
    public String getComment() {
        return this.book.getComment();
    }

    /**
     * Set the SimpleBook's comment
     * @param comment : a new comment
     */
    public void setComment(String comment) {
        this.book.setComment(comment);
    }

    /**
     * @return the SimpleBook's photo
     */
    public String getPhoto() {
        return this.book.getPhoto();
    }

    /**
     * Set the SimpleBook's photo
     * @param photo a new photo
     */
    public void setPhoto(String photo) {
        this.book.setPhoto(photo);
    }

    /**
     * @return a string which describe the SimpleBook
     */
    public String toString(){

        if(!getPublisher().equals(""))
            return(getTitle() + " - " + getAuthors().toString() + " - " + getPublisher());
        return (getTitle() + " - " + getAuthors().toString());
    }

}

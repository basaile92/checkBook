package com.example.basaile92.listelivre.entity;

import com.example.basaile92.listelivre.manager.TypeManager;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Basile on 19/11/2016.
 */

public class SimpleBook implements Serializable{

    Book book;
    AuthorList authors;
    TypeList types;

    public SimpleBook(String isbn, AuthorList authors, String  title, String collection, TypeList types, String publisher, String year, String summary, boolean isRead, boolean isBorrowed, String borrower, String owner, String comment, String photo){
        this.book = new Book(isbn,  title, collection, publisher, year, summary, (isRead? 1 : 0), (isBorrowed? 1 : 0), borrower, owner, comment, photo);
        this.authors = authors;
        this.types = types;
    }

    public SimpleBook(Book book, AuthorList authors, List<Typebook> typebooks) {

        this.book = book;
        this.authors = authors;
        this.types = TypeManager.fromTypebookListToTypeList(typebooks);
    }

    public Book getBook(){
        return this.book;
    }

    public void setBook(Book book){

        this.book = book;
    }

    public AuthorList getAuthors(){

        return this.authors;
    }

    public void setAuthors(AuthorList authors){

        this.authors = authors;
    }

    public TypeList getTypes(){

        return this.types;
    }

    public void setTypes(TypeList types){

        this.types = types;
    }

    public String getIsbn() {
        return this.book.getIsbn();
    }

    public void setIsbn(String isbn) {
        this.book.setIsbn(isbn);
    }

    public String getTitle() {
        return this.book.getTitle();
    }

    public void setTitle(String title) {
        this.book.setTitle(title);
    }

    public String getCollection() {
        return this.book.getCollection();
    }

    public void setCollection(String collection) {
        this.book.setCollection(collection);
    }

    public String getPublisher() {
        return this.book.getPublisher();
    }

    public void setPublisher(String publisher) {
        this.book.setPublisher(publisher);
    }

    public String getYear() {
        return this.book.getYear();
    }

    public void setYear(String year) {
        this.book.setYear(year);
    }

    public String getSummary() {
        return this.book.getSummary();
    }

    public void setSummary(String summary) {
        this.book.setSummary(summary);
    }

    public boolean isRead() {
        return (this.book.getIsRead())!=0 ;
    }

    public void setIsRead(boolean isRead) {
        int res =  isRead? 1 :  0;
        this.book.setIsRead(res);
    }

    public boolean isBorrowed() {
        return this.book.getIsBorrowed()!=0;
    }

    public void setIsBorrowed(boolean isBorrowed) {
        int res = isBorrowed? 1 : 0;
        this.book.setIsBorrowed(res);
    }

    public String getBorrower() {
        return this.book.getBorrower();
    }

    public void setBorrower(String borrower) {
        this.book.setBorrower(borrower);
    }

    public String getOwner() {
        return this.book.getOwner();
    }

    public void setOwner(String owner) {
        this.book.setOwner(owner);
    }

    public String getComment() {
        return this.book.getComment();
    }

    public void setComment(String comment) {
        this.book.setComment(comment);
    }

    public String getPhoto() {
        return this.book.getPhoto();
    }

    public void setPhoto(String photo) {
        this.book.setPhoto(photo);
    }

    public String toString(){

        return(getTitle() + " - " + getAuthors().toString() + " - " + getPublisher());
    }

}

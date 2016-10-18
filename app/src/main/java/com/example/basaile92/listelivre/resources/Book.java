package com.example.basaile92.listelivre.resources;

import java.io.Serializable;

/**
 * Created by basaile92 on 29/09/2016.
 */

public class Book implements Serializable{

    private String author;
    private String title;
    private String isbn;

    public Book(String author, String title, String isbn){
        this.author = author;
        this.title = title;
        this.isbn = isbn;
    }

    /**
     * getter author
     * @return a String which describes the author of the Book
     */
    public String getAuthor() {
        return this.author;
    }

    /**
     * setter author
     * @param author is the String author that you want to set.
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * getter title
     * @return a String which describes the title of the Book
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * setter title
     * @param title is the String title that you want to set.
     */
    public void setTitle(String title) {
        this.title = title;
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
}

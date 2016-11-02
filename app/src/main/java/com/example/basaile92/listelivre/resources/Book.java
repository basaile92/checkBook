package com.example.basaile92.listelivre.resources;

import java.io.Serializable;

/**
 * Created by basaile92 on 29/09/2016.
 */

public abstract class Book implements Serializable{

    protected String author;
    protected String title;
    protected String isbn;
    private String description;

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

    public abstract boolean canContainBook();

    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(String title) {
        this.isbn = isbn;
    }


    public String getDescription() {
        return description;
    }

}

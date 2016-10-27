package com.example.basaile92.listelivre.resources;

/**
 * Created by Nicolas DUFLOS on 06/10/16.
 */

public class BookFilter {

    private String author;
    private String title;
    private String isbn;

    public BookFilter(String author, String title, String isbn) {
        this.author = author;
        this.title = title;
        this.isbn = isbn;
    }

    //TODO: Modifier cette fonction
/*
    public boolean isSelected (Book book){
        if (book.getAuthor().equals(this.author))
            return true;
        if (book.getTitle().equals(this.title))
            return true;
        if (book.getIsbn().equals(this.isbn))
            return true;
        return false;
    }
*/
}

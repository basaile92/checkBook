package com.example.basaile92.listelivre.entity;

/**
 * Created by Basile on 22/11/2016.
 */
public class Collection {

    private String name;
    private BookLibrary books = new BookLibrary();

    // Constructor
    public Collection(String name, BookLibrary books) {

        this.name = name;
        this.books = books;
    }


    public void setName(String name) {

        this.name = name;
    }

    public String getName() {

        return this.name;
    }

    public void setBooks(BookLibrary books) {

        this.books = books;
    }

    public BookLibrary getBooks() {

        return this.books;
    }
}

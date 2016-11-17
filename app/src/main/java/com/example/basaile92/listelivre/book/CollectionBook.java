package com.example.basaile92.listelivre.book;

import java.util.HashMap;

/**
 * Created by Mercier on 27/10/2016.
 */

public class CollectionBook {

    private String title;
    private BookLibrary books;

    /**
     * Constructor of a collection Book
     * @param title
     */
    public CollectionBook(String title) {
        this.title = title;
        this.books = new BookLibrary();
    }

    /**
     * Add a book into the collection
     * @param book
     */
    public void addBook(SimpleBook book){

        this.books.addBook(book);
    }

    /**
     * Remove a book from the collection
     * @param book
     */
    public void removeBook(SimpleBook book){

        this.books.remove(book);
    }
}

package com.example.basaile92.listelivre.entity;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * A BookLibrary contains a list of user's books
 */
public class BookLibrary extends ArrayList<SimpleBook> implements Serializable{

    public BookLibrary(){

    }

    /**
     * Add a book in the Book library.
     * @param book is the Book that you want to add in the library.
     */
    public void addBook(SimpleBook book){

        this.add(book);
    }

    /**
     * Remove a book from the Book library.
     * @param book is the Book that you want to delete in the library.
     */
    public void removeBook(SimpleBook book){

        this.remove(book);
    }
}

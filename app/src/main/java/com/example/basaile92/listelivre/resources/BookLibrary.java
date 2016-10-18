package com.example.basaile92.listelivre.resources;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by basaile92 on 29/09/2016.
 */

public class BookLibrary extends ArrayList<Book> implements Serializable{

    public BookLibrary(){

    }

    /**
     * Add a book in the Book library.
     * @param book is the Book that you want to add in the library.
     */
    public void addBook(Book book){

        this.add(book);
    }

    /**
     * Remove a book in the Book library.
     * @param book is the Book that you want to remove in the library.
     */
    public void removeBook(Book book){

        this.remove(book);
    }

}

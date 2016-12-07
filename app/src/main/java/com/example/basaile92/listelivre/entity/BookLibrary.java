package com.example.basaile92.listelivre.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by basaile92 on 29/09/2016.
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
}

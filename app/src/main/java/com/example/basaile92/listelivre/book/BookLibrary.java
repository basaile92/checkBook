package com.example.basaile92.listelivre.book;

import com.example.basaile92.listelivre.book.Book;

import java.io.Serializable;
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


}

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
     * Modify a book in the Book library if it already exists. Add just the book in the other case
     * @param book is the new Book that you want to add in the library.
     */
    public void modifyBook(Book book){

        for(Book b : this){

            if(b.getIsbn().equals(book.getIsbn()))
            {

                this.removeBook(b);
            }
        }

        this.add(book);
    }

    /**
     * Remove a book in the Book library.
     * @param book is the Book that you want to remove in the library.
     */
    public void removeBook(Book book){

        this.remove(book);
    }

    /**
     * Test if the book already exists in the library.
     * @param book is the book you want to check the presence in the library
     * @return true or false
     */
    public boolean existBook(Book book){

        boolean test = false;
        for(Book b : this){

            if(b.getIsbn().equals(book.getIsbn()))
            {

                test = true;
            }
        }

        return test;
    }
}

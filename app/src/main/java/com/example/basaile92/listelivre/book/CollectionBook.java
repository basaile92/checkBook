package com.example.basaile92.listelivre.book;

import java.util.HashMap;

/**
 * Created by Mercier on 27/10/2016.
 */

public class CollectionBook extends Book {

    protected HashMap<Book, Integer> books;

    /**
     * Constructor of a collection Book
     * @param author
     * @param title
     */
    public CollectionBook(String author, String title) {
        this.author = author;
        this.title = title;
        this.books = new HashMap<Book, Integer>();
    }

    /**
     * Add a book into the collection
     * @param book
     * @param number is the number of the book inside the collection
     */
    public void addBook(Book book, int number){
        if (!(this.books.containsValue(number))){
            this.books.put(book, number);
        } else{

            new NumberAlreadyExistsException();
        }
    }

    /**
     * Remove a book from the collection
     * @param book
     */
    public void removeBook(Book book){
        if(this.books.containsKey(book)) {
            this.books.remove(book);
        }
    }

    @Override
    public boolean canContainBook(){

        return true;
    }
}

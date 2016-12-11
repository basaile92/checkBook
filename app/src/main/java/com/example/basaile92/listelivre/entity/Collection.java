package com.example.basaile92.listelivre.entity;

/**
 * Class to manipulate a collection object
 */
public class Collection {

    private String name;
    private BookLibrary books = new BookLibrary();

    //Constructors
    public Collection() {
        this.books = new BookLibrary();
    }


    public Collection(String name, BookLibrary books){
        this.name = name;
        this.books = books;
    }

    /**
     * Set the name of a collection
     * @param name : the new name
     */
    public void setName(String name) {

        this.name = name;
    }


    /**
     * @return the collection's name
     */
    public String getName() {

        return this.name;
    }


    /**
     * Set books of a collection
     * @param books : the new books
     */
    public void setBooks(BookLibrary books) {

        this.books = books;
    }


    /**
     * @return books of the collection
     */
    public BookLibrary getBooks() {

        return this.books;
    }
}

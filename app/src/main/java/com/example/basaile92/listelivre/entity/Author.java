package com.example.basaile92.listelivre.entity;

import java.io.Serializable;

/**
 * Class used to manipulate an author of a book
 * An author is associated with a book's isbn
 */
public class Author implements Serializable {

    private long id;
    private String name;
    private String isbn;

    /**
     * Constructor of class Author
     * @param name : author's name
     * @param isbn : book's isbn
     */
    public Author(String name, String isbn){

        this.name = name;
        this.isbn = isbn;
    }

    /**
     * Constructor of class Author with empty field
     */
    public Author(){
        this.name = "";
        this.isbn = "";
    }

    /**
     * @return the id of the author
     */
    public long getId() {
        return this.id;
    }


    /**
     * Set the id of an author
     * @param id : the new id
     */
    public void setId(long id) {
        this.id = id;
    }


    /**
     * @return the author's name
     */
    public String getName() {
        return this.name;
    }


    /**
     * Set the author's name
     * @param name : the new name
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * @return the book's isbn of the author
     */
    public String getIsbn() {
        return this.isbn;
    }


    /**
     * Set the book's isbn
     * @param isbn : the new isbn
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
}

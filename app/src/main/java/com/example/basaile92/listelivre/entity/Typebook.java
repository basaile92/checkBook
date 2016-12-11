package com.example.basaile92.listelivre.entity;

import java.io.Serializable;

/**
 * A TypeBook is used to associate a Book with a Type.
 * It contains an id, the type's name and the book's isbn
 */
public class Typebook implements Serializable {

    private long id;
    private String nametype;
    private String isbn;

    /**
     * @return the TypeBook's id
     */
    public long getId() {
        return this.id;
    }


    /**
     * Set the TypeBook's id
     * @param id : a new id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the TypeBook's nameType
     */
    public String getNametype() {
        return this.nametype;
    }

    /**
     * Set the TypeBook's nameType
     * @param nametype : a new name of type
     */
    public void setNametype(String nametype) {
        this.nametype = nametype;
    }

    /**
     * @return the TypeBook's isbn
     */
    public String getIsbn() {
        return this.isbn;
    }

    /**
     * Set the TypeBook's isbn
     * @param isbn : a new isbn to associate an other Book with the Type
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }


}

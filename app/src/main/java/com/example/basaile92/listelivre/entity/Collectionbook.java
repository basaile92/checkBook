package com.example.basaile92.listelivre.entity;

/**
 * Class use to associate a book with a collection
 */
public class Collectionbook {

    private long id;
    private String namecollection;
    private String isbn;


    //Constructor
    public Collectionbook(){

    }

    public Collectionbook(String namecollection, String isbn) {

        this.namecollection = namecollection;
        this.isbn = isbn;
    }


    /**
     * Set the id of a collectionBook
     * @param id : the new id
     */
    public void setId(long id) {

        this.id = id;
    }


    /**
     * Set the collection's name of a collectionBook
     * @param namecollection : the new collection's name
     */
    public void setNamecollection(String namecollection) {

        this.namecollection = namecollection;
    }


    /**
     * Set the book's isbn of a collectionBook
     * @param isbn : the new book's isbn
     */
    public void setIsbn(String isbn) {

        this.isbn = isbn;
    }


    /**
     * @return the id of the collectionBook
     */
    public long getId(){

        return this.id;
    }


    /**
     * @return the collection's name of the collectionBook
     */
    public String getNamecollection() {

        return this.namecollection;
    }


    /**
     * @return the book's isbn of the collectionBook
     */
    public String getIsbn() {

        return this.isbn;
    }
}

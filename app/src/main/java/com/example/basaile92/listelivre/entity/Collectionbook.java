package com.example.basaile92.listelivre.entity;

/**
 * Created by Basile on 22/11/2016.
 */
public class Collectionbook {

    private long id;
    private String namecollection;
    private String isbn;

    public void setId(long id) {

        this.id = id;
    }

    public void setNamecollection(String namecollection) {

        this.namecollection = namecollection;
    }

    public void setIsbn(String isbn) {

        this.isbn = isbn;
    }

    public long getId(){

        return this.id;
    }

    public String getNamecollection() {

        return this.namecollection;
    }

    public String getIsbn() {

        return this.isbn;
    }
}

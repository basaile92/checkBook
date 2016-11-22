package com.example.basaile92.listelivre.entity;

import java.io.Serializable;

public class Author implements Serializable {

    private long id;
    private String name;
    private String isbn;

    public Author(String name, String isbn){

        this.name = name;
        this.isbn = isbn;
    }

    public Author(){
        this.name = "";
        this.isbn = "";
    }

    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getIsbn() {
        return this.isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
}

package com.example.basaile92.listelivre.entity;

import java.io.Serializable;

public class Typebook implements Serializable {

    private long id;
    private String nametype;
    private String isbn;
    
    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getNametype() {
        return this.nametype;
    }
    
    public void setNametype(String nametype) {
        this.nametype = nametype;
    }
    
    public String getIsbn() {
        return this.isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }


}

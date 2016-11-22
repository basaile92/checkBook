package com.example.basaile92.listelivre.entity;

import java.io.Serializable;

public class Type implements Serializable {

    private String name;

    public Type(String name){

        this.name = name;
    }

    public Type(){
        this.name = new String();
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}

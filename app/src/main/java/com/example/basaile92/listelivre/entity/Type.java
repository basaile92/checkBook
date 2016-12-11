package com.example.basaile92.listelivre.entity;

import java.io.Serializable;

/**
 * A Type is use to give information about a Book.
 * It contains just a name.
 */
public class Type implements Serializable {

    private String name;

    /**
     * Constructor of class Type
     * @param name : name of the type
     */
    public Type(String name){

        this.name = name;
    }

    /**
     * Constructor of class Type without parameter
     * The name is initialized with an empty string
     */
    public Type(){
        this.name = new String();
    }


    /**
     * @return the Type's name
     */
    public String getName() {
        return this.name;
    }


    /**
     * Set the Type's name
     * @param name : a new name
     */
    public void setName(String name) {
        this.name = name;
    }
}

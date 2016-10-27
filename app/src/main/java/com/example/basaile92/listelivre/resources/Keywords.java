package com.example.basaile92.listelivre.resources;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Mercier on 27/10/2016.
 */
public class Keywords extends ArrayList<String> implements Serializable{

    /**
     * Add a keyword in the keywords list
     * @param keyword
     */
    public void addKeyword (String keyword){
        this.add(keyword);
    }

    /**
     * Remove the keyword from the keywords list
     * @param keyword
     */
    public void removeKeyword(String keyword){
        this.remove(keyword);
    }

}

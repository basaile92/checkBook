package com.example.basaile92.listelivre.entity;

import java.util.ArrayList;

/**
 * Used to manipulate a list of Author
 */

public class AuthorList extends ArrayList<Author> {


    /**
     * Add an author to the list
     * @param author : author to add
     */
    public void addAuthor(Author author){

        this.add(author);
    }


    /**
     * Remove an author from the list
     * @param author : author to remove
     */
    public void removeAuthor(Author author){

        this.remove(author);
    }


    /**
     * @return a string to describe the author
     */
    public String toString(){

        String res = "";
        int i = 0;
        for(Author author : this){

            if(i != 0){
                res += " , ";
            }
            res += author.getName();
            i++;

        }
        return res;
    }

}

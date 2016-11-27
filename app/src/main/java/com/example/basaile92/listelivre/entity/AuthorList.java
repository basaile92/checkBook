package com.example.basaile92.listelivre.entity;

import java.util.ArrayList;

/**
 * Created by Basile on 20/11/2016.
 */

public class AuthorList extends ArrayList<Author> {

    public void addAuthor(Author author){

        this.add(author);
    }

    public void removeAuthor(Author author){

        this.remove(author);
    }


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

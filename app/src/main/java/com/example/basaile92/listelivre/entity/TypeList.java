package com.example.basaile92.listelivre.entity;

import java.util.ArrayList;

/**
 * Created by Basile on 20/11/2016.
 */

public class TypeList extends ArrayList<Type> {

    public void addType(Type type){

        this.add(type);
    }

    public void removeAuthor(Type type){

        this.remove(type);
    }

    public String toString(){

        String res = "";
        for(Type type : this){

            res += type.getName();
            if(this.size()>0){
                res += " , ";
            }
        }
        return res;
    }

}

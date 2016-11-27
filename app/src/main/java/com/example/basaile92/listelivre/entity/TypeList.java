package com.example.basaile92.listelivre.entity;

import java.util.ArrayList;

/**
 * Created by Basile on 20/11/2016.
 */

public class TypeList extends ArrayList<Type> {

    public void addType(Type type){

        this.add(type);
    }

    public void removeType(Type type){

        this.remove(type);
    }

    public String toString(){

        String res = "";
        int i = 0;
        for(Type type : this){

            if(i != 0){
                res += " , ";
            }

            res += type.getName();
            i++;
        }
        return res;
    }

}

package com.example.basaile92.listelivre.entity;

import java.util.ArrayList;

/**
 * Class used to manipulate a list of Type
 */
public class TypeList extends ArrayList<Type> {

    /**
     * Add a new type in the list
     * @param type : Type to add
     */
    public void addType(Type type){

        this.add(type);
    }

    /**
     * Remove a type from the list
     * @param type : Type to remove
     */
    public void removeType(Type type){

        this.remove(type);
    }

    /**
     * @return a string to describe the TypeList
     */
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

package com.example.basaile92.listelivre.entity;

import java.util.ArrayList;

/**
 * Class to manipulate a list of collection
 */

public class CollectionList extends ArrayList<Collection> {

    /**
     * Use to add a new collection
     * @param collection : collection to add
     */
    public void addCollection(Collection collection){

        this.add(collection);
    }


    /**
     * Use to remove a collection
     * @param collection collection to remove
     */
    public void removeCollection(Collection collection){

        this.remove(collection);
    }


}

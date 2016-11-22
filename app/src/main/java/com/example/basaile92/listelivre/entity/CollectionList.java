package com.example.basaile92.listelivre.entity;

import java.util.ArrayList;

/**
 * Created by Basile on 22/11/2016.
 */

public class CollectionList extends ArrayList<Collection> {

    public void addCollection(Collection collection){

        this.add(collection);
    }

    public void removeCollection(Collection collection){

        this.remove(collection);
    }


}

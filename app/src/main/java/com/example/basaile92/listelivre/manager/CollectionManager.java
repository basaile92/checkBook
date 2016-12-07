package com.example.basaile92.listelivre.manager;

import android.content.Context;

import com.example.basaile92.listelivre.data.CollectionData;
import com.example.basaile92.listelivre.database.DAOBase;
import com.example.basaile92.listelivre.entity.Collection;
import com.example.basaile92.listelivre.entity.CollectionList;

import java.util.List;

/**
 * Created by Basile on 22/11/2016.
 */

public class CollectionManager extends DAOBase {

    public CollectionManager(Context pContext) {

        super(pContext);
    }

    public CollectionList readCollectionList(){

        CollectionData collectionData = new CollectionData(handler);
        CollectionList collectionList = collectionData.getAllCollection();
        return collectionList;
    }


    public void saveCollection(Collection collection){

        CollectionData collectionData = new CollectionData(handler);
        collectionData.createCollection(collection);
    }


    public void modifyCollectionName(Collection collection, String newName){

        CollectionData collectionData = new CollectionData(handler);

        collectionData.updateCollectionByName(collection, newName);
    }


    public Collection getCollectionAtPosition (int position) {

        CollectionData collectionData = new CollectionData(handler);
        List<Collection> collectionList = collectionData.getAllCollection();

        Collection collection = collectionList.get(position);

        return collection;
    }


    public Collection getCollectionByName (String name) {

        CollectionData collectionData = new CollectionData(handler);
        Collection collection = collectionData.getCollectionByName(name);

        return collection;
    }


    public void deleteCollection(Collection collection) {

        CollectionData collectionData = new CollectionData(handler);
        collectionData.deleteCollectionByName(collection.getName());
    }


    public boolean existCollection(String name){

        boolean exist;

        CollectionData collectionData = new CollectionData(handler);
        exist = (collectionData.getCollectionByName(name) != null);

        return exist;
    }
}

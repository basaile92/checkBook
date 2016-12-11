package com.example.basaile92.listelivre.manager;

import android.content.Context;

import com.example.basaile92.listelivre.data.CollectionData;
import com.example.basaile92.listelivre.database.DAOBase;
import com.example.basaile92.listelivre.entity.Collection;
import com.example.basaile92.listelivre.entity.CollectionList;

import java.util.List;

/**
 * Class to manipulate the collection database
 */

public class CollectionManager extends DAOBase {

    //Constructor
    public CollectionManager(Context pContext) {

        super(pContext);
    }


    /**
     * Get all collections from the database
     * @return a CollectionList
     */
    public CollectionList readCollectionList(){

        CollectionData collectionData = new CollectionData(handler);

        return collectionData.getAllCollection();
    }


    /**
     * Create a new Collection in the database
     * @param collection : collection to add in the database
     */
    public void saveCollection(Collection collection){

        CollectionData collectionData = new CollectionData(handler);
        collectionData.createCollection(collection);
    }


    /**
     * Modify collection's name in the database
     * @param collection : the new collection
     * @param Name of the collection in the database
     */
    public void modifyCollectionName(Collection collection, String Name){

        CollectionData collectionData = new CollectionData(handler);

        collectionData.updateCollectionByName(collection, Name);
    }


    /**
     * Get the collection at position "position" in the database
     * @param position : position of the collection
     * @return a Collection
     */
    public Collection getCollectionAtPosition (int position) {

        CollectionData collectionData = new CollectionData(handler);
        List<Collection> collectionList = collectionData.getAllCollection();

        return collectionList.get(position);
    }


    /**
     * Get the collection with the name 'name'
     * @param name : name of the collection
     * @return a Collection
     */
    public Collection getCollectionByName (String name) {

        CollectionData collectionData = new CollectionData(handler);

        return collectionData.getCollectionByName(name);
    }


    /**
     * Delete a collection from the database
     * @param collection : collection to delete
     */
    public void deleteCollection(Collection collection) {

        CollectionData collectionData = new CollectionData(handler);
        collectionData.deleteCollectionByName(collection.getName());
    }


    /**
     * Check if the collection already exist in the database
     * @param name : name to check
     * @return True if the collection already exist
     *          False if not
     */
    public boolean existCollection(String name){

        boolean exist;

        CollectionData collectionData = new CollectionData(handler);
        exist = (collectionData.getCollectionByName(name) != null);

        return exist;
    }
}

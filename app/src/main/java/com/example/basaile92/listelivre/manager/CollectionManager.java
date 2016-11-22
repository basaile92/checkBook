package com.example.basaile92.listelivre.manager;

import android.content.Context;

import com.example.basaile92.listelivre.data.CollectionData;
import com.example.basaile92.listelivre.database.DAOBase;
import com.example.basaile92.listelivre.entity.CollectionList;

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



}

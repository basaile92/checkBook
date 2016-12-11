package com.example.basaile92.listelivre.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.basaile92.listelivre.database.MySQLHelper;
import com.example.basaile92.listelivre.entity.Collection;
import com.example.basaile92.listelivre.entity.CollectionList;

/**
 * Database for Collection.
 * Each Collection contains a name.
 */
public class CollectionData {
    
    private static final String LOG = "CollectionData";
    
    public static final String TABLE_COLLECTION = "collection";
    
    public static final String KEY_NAME = "name";

    private MySQLHelper helper;
    
    public CollectionData(MySQLHelper helper) {
        this.helper = helper;
    }

    /**
     * @param c a cursor
     * @return the Collection which is designated by the cursor
     */
    public Collection fromCursor(Cursor c) {

        if(c.getCount() > 0) {


            Collection entry = new Collection();
            entry.setName(c.getString(c.getColumnIndex(KEY_NAME)));
            return entry;
        }
        return null;
    }

    /**
     * Create a ContentValue with a Collection information
     * @param arg a Collection which contains information
     * @return a ContentValue
     */
    public ContentValues toContentValues(Collection arg) {
    
        ContentValues values = new ContentValues();
        
        values.put(KEY_NAME, arg.getName());
        
        return values;
    }

    /**
     * Create a new Collection in the database
     * @param arg Collection to add in the database
     * @return the id
     */
    public long createCollection(Collection arg) {
        SQLiteDatabase db = helper.getWritableDatabase();
 
        ContentValues values = toContentValues(arg);
 
        return db.insert(TABLE_COLLECTION, null, values);
    }

    /**
     * @param query the condition
     * @return a list of Collection which contains all Collection from the database which respects the query
     */
    public CollectionList getAllCollectionByQuery(String query) {
        
        CollectionList list = new CollectionList();
            
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
 
        if (c.moveToFirst()) {
            do {
                Collection entry = fromCursor(c);
 
                list.addCollection(entry);
            } while (c.moveToNext());
        }

        c.close();
 
        return list;
    }

    /**
     * @param query the condition
     * @return a Collection from the database which respects the query
     */
    public Collection getCollectionByQuery(String query) {
        
        SQLiteDatabase db = helper.getReadableDatabase();
        Log.e(LOG, query);
 
        Cursor c = db.rawQuery(query, null);
 
        if (c != null) {
            c.moveToFirst();
        }
        
        Collection entry = fromCursor(c);

        c.close();
 
        return entry;
    }

    /**
     * @param name
     * @return a Collection from the database with the name 'name'
     */
    public Collection getCollectionByName(String name) {

        String selectQuery = "SELECT  * FROM " + TABLE_COLLECTION + " WHERE "
            + KEY_NAME + " = " + "'" + name + "'"
            ;

        return getCollectionByQuery(selectQuery);
    }


    /**
     * @return all Collection from the database
     */
    public CollectionList getAllCollection() {
    
        String selectQuery = "SELECT  * FROM " + TABLE_COLLECTION;

        return getAllCollectionByQuery(selectQuery);
    }

    /**
     * Modify information of the Collection have the name 'name' in the database
     * @param arg new Collection
     * @param name
     * @return the id
     */
    public long updateCollectionByName(Collection arg, String name) {
        SQLiteDatabase db = helper.getWritableDatabase();
 
        ContentValues values = toContentValues(arg);
 
        // updating row
        return db.update(TABLE_COLLECTION, values, KEY_NAME + " = ?",
                new String[] { String.valueOf(name) });
    }

    /**
     * Delete a Collection from the database which have the name 'name'
     * @param name
     */
    public void deleteCollectionByName(String name) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TABLE_COLLECTION, KEY_NAME + " = ?",
                new String[] { String.valueOf(name) });
    }
}

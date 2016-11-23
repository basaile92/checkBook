package com.example.basaile92.listelivre.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.basaile92.listelivre.database.MySQLHelper;
import com.example.basaile92.listelivre.entity.Collection;
import com.example.basaile92.listelivre.entity.CollectionList;

public class CollectionData {
    
    private static final String LOG = "CollectionData";
    
    public static final String TABLE_COLLECTION = "collection";
    
    public static final String KEY_NAME = "name";

    private MySQLHelper helper;
    
    public CollectionData(MySQLHelper helper) {
        this.helper = helper;
    }
    
    public Collection fromCursor(Cursor c) {
 
        Collection entry = new Collection();
        entry.setName(c.getString(c.getColumnIndex(KEY_NAME)));
        return entry;
    }
    
    public ContentValues toContentValues(Collection arg) {
    
        ContentValues values = new ContentValues();
        
        values.put(KEY_NAME, arg.getName());
        
        return values;
    }
    
    public long createCollection(Collection arg) {
        SQLiteDatabase db = helper.getWritableDatabase();
 
        ContentValues values = toContentValues(arg);
 
        return db.insert(TABLE_COLLECTION, null, values);
    }
    
    public CollectionList getAllCollectionByQuery(String query) {
        
        CollectionList list = new CollectionList();
            
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
 
        if (c.moveToFirst()) {
            do {
                Collection entry = fromCursor(c);
 
                // adding to todo list
                list.addCollection(entry);
            } while (c.moveToNext());
        }

        c.close();
 
        return list;
    }
    
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
    
    public Collection getCollectionByName(String name) {

        String selectQuery = "SELECT  * FROM " + TABLE_COLLECTION + " WHERE "
            + KEY_NAME + " = " + "'" + name + "'"
            ;

        return getCollectionByQuery(selectQuery);
    }
    
    
    
    public CollectionList getAllCollection() {
    
        String selectQuery = "SELECT  * FROM " + TABLE_COLLECTION;

        return getAllCollectionByQuery(selectQuery);
    }
    
    public long updateCollectionByName(Collection arg, String name) {
        SQLiteDatabase db = helper.getWritableDatabase();
 
        ContentValues values = toContentValues(arg);
 
        // updating row
        return db.update(TABLE_COLLECTION, values, KEY_NAME + " = ?",
                new String[] { String.valueOf(name) });
    }
    
    public void deleteCollectionByName(String name) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TABLE_COLLECTION, KEY_NAME + " = ?",
                new String[] { String.valueOf(name) });
    }
}

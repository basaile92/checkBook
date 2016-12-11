package com.example.basaile92.listelivre.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.basaile92.listelivre.database.MySQLHelper;
import com.example.basaile92.listelivre.entity.Type;
import com.example.basaile92.listelivre.entity.TypeList;

/**
 * Database for Type.
 * Each Type contains a name.
 */
public class TypeData {
    
    private static final String LOG = "TypeData";
    
    public static final String TABLE_TYPE = "type";
    
    public static final String KEY_NAME = "name";

    private MySQLHelper helper;
    
    public TypeData(MySQLHelper helper) {
        this.helper = helper;
    }

    /**
     * @param c a cursor
     * @return the Type which is designated by the cursor
     */
    public Type fromCursor(Cursor c) {
 
        Type entry = new Type();

        if(c.getCount() > 0){
            String res = c.getString(c.getColumnIndex(KEY_NAME));
            entry.setName(res);
            return entry;
        }
        return null;
    }

    /**
     * Create a ContentValue with a Type information
     * @param arg a Type which contains information
     * @return a ContentValue
     */
    public ContentValues toContentValues(Type arg) {
    
        ContentValues values = new ContentValues();
        
        values.put(KEY_NAME, arg.getName());
        
        return values;
    }

    /**
     * Create new Type in the database
     * @param arg Type to add
     */
    public long createType(Type arg) {
        SQLiteDatabase db = helper.getWritableDatabase();
 
        ContentValues values = toContentValues(arg);
 
        return db.insert(TABLE_TYPE, null, values);
    }

    /**
     * @param query the condition
     * @return a list of Type which contains all Types from the database which respects the query
     */
    public TypeList getAllTypeByQuery(String query) {
        
        TypeList list = new TypeList();
            
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
 
        if (c.moveToFirst()) {
            do {
                Type entry = fromCursor(c);
 
                list.add(entry);
            } while (c.moveToNext());
        }

        c.close();
 
        return list;
    }

    /**
     * @param query the condition
     * @return a Type from the database which respects the query
     */
    public Type getTypeByQuery(String query) {
        
        SQLiteDatabase db = helper.getReadableDatabase();
        Log.e(LOG, query);
 
        Cursor c = db.rawQuery(query, null);
 
        if (c != null) {
            c.moveToFirst();
        }
        
        Type entry = fromCursor(c);

        c.close();
 
        return entry;
    }

    /**
     * @param name
     * @return a Type from the database with the name 'name'
     */
    public Type getTypeByName(String name) {

        if(name != null) {
            String selectQuery = "SELECT  * FROM " + TABLE_TYPE + " WHERE "
                    + KEY_NAME + " = " + "'" + name + "'";

            return getTypeByQuery(selectQuery);
        }else
            return null;
    }


    /**
     * @return all Types from the database
     */
    public TypeList getAllType() {
    
        String selectQuery = "SELECT  * FROM " + TABLE_TYPE;

        return getAllTypeByQuery(selectQuery);
    }


    /**
     * Modify information of the Type have the name 'name' in the database
     * @param arg new Type
     * @param name
     * @return the id
     */
    public long updateTypeByName(Type arg, String name) {
        SQLiteDatabase db = helper.getWritableDatabase();
 
        ContentValues values = toContentValues(arg);
 
        // updating row
        return db.update(TABLE_TYPE, values, KEY_NAME + " = ?",
                new String[] { String.valueOf(name) });
    }

    /**
     * Delete a Type from the database which have the name 'name'
     * @param name
     */
    public void deleteTypeByName(String name) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TABLE_TYPE, KEY_NAME + " = ?",
                new String[] { String.valueOf(name) });
    }



}

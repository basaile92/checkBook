package com.example.basaile92.listelivre.resources;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Basile on 02/11/2016.
 */

public abstract class DAOBase {

    protected final static int VERSION = 1;
    protected final static String NOM = "database.db";

    protected SQLiteDatabase db = null;
    protected BookDatabaseHandler handler = null;

    public DAOBase(Context pContext) {
        this.handler = new BookDatabaseHandler(pContext, NOM, null, VERSION);
        open();
    }

    public SQLiteDatabase open() {
        db = handler.getWritableDatabase();
        return db;
    }

    public void close() {
        db.close();
    }

    public SQLiteDatabase getDb() {
        return db;
    }

}

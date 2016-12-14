package com.example.basaile92.listelivre.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public abstract class DAOBase {

    protected SQLiteDatabase db = null;
    protected MySQLHelper handler = null;

    public DAOBase(Context pContext) {
        this.handler = new MySQLHelper(pContext);
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

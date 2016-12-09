package com.example.basaile92.listelivre.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "database";

    // Table names
    private static final String TABLE_BOOK = "book";
    private static final String TABLE_AUTHOR = "author";
    private static final String TABLE_TYPEBOOK = "typebook";
    private static final String TABLE_TYPE = "type";
    private static final String TABLE_COLLECTION = "collection";
    private static final String TABLE_COLLECTIONBOOK = "collectionbook";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_ISBN = "isbn";

    // Table book columns
    private static final String KEY_TITLE = "title";
    private static final String KEY_PUBLISHER = "publisher";
    private static final String KEY_YEAR = "year";
    private static final String KEY_SUMMARY = "summary";
    private static final String KEY_ISREAD = "isread";
    private static final String KEY_ISBORROWED = "isborrowed";
    private static final String KEY_COMMENT = "comment";
    private static final String KEY_PHOTO = "photo";

    // Table typebook columns
    private static final String KEY_NAMETYPE = "nametype";


    // Table collectionbook columns
    private static final String KEY_NAMECOLLECTION = "namecollection";
    // book table create statement
    private static final String CREATE_TABLE_BOOK =
            "CREATE TABLE " + TABLE_BOOK + " ("
                    +    KEY_ISBN + " TEXT, "
                    +    KEY_TITLE + " TEXT, "
                    +    KEY_PUBLISHER + " TEXT, "
                    +    KEY_YEAR + " TEXT, "
                    +    KEY_SUMMARY + " TEXT, "
                    +    KEY_ISREAD + " INTEGER, "
                    +    KEY_ISBORROWED + " INTEGER, "
                    +    KEY_COMMENT + " TEXT, "
                    +    KEY_PHOTO + " TEXT, "

                    +   "PRIMARY KEY(isbn) "
                    +   ")";

    // author table create statement
    private static final String CREATE_TABLE_AUTHOR =
            "CREATE TABLE " + TABLE_AUTHOR + " ("
                    +    KEY_ID + " INTEGER, "
                    +    KEY_NAME + " TEXT, "
                    +    KEY_ISBN + " TEXT, "
                    +   "FOREIGN KEY (isbn) "
                    +       "REFERENCES book(isbn) "
                    +       "ON DELETE CASCADE "
                    +       "ON UPDATE CASCADE "
                    +   ", "

                    +   "PRIMARY KEY(id) "
                    +   ")";

    // typebook table create statement
    private static final String CREATE_TABLE_TYPEBOOK =
            "CREATE TABLE " + TABLE_TYPEBOOK + " ("
                    +    KEY_ID + " INTEGER, "
                    +    KEY_NAMETYPE + " TEXT, "
                    +    KEY_ISBN + " TEXT, "
                    +   "FOREIGN KEY (nametype) "
                    +       "REFERENCES type(name) "
                    +       "ON DELETE CASCADE "
                    +       "ON UPDATE CASCADE "
                    +   ", "
                    +   "FOREIGN KEY (isbn) "
                    +       "REFERENCES book(isbn) "
                    +       "ON DELETE CASCADE "
                    +       "ON UPDATE CASCADE "
                    +   ", "

                    +   "PRIMARY KEY(id) "
                    +   ")";

    // type table create statement
    private static final String CREATE_TABLE_TYPE =
            "CREATE TABLE " + TABLE_TYPE + " ("
                    +    KEY_NAME + " TEXT, "

                    +   "PRIMARY KEY(name) "
                    +   ")";

    // collection table create statement
    private static final String CREATE_TABLE_COLLECTION =
            "CREATE TABLE " + TABLE_COLLECTION + " ("
                    +    KEY_NAME + " TEXT, "

                    +   "PRIMARY KEY(name) "
                    +   ")";

    // collectionbook table create statement
    private static final String CREATE_TABLE_COLLECTIONBOOK =
            "CREATE TABLE " + TABLE_COLLECTIONBOOK + " ("
                    +    KEY_ID + " INTEGER, "
                    +    KEY_NAMECOLLECTION + " TEXT, "
                    +    KEY_ISBN + " TEXT, "
                    +   "FOREIGN KEY (namecollection) "
                    +       "REFERENCES collection(name) "
                    +       "ON DELETE CASCADE "
                    +       "ON UPDATE CASCADE "
                    +   ", "
                    +   "FOREIGN KEY (isbn) "
                    +       "REFERENCES book(isbn) "
                    +       "ON DELETE CASCADE "
                    +       "ON UPDATE CASCADE "
                    +   ", "

                    +   "PRIMARY KEY(id) "
                    +   ")";


    public MySQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON");
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_BOOK);
        db.execSQL(CREATE_TABLE_AUTHOR);
        db.execSQL(CREATE_TABLE_TYPEBOOK);
        db.execSQL(CREATE_TABLE_TYPE);
        db.execSQL(CREATE_TABLE_COLLECTION);
        db.execSQL(CREATE_TABLE_COLLECTIONBOOK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUTHOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPEBOOK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COLLECTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COLLECTIONBOOK);

        // create new tables
        onCreate(db);
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}


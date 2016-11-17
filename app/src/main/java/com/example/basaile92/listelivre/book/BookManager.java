package com.example.basaile92.listelivre.book;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.basaile92.listelivre.database.DAOBase;

import java.util.ArrayList;

/**
 * Created by basaile92 on 06/10/2016.
 */

public class BookManager extends DAOBase {

    public static final String bookNameDb = "Book";
    public static final String isbnBookDb = "isbn";
    public static final String titleBookDb = "title";
    public static final String collectionBookDb = "collection";
    public static final String publisherBookDb = "publisher";
    public static final String yearBookDb = "year";
    public static final String summaryBookDb = "summary";
    public static final String isReadDb = "isread";
    public static final String isBorrowedDb = "isborrowed";
    public static final String borrowerDb = "borrower";
    public static final String ownerDb = "owner";
    public static final String commentDb = "comment";
    public static final String photoDb = "photo";

    public static final String authorNameDb = "Author";
    public static final String nameAuthorDb = "name";

    public static final String typeBookNameDb = "TypeBook";
    public static final String idTypeDb = "idType";
    public static final String idBookDb = "idBook";

    public static final String typeNameDb = "Type";
    public static final String nameTypeDb = "name";


    public BookManager(Context pContext) {
        super(pContext);
    }


    /**
     * Add a book in the book save file.
     *
     * @param book is the book that you want to add in the save file.
     */
    public void saveSimpleBook(SimpleBook book) throws BookAlreadyExistsException {

        if (dbExistSimpleBook(book)) {

            throw new BookAlreadyExistsException();
        }


        ContentValues value = new ContentValues();
        value.put(isbnBookDb, book.getIsbn());
        value.put(titleBookDb, book.getTitle());
        value.put(collectionBookDb, book.getCollection());
        value.put(publisherBookDb, book.getPublisher());
        value.put(yearBookDb, book.getYear());
        value.put(summaryBookDb, book.getSummary());
        value.put(isReadDb, book.isRead());
        value.put(isBorrowedDb, book.isBorrowed());
        value.put(borrowerDb, book.getBorrower());
        value.put(ownerDb, book.getOwner());
        value.put(commentDb, book.getComment());
        value.put(photoDb, book.getPhoto());

        ContentValues valueAuthor = new ContentValues();

        for(String author: book.getAuthors()){

            valueAuthor.put(nameAuthorDb, author);
            valueAuthor.put(isbnBookDb, book.getIsbn());
        }

        ContentValues valueTypeBook = new ContentValues();

        for(String type: book.getTypes()){

            Cursor cursor = db.rawQuery("SELECT "+ idTypeDb +" FROM "+ typeNameDb + " WHERE " + nameTypeDb + " = ?", new String[]{ type} );
            valueTypeBook.put(idTypeDb, cursor.getString(0));
            valueTypeBook.put(idBookDb, book.getIsbn());

            cursor.close();
        }



        db.insert(bookNameDb, null, value);
        db.insert(authorNameDb, null, valueAuthor);
        db.insert(typeBookNameDb, null, valueTypeBook);
    }

    private boolean dbExistSimpleBook(SimpleBook book){

        Cursor cursor = db.rawQuery("SELECT * FROM "+ bookNameDb + " WHERE " + isbnBookDb + " = ?", new String[]{ book.getIsbn()} );
        boolean res = cursor.getCount() != 0;

        cursor.close();
        return res;
    }

    private boolean dbExistSimpleBook(SimpleBook book, long position){

        if(book.getIsbn().equals(getSimpleBook(position).getIsbn())){

            return false;
        }

        Cursor cursor = db.rawQuery("SELECT * FROM "+ bookNameDb + " WHERE " + isbnBookDb + " = ?", new String[]{ book.getIsbn()} );
        boolean res = cursor.getCount() !=0;

        cursor.close();
        return res;


    }


    /**
     * Modify a book in the book save file.
     *
     * @param book is the new book that you want to put in the save file.
     */
    public void modifyBook(SimpleBook book, long position) throws BookAlreadyExistsException {

        if (dbExistSimpleBook(book, position)){

            throw new BookAlreadyExistsException();
        }


        long pos = position;
        Cursor cursor = db.rawQuery("SELECT * FROM "+ bookNameDb, null);

        while(cursor.moveToNext() && pos > 0){

            pos --;

        }
        long id = cursor.getLong(0);

        ContentValues value = new ContentValues();
        value.put(isbnBookDb, book.getIsbn());
        value.put(titleBookDb, book.getTitle());
        value.put(collectionBookDb, book.getCollection());
        value.put(publisherBookDb, book.getPublisher());
        value.put(summaryBookDb, book.getSummary());
        value.put(yearBookDb, book.getYear());
        value.put(isReadDb, book.isRead());
        value.put(isBorrowedDb, book.isBorrowed());
        value.put(borrowerDb, book.getBorrower());
        value.put(ownerDb, book.getOwner());
        value.put(commentDb, book.getComment());
        value.put(photoDb, book.getPhoto());

        ContentValues valueAuthor = new ContentValues();

        db.delete(authorNameDb, isbnBookDb + " = ?", new String[]{book.getIsbn()});

        for(String author: book.getAuthors()){

            valueAuthor.put(nameAuthorDb, author);
            valueAuthor.put(isbnBookDb, book.getIsbn());
        }

        ContentValues valueTypeBook = new ContentValues();

        db.delete(typeBookNameDb, isbnBookDb + " = ? ", new String[]{book.getIsbn()});

        for(String type: book.getTypes()){

            Cursor cursorType = db.rawQuery("SELECT "+ idTypeDb +" FROM "+ typeNameDb + " WHERE " + nameTypeDb + " = ?", new String[]{ type} );
            valueTypeBook.put(idTypeDb, cursorType.getString(0));
            valueTypeBook.put(idBookDb, book.getIsbn());

            cursorType.close();
        }

        cursor.close();
        db.update(bookNameDb, value, idBookDb + " = ? ", new String[]{String.valueOf(id)});
        db.insert(authorNameDb, null, valueAuthor);
        db.insert(typeBookNameDb, null, valueTypeBook);

    }

    /**
     * Delete a book in the book save file.
     * @param position is the position in the database
     */
    public void deleteBook(long position) {


        long pos = position;
        Cursor cursor = db.rawQuery("SELECT "+ idBookDb+", "+isbnBookDb+" FROM "+ bookNameDb, null);

        while(cursor.moveToNext() && pos > 0){

            pos --;

        }

        long id = cursor.getLong(0);
        String isbn = cursor.getString(1);


        cursor.close();

        db.delete(bookNameDb, idBookDb + " = ?", new String[]{String.valueOf(id)});
        db.delete(authorNameDb, isbnBookDb + " = ?", new String[]{isbn});
        db.delete(typeBookNameDb, isbnBookDb + " = ?", new String[]{isbn});
    }

    /**
     * Read the Book Library in the book save file.
     *
     * @return the booklibrary
     */
    public BookLibrary readBookLibrary() {


        BookLibrary bookLibrary = new BookLibrary();
        Cursor cursor = db.rawQuery("SELECT * FROM " + bookNameDb, null);

        while (cursor.moveToNext()) {

            String isbn = cursor.getString(1);
            String title = cursor.getString(2);
            String collection = cursor.getString(3);
            String publisher = cursor.getString(4);
            String year = cursor.getString(5);
            String summary = cursor.getString(6);
            boolean isRead = intToBool(cursor.getInt(7));
            boolean isBorrowed = intToBool(cursor.getInt(8));
            String borrower = cursor.getString(9);
            String owner = cursor.getString(10);
            String comment = cursor.getString(11);
            String photo = cursor.getString(12);

            ArrayList<String> authors = new ArrayList<String>();
            Cursor cursorAuthors = db.rawQuery("SELECT "+ nameAuthorDb +" FROM "+ authorNameDb + " WHERE " + isbnBookDb + " = ?", new String[]{ isbn} );
            while(cursorAuthors.moveToNext()){

                authors.add(cursorAuthors.getString(1));
            }

            Cursor cursorType = db.rawQuery("SELECT "+ nameTypeDb +" FROM "+ typeNameDb + " NATURAL JOIN "+ typeBookNameDb+" WHERE " + isbnBookDb + " = ?" , new String[]{ isbn} );
            ArrayList<String> types = new ArrayList<String>();
            while(cursorType.moveToNext()){

                types.add(cursorType.getString(1));
            }


            cursorAuthors.close();
            cursorType.close();

            bookLibrary.addBook(new SimpleBook(isbn, authors, title, collection, types, publisher, year, summary, isRead, isBorrowed, borrower, owner, comment, photo));
        }
        cursor.close();

        return bookLibrary;


    }

    private boolean intToBool(int anInt) {
        if(anInt == 0) return false;

        return true;
    }

    public SimpleBook getSimpleBook(long position) {

        long pos = position;
        Cursor cursor = db.rawQuery("SELECT * FROM "+ bookNameDb, null);

        while(cursor.moveToNext() && pos > 0){

            pos --;

        }

        String isbn = cursor.getString(1);
        String title = cursor.getString(2);
        String collection = cursor.getString(3);
        String publisher = cursor.getString(4);
        String year = cursor.getString(5);
        String summary = cursor.getString(6);
        boolean isRead = intToBool(cursor.getInt(7));
        boolean isBorrowed = intToBool(cursor.getInt(8));
        String borrower = cursor.getString(9);
        String owner = cursor.getString(10);
        String comment = cursor.getString(11);
        String photo = cursor.getString(12);

        ArrayList<String> authors = new ArrayList<String>();
        Cursor cursorAuthors = db.rawQuery("SELECT "+ nameAuthorDb +" FROM "+ authorNameDb + " WHERE " + isbnBookDb + " = ?", new String[]{ isbn} );
        while(cursorAuthors.moveToNext()){

            authors.add(cursorAuthors.getString(1));
        }

        Cursor cursorType = db.rawQuery("SELECT "+ nameTypeDb +" FROM "+ typeNameDb + " NATURAL JOIN "+ typeBookNameDb+" WHERE " + isbnBookDb + " = ?" , new String[]{ isbn} );
        ArrayList<String> types = new ArrayList<String>();
        while(cursorType.moveToNext()){

            types.add(cursorType.getString(1));
        }

        cursor.close();
        cursorAuthors.close();
        cursorType.close();

        return new SimpleBook(isbn, authors, title, collection, types, publisher, year, summary, isRead, isBorrowed, borrower, owner, comment, photo);

    }
}

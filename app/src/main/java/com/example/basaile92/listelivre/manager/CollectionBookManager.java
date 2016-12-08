package com.example.basaile92.listelivre.manager;


import android.content.Context;

import com.example.basaile92.listelivre.data.AuthorData;
import com.example.basaile92.listelivre.data.BookData;
import com.example.basaile92.listelivre.data.CollectionbookData;
import com.example.basaile92.listelivre.data.TypebookData;
import com.example.basaile92.listelivre.database.DAOBase;
import com.example.basaile92.listelivre.entity.Author;
import com.example.basaile92.listelivre.entity.AuthorList;
import com.example.basaile92.listelivre.entity.Book;
import com.example.basaile92.listelivre.entity.BookLibrary;
import com.example.basaile92.listelivre.entity.Collection;
import com.example.basaile92.listelivre.entity.Collectionbook;
import com.example.basaile92.listelivre.entity.SimpleBook;
import com.example.basaile92.listelivre.entity.TypeList;
import com.example.basaile92.listelivre.entity.Typebook;

import java.util.ArrayList;
import java.util.List;

public class CollectionBookManager extends DAOBase{


    public CollectionBookManager (Context pContext) {

        super(pContext);
    }


    public void saveCollectionBook (Collection collection, SimpleBook book) {

        String collectionName = collection.getName();
        String isbn = book.getIsbn();
        Collectionbook collectionbook = new Collectionbook(collectionName, isbn);

        CollectionbookData collectionbookData = new CollectionbookData(handler);
        collectionbookData.createCollectionbook(collectionbook);
    }

    public void deleteCollectionBook (Collection collection, SimpleBook book) {

        String collectionName = collection.getName();
        String isbn = book.getIsbn();

        CollectionbookData collectionbookData = new CollectionbookData(handler);
        collectionbookData.deleteCollectionbook(collectionName, isbn);
    }

    public List<Book> getAllBooksFromCollection (Collection collection) {

        CollectionbookData collectionbookData = new CollectionbookData(handler);
        List<Collectionbook> collectionbooks = collectionbookData.getAllCollectionbookByNamecollection(collection.getName());
        List<Book> books = new ArrayList<Book>();
        BookData bookData = new BookData(handler);

        for (Collectionbook collectionbook : collectionbooks) {
            books.add(bookData.getBookByIsbn(collectionbook.getIsbn()));
        }

        return books;
    }


    public BookLibrary getAllSimpleBooksFromCollection (Collection collection) {

        List<Book> books = getAllBooksFromCollection(collection);
        BookLibrary bookLibrary = new BookLibrary();
        AuthorList authors;
        List<Typebook> typebooks;
        AuthorData authorData = new AuthorData(handler);
        TypebookData typebookData = new TypebookData(handler);


        for (Book book : books) {
                authors = authorData.getAllAuthorByIsbn(book.getIsbn());
                typebooks = typebookData.getAllTypebookByIsbn(book.getIsbn());
                bookLibrary.add(new SimpleBook(book, authors, typebooks));
        }

        return bookLibrary;
    }
}

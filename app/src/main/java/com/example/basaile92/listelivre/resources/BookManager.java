package com.example.basaile92.listelivre.resources;

import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 * Created by basaile92 on 06/10/2016.
 */

public class BookManager {

    private File file;

    public BookManager(File file){

        this.file = file;
    }

    /**
     * Create the file of the book manager
     */
    public void createFile(){

        try {
            if (!file.exists()) {

                new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.example.basaile92.listelivre").mkdirs();
                new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.example.basaile92.listelivre/files").mkdirs();
                this.file.createNewFile();
            }
        } catch (IOException e) {
        }

    }

    /**
     * Add a book in the book save file.
     * @param book is the book that you want to add in the save file.
     */
    public void saveBook(Book book) throws BookAlreadyExistsException {
        BookLibrary bookLibrary;
        bookLibrary = (BookLibrary) readData(this.file);
        if(bookLibrary == null)
            bookLibrary = new BookLibrary();
        else if(bookLibrary.existBook(book)){

            throw new BookAlreadyExistsException();
        }
        bookLibrary.addBook(book);
        saveData(this.file, bookLibrary);
    }

    /**
     * Modify a book in the book save file.
     * @param book is the new book that you want to put in the save file.
     */
    public void modifyBook(Book book){

        BookLibrary bookLibrary;
        bookLibrary = (BookLibrary) readData(this.file);
        bookLibrary.modifyBook(book);
        saveData(this.file, bookLibrary);

    }

    /**
     * Delete a book in the book save file.
     * @param book is the book that you want to remove in the save file
     */
    public void deleteBook(Book book){

        BookLibrary bookLibrary;
        bookLibrary = (BookLibrary) this.readData(this.file);

        for(Book thisBook : bookLibrary){
            if(book.getIsbn().equals(thisBook.getIsbn())){
                bookLibrary.removeBook(thisBook);
                saveData(this.file, bookLibrary);
                break;
            }

        }
    }

    /**
     * Read the Book Library in the book save file.
     * @return the booklibrary
     */
    public BookLibrary readBookLibrary(){

        return (BookLibrary) readData(this.file);
    }

    /**
     * Used to save the data in a file.
     * @param file is the File where you want to save the data.
     * @param data is the Data that you want to save in the file.
     */
    private void saveData(File file, final Object data)
    {
            synchronized (data) {
            if(data == null) return;
            try {
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(data);
                oos.close();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Used to read the data of a file
     * @param file is the file that you want to read.
     * @return the data read in the file
     */
    private Object readData(File file){

        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object o = ois.readObject();
            ois.close();
            fis.close();
            return o;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


}

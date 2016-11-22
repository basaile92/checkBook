package com.example.basaile92.listelivre.book;

import com.example.basaile92.listelivre.entity.Author;
import com.example.basaile92.listelivre.entity.AuthorList;

import java.util.ArrayList;

/**
 * Created by Basile on 22/11/2016.
 */

public class AuthorManager {

    public static AuthorList fromString(ArrayList<String> authorNameList,  String isbn) {

        AuthorList authorList = new AuthorList();

        for(String authorName : authorNameList){

            authorList.addAuthor(new Author(authorName, isbn));
        }

        return authorList;

    }
}

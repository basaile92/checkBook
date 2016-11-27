package com.example.basaile92.listelivre.manager;

import android.content.Context;

import com.example.basaile92.listelivre.database.DAOBase;
import com.example.basaile92.listelivre.entity.Author;
import com.example.basaile92.listelivre.entity.AuthorList;

import java.util.ArrayList;

/**
 * Created by Basile on 22/11/2016.
 */

public class AuthorManager extends DAOBase{

    public AuthorManager(Context pContext) {
        super(pContext);
    }

    public static AuthorList fromString(ArrayList<String> authorNameList, String isbn) {

        AuthorList authorList = new AuthorList();

        for(String authorName : authorNameList){

            authorList.addAuthor(new Author(authorName, isbn));
        }

        return authorList;

    }

    public static ArrayList<String> toStringList(AuthorList authorList) {

        ArrayList<String> listNameList = new ArrayList<String>();

        for(Author author : authorList){

            listNameList.add(author.getName());
        }

        return listNameList;
    }
}

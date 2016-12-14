package com.example.basaile92.listelivre.manager;

import android.content.Context;

import com.example.basaile92.listelivre.database.DAOBase;
import com.example.basaile92.listelivre.entity.Author;
import com.example.basaile92.listelivre.entity.AuthorList;

import java.util.ArrayList;

/**
 * Class used to manipulate authors
 */
public class AuthorManager extends DAOBase{

    public AuthorManager(Context pContext) {
        super(pContext);
    }

    /**
     * Transform a list of string in an authorList
     * @param authorNameList : the list to transform
     * @param isbn : the Book's isbn assoicated with the authors
     * @return
     */
    public static AuthorList fromString(ArrayList<String> authorNameList, String isbn) {

        AuthorList authorList = new AuthorList();

        for(String authorName : authorNameList){

            authorList.addAuthor(new Author(authorName, isbn));
        }

        return authorList;

    }

    /**
     * Transform an AuthorList in a list of string
     * @param authorList the list to transform
     * @return a list of Author's name
     */
    public static ArrayList<String> toStringList(AuthorList authorList) {

        ArrayList<String> listNameList = new ArrayList<String>();

        for(Author author : authorList){

            listNameList.add(author.getName());
        }

        return listNameList;
    }

    /**
     * Concatenate a list of string in a string of all author's name
     * @param list list to concatenate
     * @return a string which contains all author's name
     */
    public static String StringListToString(ArrayList<String> list){

        String res = "";
        int i = 0;
        for(String s : list){

            if(i != 0){

                res += ", ";

            }

            res+=s;
            i++;
        }

        return res;


    }


    /**
     * Check if an author already exist in the list
     * @param s the author's name to compare
     * @param authorNameList the list which contains different author's names
     * @return True if the author already exist
     *          False if not
     */
    public boolean existAuthor(String s, ArrayList<String> authorNameList) {

        for(String str : authorNameList){

            if(str.equals(s)){
                return true;
            }
        }
        return false;
    }
}

package com.example.basaile92.listelivre.book;

import com.example.basaile92.listelivre.entity.Type;
import com.example.basaile92.listelivre.entity.TypeList;
import com.example.basaile92.listelivre.entity.Typebook;

import java.util.List;

/**
 * Created by Basile on 17/11/2016.
 */

public class TypeManager {


    //TODO TypeManager

    public TypeManager(){


    }

    private boolean dbExistType(String type){

        return false;
    }

    public void saveType(String type) throws TypeAlreadyExistsException{



    }

    public void deleteType(String type){


    }

    public static TypeList fromTypebookListToTypeList(List<Typebook> typebooks){

        TypeList types = new TypeList();

        for(Typebook typebook : typebooks){

            types.add(new Type(typebook.getIsbn()));
        }
        return types;
    }

}

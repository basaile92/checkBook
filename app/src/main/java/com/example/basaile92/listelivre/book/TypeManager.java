package com.example.basaile92.listelivre.book;

import android.content.Context;

import com.example.basaile92.listelivre.data.TypeData;
import com.example.basaile92.listelivre.database.DAOBase;
import com.example.basaile92.listelivre.entity.Type;
import com.example.basaile92.listelivre.entity.TypeList;
import com.example.basaile92.listelivre.entity.Typebook;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Basile on 17/11/2016.
 */

public class TypeManager extends DAOBase{


    //TODO TypeManager

    public TypeManager(Context pContext){
        super(pContext);

    }

    public TypeList readTypeList(){

        TypeData typeData = new TypeData(handler);
        TypeList typeList = typeData.getAllType();
        return typeList;
    }



    public static TypeList fromTypebookListToTypeList(List<Typebook> typebooks){

        TypeList types = new TypeList();

        for(Typebook typebook : typebooks){

            types.add(new Type(typebook.getIsbn()));
        }
        return types;
    }

    public static TypeList fromStringArray(ArrayList<String> typeNameList) {

        TypeList typeList = new TypeList();
        for(String typeName : typeNameList){

            typeList.addType(new Type(typeName));
        }

        return typeList;
    }

    public static TypeList OneListMinusAnOther(TypeList typeList, TypeList types) {

        TypeList typeListRes = new TypeList();

        for(Type type : typeList){

            if(!types.contains(type)){

                typeListRes.add(type);
            }
        }

        return typeListRes;
    }

    public static TypeList fromString(ArrayList<String> typeNameList) {


        TypeList typeList = new TypeList();

        for(String typeName : typeNameList){

            typeList.addType(new Type(typeName));
        }

        return typeList;
    }
}

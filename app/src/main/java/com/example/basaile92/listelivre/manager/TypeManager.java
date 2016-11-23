package com.example.basaile92.listelivre.manager;

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


    public void saveType(Type type){

        TypeData typeData = new TypeData(handler);
        typeData.createType(type);
    }

    public TypeList readTypeList(){

        TypeData typeData = new TypeData(handler);
        TypeList typeList = typeData.getAllType();
        return typeList;
    }

    public boolean existName(String name){

        TypeData typeData = new TypeData(handler);
        boolean res = typeData.getTypeByName(name) != null;

        return res;
    }

    public void deleteType(Type type){

        TypeData typeData = new TypeData(handler);
        typeData.deleteTypeByName(type.getName());
    }

    public Type getTypeAtPosition(int position){

        TypeData bookData = new TypeData(handler);
        List<Type> types = bookData.getAllType();
        Type type = types.get(position);

        return type;

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

    public static String[] toStringArray(TypeList typeList){

        String[] res = new String[typeList.size()];
        int i = 0;
        for(Type type: typeList){

            res[i] = type.getName();
            i++;
        }

        return res;

    }

    public static boolean[] elementsFromSecondInFirstList(TypeList typeList, ArrayList<String> typeNameList) {

        boolean[] res = new boolean[typeList.size()];
        int i = 0;

        for(Type type: typeList){

            if((typeNameList.contains(type.getName()))) {
                res[i] = true;
            }else {
                res[i] = false;
            }
            i++;
        }

        return res;
    }


    public static ArrayList<String> elementsTrueFromTwoArrays(String[] typeListString, boolean[] onCheckedItems) {

            ArrayList<String> res = new ArrayList<String>();
            for(int i = 0; i < typeListString.length; i++){

                if(onCheckedItems[i]){

                    res.add(typeListString[i]);
                }
            }

            return res;
    }

    public static ArrayList<String> toStringList(TypeList types) {

        ArrayList<String> res = new ArrayList<String>();

        for(Type type : types){

            res.add(type.getName());
        }

        return res;
    }
}

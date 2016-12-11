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
 * Class used to manipulate Book's Type
 */
public class TypeManager extends DAOBase{



    public TypeManager(Context pContext){
        super(pContext);

    }

    /**
     * Create a new type in the database
     * @param type : a new type
     */
    public void saveType(Type type){

        TypeData typeData = new TypeData(handler);
        typeData.createType(type);
    }

    /**
     * @return a TypeList which contains all types in database
     */
    public TypeList readTypeList(){

        TypeData typeData = new TypeData(handler);
        TypeList typeList = typeData.getAllType();
        return typeList;
    }

    /**
     * Check if the Type's name already exist in the database
     * @param name Type's name to verify
     * @return True if the name already exist, false if not
     */
    public boolean existName(String name){

        TypeData typeData = new TypeData(handler);
        boolean res = typeData.getTypeByName(name) != null;

        return res;
    }

    /**
     * Delete a Type from the database
     * @param type : Type to delete
     */
    public void deleteType(Type type){

        TypeData typeData = new TypeData(handler);
        typeData.deleteTypeByName(type.getName());
    }

    /**
     * @param position the position in the database
     * @return Type at position 'position'
     */
    public Type getTypeAtPosition(int position){

        TypeData bookData = new TypeData(handler);
        List<Type> types = bookData.getAllType();
        Type type = types.get(position);

        return type;

    }

    /**
     * Convert a list of TypeBook into a TypeList
     * @param typebooks : list to convert
     * @return a TypeList
     */
    public static TypeList fromTypebookListToTypeList(List<Typebook> typebooks){

        TypeList types = new TypeList();

        for(Typebook typebook : typebooks){

            types.add(new Type(typebook.getNametype()));
        }
        return types;
    }

    /**
     * Convert an array of string into a TypeList
     * @param typeNameList : array to convert
     * @return a TypeList
     */
    public static TypeList fromStringArray(ArrayList<String> typeNameList) {

        TypeList typeList = new TypeList();
        for(String typeName : typeNameList){

            typeList.addType(new Type(typeName));
        }

        return typeList;
    }

    /**
     * Convert an array of string into a TypeList
     * @param typeNameList : array to convert
     * @return a TypeList
     */
    public static TypeList fromString(ArrayList<String> typeNameList) {


        TypeList typeList = new TypeList();

        for(String typeName : typeNameList){

            typeList.addType(new Type(typeName));
        }

        return typeList;
    }

    /**
     * Convert a TypeList into a string array
     * @param typeList list to convert
     * @return a string array
     */
    public static String[] toStringArray(TypeList typeList){

        String[] res = new String[typeList.size()];
        int i = 0;
        for(Type type: typeList){

            res[i] = type.getName();
            i++;
        }

        return res;

    }

    /**
     * Verify if a list of name contains a Type already defined
     * @param typeList
     * @param typeNameList
     * @return A boolean array which contains True if the ArrayList contains a type already exist, False if not
     */
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


    /**
     * Add in an arrayList of string all Types does not already exist
     * @param typeListString
     * @param onCheckedItems
     * @return an arrayList with different type's names
     */
    public static ArrayList<String> elementsTrueFromTwoArrays(String[] typeListString, boolean[] onCheckedItems) {

            ArrayList<String> res = new ArrayList<String>();
            for(int i = 0; i < typeListString.length; i++){

                if(onCheckedItems[i]){

                    res.add(typeListString[i]);
                }
            }

            return res;
    }

    /**
     * Convert a TypeList into an ArrayList of String
     * @param types : TypeList to convert
     * @return an ArrayList which contains Type's name of the TypeList
     */
    public static ArrayList<String> toStringList(TypeList types) {

        ArrayList<String> res = new ArrayList<String>();

        for(Type type : types){

            res.add(type.getName());
        }

        return res;
    }
}

package com.example.basaile92.listelivre.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.entity.Type;
import com.example.basaile92.listelivre.manager.TypeManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeManagerActivity extends AppCompatActivity {

    ArrayList<String> typeNameList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_manager);

        EditText typesEdit = (EditText) findViewById(R.id.typesEdit);
        ImageView addTypeButton = (ImageView) findViewById(R.id.addTypeButton);

        ListView typeListView = (ListView) findViewById(R.id.typeListView);

        TypeManager typeManager = new TypeManager(TypeManagerActivity.this);

        typeNameList = new ArrayList<String>();
        typeNameList.addAll(TypeManager.toStringList(typeManager.readTypeList()));


        setAddTypeButton(typesEdit, addTypeButton, typeManager, typeListView, TypeManagerActivity.this);
        setTypeListView(typeListView, typeManager, TypeManagerActivity.this);
    }

    //Set add type Button
    private void setAddTypeButton(final EditText typesEdit, ImageView addTypeButton, final TypeManager typeManager, final ListView typeListView, final Context context) {

        addTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String typeName = typesEdit.getText().toString();

                if(!typeName.equals("")){

                    //if the type name doesn't already exist, save it in database and add it to the type list.
                    if(!typeManager.existName(typeName)) {

                        typeManager.saveType(new Type(typeName));
                        typeNameList.add(typeName);
                        setTypeListView(typeListView, typeManager, context);
                        typesEdit.setText("");

                    }else
                    {

                        Toast toast=Toast.makeText(TypeManagerActivity.this,R.string.typeAlreadyExist,Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }else{

                    Toast toast=Toast.makeText(TypeManagerActivity.this,R.string.toastEmptyField,Toast.LENGTH_SHORT);
                    toast.show();
                }


            }
        });
    }

    //set the Type List View
    private void setTypeListView(final ListView typeListView, final TypeManager typeManager, final Context context){

        List<Map<String,String>> listOfType = new ArrayList<Map<String, String>>();

        for(String typeName : typeNameList){

            Map<String, String> typeMap = new HashMap<String, String>();
            typeMap.put("name", typeName);
            listOfType.add(typeMap);
        }
        SimpleAdapter listAdapter = new SimpleAdapter(context, listOfType, R.layout.type, new String[]{"name"}, new int[]{R.id.typeText});
        typeListView.setAdapter(listAdapter);

        typeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage(R.string.deleteQuestion);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        typeManager.deleteType(typeManager.getTypeAtPosition(position));
                        typeNameList.remove(position);
                        setTypeListView(typeListView, typeManager, context);
                    }
                });

                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.cancel();
                    }
                });

                builder.create();
                builder.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}

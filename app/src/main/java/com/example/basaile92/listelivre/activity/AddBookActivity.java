package com.example.basaile92.listelivre.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.entity.AuthorList;
import com.example.basaile92.listelivre.entity.SimpleBook;
import com.example.basaile92.listelivre.entity.TypeList;
import com.example.basaile92.listelivre.manager.ImageManager;
import com.example.basaile92.listelivre.manager.AuthorManager;
import com.example.basaile92.listelivre.manager.BookManager;
import com.example.basaile92.listelivre.manager.TypeManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import rebus.permissionutils.FullCallback;
import rebus.permissionutils.PermissionEnum;
import rebus.permissionutils.PermissionManager;
import rebus.permissionutils.PermissionUtils;

public class AddBookActivity extends AppCompatActivity {

    //AuthorNameList will keep all author name which is added and will manage all of it while the send button is not pushed
    private ArrayList<String> authorNameList = new ArrayList<String>();
    //TypeNameList will keep all type name which is added and will manage all of it while the send button is not pushed
    private ArrayList<String> typeNameList = new ArrayList<String>();
    static final int REQUEST_IMAGE_CAPTURE = 1;
    //The path of the photo of book.
    private String mCurrentPhotoPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_library);
        setTitle(R.string.addBookLibraryActivity);

        final EditText isbnEdit = (EditText) findViewById(R.id.isbnEdit);
        final EditText titleEdit = (EditText) findViewById(R.id.titleEdit);
        final EditText publisherEdit = (EditText) findViewById(R.id.publisherEdit);
        final EditText yearEdit = (EditText) findViewById(R.id.yearEdit);
        final EditText summaryEdit = (EditText) findViewById(R.id.summaryEdit);
        final CheckBox isReadCheckBox = (CheckBox) findViewById(R.id.isReadCheckBox);
        final CheckBox isBorrowedCheckBox = (CheckBox) findViewById(R.id.isBorrowedCheckBox);
        final EditText commentsEdit = (EditText) findViewById(R.id.commentsEdit);
        final ImageView imageButton = (ImageView) findViewById(R.id.imageButton);
        final ImageView sendButton = (ImageView) findViewById(R.id.sendButton);

        final TextView addAuthorsText = (TextView) findViewById(R.id.addAuthorsText);
        final ImageView editAuthorsButton = (ImageView) findViewById(R.id.editAuthorsButton);
        final EditText addAuthorsEdit = (EditText) findViewById(R.id.addAuthorsEdit);
        final ImageView addAuthorsButton = (ImageView) findViewById(R.id.addAuthorsButton);
        addAuthorsText.setText("");

        final AuthorList authorsList = new AuthorList();

        final TypeList typesList = new TypeList();
        TextView typesText = (TextView) findViewById(R.id.typesText);
        ImageView addTypesButton = (ImageView) findViewById(R.id.addTypesButton);

        setPhotoButton(imageButton);
        setAuthorsManager(addAuthorsText, editAuthorsButton, addAuthorsEdit, addAuthorsButton, AddBookActivity.this);
        setTypeListView(typesText, addTypesButton, AddBookActivity.this);

        sendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // We get from the Author Name String and the ISBN the Author List
                authorsList.addAll(AuthorManager.fromString( authorNameList , isbnEdit.getText().toString()));
                typesList.addAll(TypeManager.fromString(typeNameList));
                // If the form is correctly fill
                if(checkForm(isbnEdit, titleEdit, authorsList, view.getContext())) {

                    String url = "";

                    // If a picture was taken
                    if(!mCurrentPhotoPath.equals("")){

                        //We save the rotated picture as a file and keep the URL
                            Bitmap bitmap = ImageManager.getRotateBitmap(BitmapFactory.decodeFile(mCurrentPhotoPath));
                            url = ImageManager.saveBitmap(view.getContext(),bitmap);
                    }

                    // We save the book in the Database
                    BookManager bookManager = new BookManager(view.getContext());
                    bookManager.saveSimpleBook(new SimpleBook(isbnEdit.getText().toString(), authorsList, titleEdit.getText().toString(), typesList, publisherEdit.getText().toString(), yearEdit.getText().toString(), summaryEdit.getText().toString(), isReadCheckBox.isChecked(), isBorrowedCheckBox.isChecked(),  commentsEdit.getText().toString(), url));
                    Intent intent = new Intent(AddBookActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    //We set in this function the role of all type views
    private void setTypeListView(final TextView typesText, final ImageView addTypesButton, final Context context) {

        final TypeManager typeManager = new TypeManager(context);

        // We set the function of the addTypesButton
        addTypesButton.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {

                //We get in a list all existing types
                TypeList typeList = typeManager.readTypeList();
                //We get in a boolean array, for the first list, true for the one which are in the second one and false if not.
                final boolean[] onCheckedItems = TypeManager.elementsFromSecondInFirstList(typeList, typeNameList);
                final String[] typeListString = TypeManager.toStringArray(typeList);
                // We put in a list of string all element which has the corresponding boolean true in the onCheckedItems. It will be the list of
                final ArrayList<String> typeArrayStringDisplayed = TypeManager.elementsTrueFromTwoArrays(typeListString, onCheckedItems );
                int editButtonString = R.string.editTypes;
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                //If there is no type inside the list
                if(!(typeList.size() > 0)){

                    builder.setMessage(R.string.emptyTypeList);
                    editButtonString = R.string.createNewType;
                }

                builder.setTitle(R.string.pickSomeTypes);
                //if a checkbox is checked, the corresponding element will be in the final list, if it isn't it won't be in the final list
                builder.setMultiChoiceItems( typeListString , onCheckedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {

                        if(b){

                            typeArrayStringDisplayed.add(typeListString[i]);
                        }else
                        {

                            typeArrayStringDisplayed.remove(typeListString[i]);
                        }

                    }
                });

                //if somebody push the positive button it will create a new list with all new types and set it
                builder.setPositiveButton(R.string.validate, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        typeNameList= new ArrayList<String>();
                        typeNameList.addAll(typeArrayStringDisplayed);
                        typesText.setText((TypeManager.fromStringArray(typeNameList)).toString());
                    }
                });

                //if somebody push the negative button it will go to the edit type menu
                builder.setNegativeButton(editButtonString, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(AddBookActivity.this, TypeManagerActivity.class);
                        startActivity(intent);
                    }
                });

                //We create and show the alert builder
                builder.create();
                builder.show();

            }
        });
    }


    private void setAuthorsManager(final TextView addAuthorsText, final ImageView editAuthorsButton, final EditText addAuthorsEdit, ImageView addAuthorsButton, final Context context) {

        addAuthorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // If the field addAuthorEdit is not empty
                if(!addAuthorsEdit.getText().toString().equals("")) {

                    // We will add in the authorNameList (which is created at the beginning of the activity all the author name that will be added)
                    authorNameList.add(addAuthorsEdit.getText().toString());
                    updateAuthorsTextView(addAuthorsText, context);
                    //We empty the addAuthorsEdit field
                    addAuthorsEdit.setText("");
                }
            }
        });

        //If the edit authorButton is pushed
        editAuthorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ArrayList<String> toDelete = new ArrayList<String>();
                // we get an Object[] array with all author name
                final Object[] objectArray = authorNameList.toArray();
                String[] authorListString = new String[objectArray.length];
                int i = 0;
                // we parse all of these authors (Object) in String
                for(Object object : objectArray){

                    authorListString[i] = (String) object;
                    i++;
                }
                //We create a builder with the list of each authors
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.deleteAuthors);
                builder.setMultiChoiceItems(authorListString, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {

                        //if the checkbox is checked, we keep the author in the deletelist, if not we remove it.
                        if(b){

                            toDelete.add(authorNameList.get(i));

                        }else{

                            toDelete.remove(authorNameList.get(i));

                        }
                    }
                });

                //If the positive button is pushed we delete all element which are in deleteList in the authorNameList.
                builder.setPositiveButton(R.string.validate, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        for(String s: toDelete){

                            authorNameList.remove(s);
                        }
                        updateAuthorsTextView(addAuthorsText, context);



                    }
                });

                // the alert builder is created and showed.
                builder.create();
                builder.show();
            }
        });
    }

    //To update the list view
    private void updateAuthorsTextView(final TextView addAuthorsText, final Context context){


        // We set the list of authors in a string
        addAuthorsText.setText(AuthorManager.StringListToString(authorNameList));

    }



    private void setPhotoButton(final ImageView imageButton) {

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

                    File photoFile = null;

                    try {
                        // We create a temporary image file to keep the photo
                        photoFile = ImageManager.createImageFile(view.getContext());
                        // put his path in mCurrentPhotoPath
                        mCurrentPhotoPath = photoFile.getAbsolutePath();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if(photoFile != null){

                        Uri photoURI = FileProvider.getUriForFile(view.getContext() ,"com.example.android.fileprovider", photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        ArrayList<PermissionEnum> permissionEnumArrayList = new ArrayList<>();
                        //We ask the Camera Permission before going to the Camera activity
                        permissionEnumArrayList.add(PermissionEnum.CAMERA);
                        PermissionManager.with(AddBookActivity.this).permissions(permissionEnumArrayList).callback(new FullCallback() {
                            @Override
                            public void result(ArrayList<PermissionEnum> permissionsGranted, ArrayList<PermissionEnum> permissionsDenied, ArrayList<PermissionEnum> permissionsDeniedForever, ArrayList<PermissionEnum> permissionsAsked) {
                                if(PermissionUtils.isGranted(AddBookActivity.this, PermissionEnum.CAMERA)) {
                                    //If it is good we start the camera activity
                                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                                }else{

                                    //If it is not good a Toas error will be displayed
                                    Toast toast = Toast.makeText(AddBookActivity.this, R.string.noPermission, Toast.LENGTH_SHORT);
                                    toast.show();
                                }

                            }
                        }).ask();

                    }
                }

            }
        });

    }


    //Check the different field of the form
    private boolean checkForm(EditText isbnEdit, EditText titleEdit, AuthorList authorsList, Context context){

        // We check that the main fields has a value.
        if(isbnEdit.getText().toString().equals("")||authorsList.size()==0||titleEdit.getText().toString().equals("")){

            // Error Toast if it hasn't
            Toast toast=Toast.makeText(AddBookActivity.this,R.string.toastEmptyField,Toast.LENGTH_SHORT);
            toast.show();
        }else{

            //We check that the ISBN has 13 characters.
            if(isbnEdit.getText().toString().length()!=13){

                //Error Toast if it hasn't
                Toast toast=Toast.makeText(AddBookActivity.this,R.string.isbnNotGoodFormat,Toast.LENGTH_SHORT);
                toast.show();
            }else{

                //We check that the ISBN doesn't already exist in database
                BookManager bookManager = new BookManager(context);
                if(!bookManager.existIsbn(isbnEdit.getText().toString())){

                    return true;
                }else{

                    //Error Toast if it does
                    Toast toast = Toast.makeText(AddBookActivity.this, R.string.bookAlreadyExist, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handleResult(requestCode, permissions, grantResults);
    }

    //When we take a photo, set the photo in the temporary file.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        ImageView imageButton = (ImageView) findViewById(R.id.imageButton);
        imageButton.setImageBitmap(ImageManager.getRotateBitmap(BitmapFactory.decodeFile(mCurrentPhotoPath)));
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(AddBookActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}

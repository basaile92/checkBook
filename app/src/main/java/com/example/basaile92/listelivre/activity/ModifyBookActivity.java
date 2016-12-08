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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.entity.AuthorList;
import com.example.basaile92.listelivre.entity.SimpleBook;
import com.example.basaile92.listelivre.entity.TypeList;
import com.example.basaile92.listelivre.fragment.BookLibraryFragment;
import com.example.basaile92.listelivre.fragment.DisplayBookFragment;
import com.example.basaile92.listelivre.manager.AuthorManager;
import com.example.basaile92.listelivre.manager.BookManager;
import com.example.basaile92.listelivre.manager.ImageManager;
import com.example.basaile92.listelivre.manager.TypeManager;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rebus.permissionutils.FullCallback;
import rebus.permissionutils.PermissionEnum;
import rebus.permissionutils.PermissionManager;
import rebus.permissionutils.PermissionUtils;

public class ModifyBookActivity extends AppCompatActivity {

    private ArrayList<String> authorNameList = new ArrayList<String>();
    private ArrayList<String> typeNameList = new ArrayList<String>();
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String mCurrentPhotoPath = "";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_book);
        setTitle(R.string.modifyBookLibraryActivity);

        final EditText isbnEdit = (EditText) findViewById(R.id.isbnEdit);
        final EditText titleEdit = (EditText) findViewById(R.id.titleEdit);
        final EditText collectionEdit = (EditText) findViewById(R.id.collectionEdit);
        final EditText publisherEdit = (EditText) findViewById(R.id.publisherEdit);
        final EditText yearEdit = (EditText) findViewById(R.id.yearEdit);
        final EditText summaryEdit = (EditText) findViewById(R.id.summaryEdit);
        final CheckBox isReadCheckBox = (CheckBox) findViewById(R.id.isReadCheckBox);
        final CheckBox isBorrowedCheckBox = (CheckBox) findViewById(R.id.isBorrowedCheckBox);
        final EditText borrowerEdit = (EditText) findViewById(R.id.borrowerEdit);
        final EditText ownerEdit = (EditText) findViewById(R.id.ownerEdit);
        final EditText commentsEdit = (EditText) findViewById(R.id.commentsEdit);
        final CircularImageView imageButton = (CircularImageView) findViewById(R.id.imageButton);
        final Button modifyButton = (Button) findViewById(R.id.modifyButton);

        final ListView authorsListView = (ListView) findViewById(R.id.authorsListView);
        final EditText addAuthorsEdit = (EditText) findViewById(R.id.addAuthorsEdit);
        final ImageView addAuthorsButton = (ImageView) findViewById(R.id.addAuthorsButton);

        final AuthorList authorsList = new AuthorList();

        final TypeList typesList = new TypeList();
        TextView typesText = (TextView) findViewById(R.id.typesText);
        ImageView addTypesButton = (ImageView) findViewById(R.id.addTypesButton);


        //we get the simple book with the Intent
        Intent intent = getIntent();
        final SimpleBook book = (SimpleBook) intent.getSerializableExtra(DisplayBookFragment.SIMPLEBOOK);

        //if book is not null we assignate all field with the book information
        if(book != null) {

            isbnEdit.setText(book.getIsbn());
            titleEdit.setText(book.getTitle());
            collectionEdit.setText(book.getCollection());
            publisherEdit.setText(book.getPublisher());
            yearEdit.setText(book.getYear());
            summaryEdit.setText(book.getSummary());
            isReadCheckBox.setChecked(book.isRead());
            isBorrowedCheckBox.setChecked(book.isBorrowed());
            borrowerEdit.setText(book.getBorrower());
            ownerEdit.setText(book.getOwner());
            commentsEdit.setText(book.getComment());

            authorsList.addAll(book.getAuthors());
            authorNameList = AuthorManager.toStringList(authorsList);

            typesList.addAll(book.getTypes());
            typeNameList = TypeManager.toStringList(typesList);

            if(!book.getPhoto().equals("")) {
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(book.getPhoto(), bmOptions);
                imageButton.setImageBitmap(bitmap);
            }
        }

        setBorrowingFieldDisplay(isBorrowedCheckBox, borrowerEdit);
        setPhotoButton(imageButton);
        setAuthorsListView(authorsListView, addAuthorsEdit, addAuthorsButton, ModifyBookActivity.this);
        setTypeListView(typesText, addTypesButton, ModifyBookActivity.this);

        modifyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // If the form is correctly fill
                if(checkForm(isbnEdit, titleEdit,authorsList, view.getContext())) {

                    // We save the book in the Database
                    BookManager bookManager = new BookManager(view.getContext());
                    bookManager.modifyBook(book , new SimpleBook(isbnEdit.getText().toString(), authorsList, titleEdit.getText().toString(), collectionEdit.getText().toString(), typesList, publisherEdit.getText().toString(), yearEdit.getText().toString(), summaryEdit.getText().toString(), isReadCheckBox.isChecked(), isBorrowedCheckBox.isChecked(), borrowerEdit.getText().toString(), ownerEdit.getText().toString(), commentsEdit.getText().toString(), ""));
                    Intent intent = new Intent(ModifyBookActivity.this, DisplayBookActivity.class);
                    int itemId = getIntent().getIntExtra(BookLibraryFragment.POSITION, -1);
                    if(itemId != -1)
                        intent.putExtra(BookLibraryFragment.POSITION, itemId);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }


    private void setTypeListView(final TextView typesText, ImageView addTypesButton, final Context context) {
        final TypeManager typeManager = new TypeManager(context);

        typesText.setText((TypeManager.fromStringArray(typeNameList)).toString());
        addTypesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TypeList typeList = typeManager.readTypeList();
                final boolean[] onCheckedItems = TypeManager.elementsFromSecondInFirstList(typeList, typeNameList);
                final String[] typeListString = TypeManager.toStringArray(typeList);
                final ArrayList<String> typeArrayStringDisplayed = TypeManager.elementsTrueFromTwoArrays(typeListString, onCheckedItems );
                int editButtonString = R.string.editTypes;
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                if(!(typeList.size() > 0)){

                    builder.setMessage(R.string.emptyTypeList);
                    editButtonString = R.string.createNewType;
                }

                builder.setTitle(R.string.pickSomeTypes);

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

                builder.setPositiveButton(R.string.validate, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        typeNameList= new ArrayList<String>();
                        typeNameList.addAll(typeArrayStringDisplayed);
                        typesText.setText((TypeManager.fromStringArray(typeNameList)).toString());
                    }
                });

                builder.setNegativeButton(editButtonString, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(ModifyBookActivity.this, TypeManagerActivity.class);
                        startActivity(intent);
                        //TODO recharger la multiple choice items with new items

                    }
                });

                builder.create();
                builder.show();

            }
        });


    }

    private void setAuthorsListView(final ListView authorsListView, final EditText addAuthorsEdit, ImageView addAuthorsButton, final Context context) {

        updateAuthorsListView(authorsListView, context);
        addAuthorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // If the field addAuthorEdit is not empty
                if(!addAuthorsEdit.getText().toString().equals("")) {

                    // We will add in the authorNameList (which is created at the beginning of the activity all the author name that will be added)
                    authorNameList.add(addAuthorsEdit.getText().toString());
                    updateAuthorsListView(authorsListView, context);
                    //We empty the addAuthorsEdit field
                    addAuthorsEdit.setText("");
                }
            }
        });
    }

    //To update the list view
    private void updateAuthorsListView(final ListView authorsListView, final Context context){

        // We display the list view
        List<Map<String, String>> listOfAuthor = new ArrayList<Map<String, String>>();

        for(String authorName: authorNameList) {

            Map<String, String> authorMap = new HashMap<String, String>();
            authorMap.put("name", authorName);
            listOfAuthor.add(authorMap);
        }
        SimpleAdapter listAdapter = new SimpleAdapter(context, listOfAuthor, R.layout.author, new String[]{"name"}, new int[]{R.id.authorText});
        authorsListView.setAdapter(listAdapter);

        // We assignate for each item the delete button and his function
        authorsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                ImageView authorDeleteButton = (ImageView) findViewById(R.id.authorDeleteButton);

                authorNameList.remove(i);
                updateAuthorsListView(authorsListView, context);

            }
        });
    }

    private void setPhotoButton(final CircularImageView imageButton) {


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

                    File photoFile = null;

                    try {
                        photoFile = ImageManager.createImageFile(view.getContext());
                        mCurrentPhotoPath = photoFile.getAbsolutePath();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if(photoFile != null){

                        Uri photoURI = FileProvider.getUriForFile(view.getContext() ,"com.example.android.fileprovider", photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        ArrayList<PermissionEnum> permissionEnumArrayList = new ArrayList<>();
                        permissionEnumArrayList.add(PermissionEnum.CAMERA);
                        PermissionManager.with(ModifyBookActivity.this).permissions(permissionEnumArrayList).callback(new FullCallback() {
                            @Override
                            public void result(ArrayList<PermissionEnum> permissionsGranted, ArrayList<PermissionEnum> permissionsDenied, ArrayList<PermissionEnum> permissionsDeniedForever, ArrayList<PermissionEnum> permissionsAsked) {
                                if(PermissionUtils.isGranted(ModifyBookActivity.this, PermissionEnum.CAMERA)) {
                                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                                }else{

                                    Toast toast = Toast.makeText(ModifyBookActivity.this, R.string.noPermission, Toast.LENGTH_SHORT);
                                    toast.show();
                                }

                            }
                        }).ask();

                    }
                }

            }
        });


    }


    //To make visible the borrowerEdit when the isBorrowed checkbox is checked or not
    private void setBorrowingFieldDisplay(CheckBox isBorrowedCheckBox, final EditText borrowerEdit){

        isBorrowedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    borrowerEdit.setVisibility(View.VISIBLE);
                }else{
                    borrowerEdit.setVisibility(View.INVISIBLE);
                }

            }
        });
    }

    //Check the different field of the form
    private boolean checkForm(EditText isbnEdit, EditText titleEdit, AuthorList authorsList, Context context){

        // We check that the main fields has a value.
        if(isbnEdit.getText().toString().equals("")||authorsList.size()==0||titleEdit.getText().toString().equals("")){

            // Error Toast if it hasn't
            Toast toast=Toast.makeText(ModifyBookActivity.this,R.string.toastEmptyField,Toast.LENGTH_SHORT);
            toast.show();
        }else{

            //We check that the ISBN has 13 characters.
            if(isbnEdit.getText().toString().length()!=13){

                //Error Toast if it hasn't
                Toast toast=Toast.makeText(ModifyBookActivity.this,R.string.isbnNotGoodFormat,Toast.LENGTH_SHORT);
                toast.show();
            }else{

                //We check that the ISBN doesn't alread exist in database
                BookManager bookManager = new BookManager(context);
                if(bookManager.existIsbn(isbnEdit.getText().toString())){

                    return true;
                }else{

                    //Error Toast if it does
                    Toast toast = Toast.makeText(ModifyBookActivity.this, R.string.bookAlreadyExist, Toast.LENGTH_SHORT);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        CircularImageView imageButton = (CircularImageView) findViewById(R.id.imageButton);
        imageButton.setImageURI(Uri.fromFile(new File(mCurrentPhotoPath)));
    }

    //When we push the back button, come back to the main activity
    public void onBackPressed()
    {
        Intent intent = new Intent(ModifyBookActivity.this, DisplayBookActivity.class);
        int itemId = getIntent().getIntExtra(BookLibraryFragment.POSITION, -1);
        if(itemId != -1)
            intent.putExtra(BookLibraryFragment.POSITION, itemId);
        startActivity(intent);
        finish();
    }
}


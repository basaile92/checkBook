package com.example.basaile92.listelivre.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.book.BookAlreadyExistsException;
import com.example.basaile92.listelivre.book.BookLibrary;
import com.example.basaile92.listelivre.book.BookManager;
import com.example.basaile92.listelivre.book.ScanBook;
import com.example.basaile92.listelivre.book.SimpleBook;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ModifyBookLibraryFragment extends Fragment {


    static final int REQUEST_TAKE_PHOTO = 1;

    public static final String POSITION = "itemPosition";

    protected BookLibraryFragmentCallBack mCallback;
    private BookManager bookManager;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_modify_book_library, container, false);


        initLabel(view, getActivity());

        Bundle bundle = getArguments();
        int position;

        if (bundle != null) {
            position = bundle.getInt(POSITION);
        } else{
            position = 0;
        }


        updateView(position, view, view);

        return view;
    }

    public void updateView(final int position, View viewModif, View viewLibrary) {


        Button modifyButton = (Button) viewModif.findViewById(R.id.modifyButton);

        final EditText isbnEdit = (EditText) viewModif.findViewById(R.id.isbnEdit);
        final EditText titleEdit = (EditText) viewModif.findViewById(R.id.titleEdit);
        final ArrayList<String> authors = new ArrayList<String>();
        final ArrayList<String> types = new ArrayList<String>();
        final EditText collectionEdit = (EditText) viewModif.findViewById(R.id.collectionEdit);
        final EditText publisherEdit = (EditText) viewModif.findViewById(R.id.publisherEdit);
        final EditText yearEdit = (EditText) viewModif.findViewById(R.id.yearEdit);
        final EditText summaryEdit = (EditText) viewModif.findViewById(R.id.summaryEdit);
        final CheckBox isReadCheckBox = (CheckBox) viewModif.findViewById(R.id.isReadCheckBox);
        final CheckBox isBorrowedCheckBox = (CheckBox) viewModif.findViewById(R.id.isBorrowedCheckBox);
        final EditText borrowerEdit = (EditText) viewModif.findViewById(R.id.borrowerEdit);
        final EditText ownerEdit = (EditText) viewModif.findViewById(R.id.ownerEdit);
        final EditText commentsEdit = (EditText) viewModif.findViewById(R.id.commentsEdit);
        final ImageView imageButton = (ImageView) viewModif.findViewById(R.id.imageButton);




        bookManager = new BookManager(viewLibrary.getContext());

        SimpleBook book = bookManager.getSimpleBook(position);

        isbnEdit.setText(book.getIsbn());
        authors.addAll(book.getAuthors());
        types.addAll(book.getTypes());
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
        if(!book.getPhoto().equals("")) {
            File photoFile = new File(book.getPhoto());
            imageButton.setImageURI(FileProvider.getUriForFile(viewLibrary.getContext(), "com.example.android.fileprovider", photoFile));
        }

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

                }
        }});


        modifyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (isbnEdit.getText().toString().equals("") || authors.size() == 0 || titleEdit.getText().toString().equals("")) {

                    Toast toast = Toast.makeText(getActivity(), R.string.toastEmptyField, Toast.LENGTH_SHORT);
                    toast.show();

                } else {


                    if (isbnEdit.getText().toString().length() != 13) {

                        Toast toast = Toast.makeText(getActivity(), R.string.isbnNotGoodFormat, Toast.LENGTH_SHORT);
                        toast.show();


                    } else {
                        Intent takePictureIntent = new Intent((MediaStore.ACTION_IMAGE_CAPTURE));
                        String mCurrentPhotoPath="";
                        if(takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null){

                            File photoFile = null;
                            try {

                                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                                String imageFileName = "JPEG_" + timeStamp + "_";
                                File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                                File image = File.createTempFile(imageFileName,".jpg", storageDir);
                                mCurrentPhotoPath = image.getAbsolutePath();
                                photoFile = image;

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if(photoFile != null){

                                // TODO Supprimer l'ancienne photo du livre.

                                Uri photoURI = FileProvider.getUriForFile(view.getContext(), "com.example.android.fileprovider", photoFile);
                                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                            }

                        }

                        try {
                            bookManager.modifyBook(new SimpleBook(isbnEdit.getText().toString(), authors, titleEdit.getText().toString(), collectionEdit.getText().toString(), types, publisherEdit.getText().toString(), yearEdit.getText().toString(), summaryEdit.getText().toString(), isReadCheckBox.isChecked(), isBorrowedCheckBox.isChecked(), borrowerEdit.getText().toString(), ownerEdit.getText().toString(), commentsEdit.getText().toString(), mCurrentPhotoPath), position);

                            mCallback.updateBookLibraryFragment(view);

                        } catch (BookAlreadyExistsException e) {

                            Toast toast = Toast.makeText(getActivity(), R.string.bookAlreadyExist, Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                }
            }
        });

    }



    private void initLabel(View view, Activity thisActivity) {
        thisActivity.setTitle(R.string.modifyBookLibraryActivity);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == getActivity().RESULT_OK) {
            ImageView imageButton = (ImageView) getActivity().findViewById(R.id.imageButton);
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageButton.setImageBitmap(imageBitmap);
        }
    }

    @Override
    public void onAttach(Context context){

        super.onAttach(context);

        try{

            mCallback = (BookLibraryFragmentCallBack) context;
            Log.e("OnAttach", context.toString());

        }catch (ClassCastException e){

            throw new ClassCastException(context.toString() + " must implement BookLibraryFragmentCallBack");
        }
    }

}

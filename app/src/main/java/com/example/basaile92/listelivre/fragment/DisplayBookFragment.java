package com.example.basaile92.listelivre.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.activity.MainActivity;
import com.example.basaile92.listelivre.activity.ModifyBookActivity;
import com.example.basaile92.listelivre.callback.BookLibraryFragmentCallBack;
import com.example.basaile92.listelivre.entity.SimpleBook;
import com.example.basaile92.listelivre.manager.BookManager;
import com.example.basaile92.listelivre.manager.ImageManager;

import org.w3c.dom.Text;

import java.io.IOException;


/**
 * Created by Basile on 20/11/2016.
 */
public class DisplayBookFragment extends Fragment{

    private BookLibraryFragmentCallBack mCallback;
    public static String SIMPLEBOOK = "SimpleBook";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle b) {
        View view = inflater.inflate(R.layout.fragment_display_book, container, false);

        //We get the position that was sent by the bundle
        Bundle bundle = getArguments();
        int position;

        if (bundle != null) {
            position = bundle.getInt(BookLibraryFragment.POSITION);
        } else{
            position = -1;
        }

        if(position != -1)
            updateView(position, view, view);


        return view;
    }

    //This function is used to be able to change the view from an other activity
    public void updateView(int position, View viewModif, View viewLibrary){

        TextView titleText = (TextView) viewModif.findViewById(R.id.titleText);
        TextView authorText = (TextView) viewModif.findViewById(R.id.authorText);
        TextView typeText = (TextView) viewModif.findViewById(R.id.typeText);
        TextView publisherText = (TextView) viewModif.findViewById(R.id.publisherText);
        TextView yearText = (TextView) viewModif.findViewById(R.id.yearText);
        TextView summaryText = (TextView) viewModif.findViewById(R.id.summaryText);
        TextView commentsText = (TextView) viewModif.findViewById(R.id.commentsText);
        ImageView imageButton = (ImageView) viewModif.findViewById(R.id.imageButton);

        LinearLayout isReadLayout = (LinearLayout) viewModif.findViewById(R.id.isReadLayout);
        LinearLayout isBorrowedLayout = (LinearLayout) viewModif.findViewById(R.id.isBorrowedLayout);

        ImageView editButton = (ImageView) viewModif.findViewById(R.id.editButton);
        ImageView deleteButton = (ImageView) viewModif.findViewById(R.id.deleteButton);

        //We get the informations of the Simple Book
        BookManager bookManager = new BookManager(viewModif.getContext());
        SimpleBook book = bookManager.getSimpleBookAtPosition(position);

        //We set all field of the view with the informations of the book
        titleText.setText(book.getTitle());
        authorText.setText(book.getAuthors().toString());
        typeText.setText(book.getTypes().toString());
        publisherText.setText(book.getPublisher());
        yearText.setText(book.getYear());
        summaryText.setText(book.getSummary());
        commentsText.setText(book.getComment());

        setImageButton(imageButton, book.getPhoto());

        setStampVisibility(isBorrowedLayout, isReadLayout, book.isBorrowed(),book.isRead());

        setButtonEdit(editButton, position);
        setButtonDelete(deleteButton, position);

    }


    private void setStampVisibility(LinearLayout isBorrowedLayout, LinearLayout isReadLayout, boolean isBorrowed, boolean isRead) {

        if(isBorrowed){

            isBorrowedLayout.setVisibility(View.VISIBLE);
        }else{

            isBorrowedLayout.setVisibility(View.INVISIBLE);
        }

        if(isRead){

            isReadLayout.setVisibility(View.VISIBLE);
        }else{

            isReadLayout.setVisibility(View.INVISIBLE);
        }

    }

    private void setButtonEdit(ImageView editButton, final int position) {

        //We assignate the function of the editButton
        editButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //We send the book at the position in the intent to the modify book activity
                Intent intent = new Intent( getContext(), ModifyBookActivity.class);
                BookManager bookManager = new BookManager(view.getContext());
                SimpleBook book = bookManager.getSimpleBookAtPosition(position);
                intent.putExtra(BookLibraryFragment.POSITION, position);
                intent.putExtra(SIMPLEBOOK, book);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    private void setButtonDelete(ImageView deleteButton, final int position){

        //We assignate the function of the deleteButton
        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {

                //We ask the confirmation of the user
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.deleteQuestion);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //if yes we come back to the main activity.
                        BookManager bookManager = new BookManager(view.getContext());
                        String pathToDel = bookManager.getSimpleBookAtPosition(position).getPhoto();

                        try {
                            ImageManager.deletePhoto(pathToDel);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        bookManager.deleteBook(bookManager.getSimpleBookAtPosition(position));

                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });

                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override

                    // if no we just cancel the pop up
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    private void setImageButton(ImageView imageButton, String path){

        if(!path.equals("")) {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);
            imageButton.setImageBitmap(bitmap);
        }

    }

    @Override
    public void onAttach(Context context){

        super.onAttach(context);

        try{

            mCallback = (BookLibraryFragmentCallBack) context;

        }catch (ClassCastException e){

            throw new ClassCastException(context.toString() + " must implement BookLibraryFragmentCallBack");
        }
    }

}

package com.example.basaile92.listelivre.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.callback.BookLibraryFragmentCallBack;

/**
 * Created by Basile on 26/11/2016.
 /* The activity that creates an instance of this dialog fragment must
 * implement this interface in order to receive event callbacks.
 * Each method passes the DialogFragment in case the host needs to query it. */

public class AddBookByIsbnDialogFragment extends DialogFragment {

    // Use this instance of the interface to deliver action events
    BookLibraryFragmentCallBack mListener;
    EditText isbnEditForm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        return inflater.inflate(R.layout.addbookformisbn, container, false);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.addBookByIsbnText);
        final View view = getActivity().getLayoutInflater().inflate(R.layout.addbookformisbn, null);
        builder.setView(view);
        builder.setPositiveButton(R.string.findBook, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                String isbnEditForm = ((EditText) view.findViewById(R.id.isbnEditForm)).getText().toString();
                mListener.onDialogPositiveClick(AddBookByIsbnDialogFragment.this, isbnEditForm);
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                mListener.onDialogNegativeClick(AddBookByIsbnDialogFragment.this);
            }
        });

        return builder.create();

    }

    public EditText getIsbnEditForm(){

        return this.isbnEditForm;
    }


    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (BookLibraryFragmentCallBack) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement BookLibraryFragmentCallBack");
        }
    }
}


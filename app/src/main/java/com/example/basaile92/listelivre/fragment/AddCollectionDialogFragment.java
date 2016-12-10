package com.example.basaile92.listelivre.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.callback.AddCollectionDialogFragmentCallBack;

/**
 * Created by merciert on 10/12/2016.
 */

public class AddCollectionDialogFragment extends DialogFragment {

    AddCollectionDialogFragmentCallBack myListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.add_collection_form, container, false);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.addCollection);
        final View view = getActivity().getLayoutInflater().inflate(R.layout.add_collection_form, null);
        builder.setView(view);
        builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                String nameEditForm = ((EditText) view.findViewById(R.id.addCollectionForm)).getText().toString();
                myListener.onDialogPositiveClick(AddCollectionDialogFragment.this, nameEditForm);
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                myListener.onDialogNegativeClick(AddCollectionDialogFragment.this);
            }
        });

        return builder.create();

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            myListener = (AddCollectionDialogFragmentCallBack) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement AddCollectionDialogFragmentCallBack");
        }
    }
}

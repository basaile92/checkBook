package com.example.basaile92.listelivre.callback;

import android.app.DialogFragment;

/**
 * Created by basaile92 on 20/10/2016.
 */

public interface BookLibraryFragmentCallBack {

    public void updateDisplayBookFragment(int position);
    public void onDialogPositiveClick(DialogFragment dialog, String isbn);
    public void onDialogNegativeClick(DialogFragment dialog);
}

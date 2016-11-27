package com.example.basaile92.listelivre.callback;

import android.app.DialogFragment;
import android.view.View;

/**
 * Created by basaile92 on 20/10/2016.
 */

public interface BookLibraryFragmentCallBack {

    public void updateDisplayBookFragment(int position, View view);
    public void onDialogPositiveClick(DialogFragment dialog, String isbn);
    public void onDialogNegativeClick(DialogFragment dialog);
}

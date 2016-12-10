package com.example.basaile92.listelivre.callback;

import android.app.DialogFragment;
import android.view.View;

/**
 * Created by dyment on 24/11/16.
 */
public interface BookCollectionListFragmentCallBack {
    public void updateDisplayCollectionFragment(int position, View view);

    public void onDialogPositiveClick(DialogFragment dialog, String nameEdit);

    public void onDialogNegativeClick(DialogFragment dialog);
}

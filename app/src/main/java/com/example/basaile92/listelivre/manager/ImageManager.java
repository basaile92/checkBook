package com.example.basaile92.listelivre.manager;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Environment;

import com.example.basaile92.listelivre.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Class used to manipulated an Image
 */
public class ImageManager {

    /**
     * Create the file which contains the image
     * @param context
     * @return a temporary file
     * @throws IOException
     */
    public static File createImageFile(Context context) throws IOException {

        String timeStamp = String.valueOf(System.currentTimeMillis());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    /**
     * Compress a Bitmap in a file
     * @param context
     * @param bitmap : Bitmap to compress
     * @return the path of the file
     */
    public static String saveBitmap(Context context, Bitmap bitmap) {

        try {

            File file = createImageFile(context);
            OutputStream output = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
            output.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Rotate a Bitmap of 90 degrees
     * @param bitmap Bitmap to rotate
     * @return a rotate Bitmap
     */
    public static Bitmap getRotateBitmap(Bitmap bitmap){

        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * Resize a Bitmap
     * @param image Bitmap to resize
     * @param maxSize the size of the new Bitmap
     * @return a resize Bitmap
     */
    public static Bitmap getNewSizeBitmap(Bitmap image, int maxSize) {

        int width = image.getWidth();
        int height = image.getHeight();
        float res = (float) width / (float) height;
        if (res < 1) {
            height = maxSize;
            width = (int) (height * res);
        } else {
            width = maxSize;
            height = (int) (width / res);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    /**
     * Delete a photo's file
     * @param pathToDel the path of the file
     * @throws IOException if the deletion fails
     */
    public static void deletePhoto(String pathToDel) throws IOException {

        File file = new File(pathToDel);

        if (file.delete() == false) {
            throw new IOException();
        }

    }


    public interface DownloadImageListener {

        void onDownload(Bitmap bitmap);
    }

    public static class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

        protected DownloadImageListener listener;
        protected Context context;
        ProgressDialog progDailog;


        public ImageDownloader(DownloadImageListener listener, Context context) {
            this.listener = listener;
            this.context = context;
            this.progDailog = new ProgressDialog(this.context);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progDailog.setMessage(context.getString(R.string.loading));
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(true);
            progDailog.show();
        }


        @Override
        protected Bitmap doInBackground(String... params) {

            if (params.length == 0)
                return null;
            String url = params[0];
            try {
                InputStream input = new java.net.URL(url).openStream();
                return BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            progDailog.dismiss();
            if (!isCancelled())
                listener.onDownload(bitmap);
        }
    }
}

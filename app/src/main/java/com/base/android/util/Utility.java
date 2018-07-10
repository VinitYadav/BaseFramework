package com.base.android.util;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class Utility {

    /**
     * Show toast from any where
     *
     * @param context Context object
     * @param msg     Display message
     */
    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Add fragment
     *
     * @param activity Activity object
     * @param fragment Fragment object
     * @param tag      Fragment tag
     * @param id       View id
     */
    public static void addFragment(AppCompatActivity activity, Fragment fragment, String tag, int id) {
        FragmentManager fragmentManager = activity.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(id, fragment);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

    /**
     * Convert bitmap to base64
     */
    public static String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.NO_WRAP);
        return encImage;
    }

    /**
     * Get fragment name
     *
     * @param activity Activity object
     * @return Fragment name
     */
    public static String getFragmentTag(AppCompatActivity activity) {
        if (activity.getFragmentManager().getBackStackEntryCount() == 0) {
            return null;
        }
        return activity.getFragmentManager().getBackStackEntryAt(activity.getFragmentManager()
                .getBackStackEntryCount() - 1).getName();
    }

    /***
     *Set image
     *//*
    public static void setImageFromUrl(Context context, String image,
                                       ImageView imageView, int size,
                                       int defaultImage, ProgressBar progressBar) {
        AQuery aQuery = new AQuery(context);
        aQuery
                .id(imageView)
                .progress(progressBar)
                .image(image, true, true, size, defaultImage);
    }*/

    /**
     * Get data after split from pipe
     */
    public static ArrayList<String> splitFromPipe(String data) {
        String[] array = data.split("\\|");
        return (new ArrayList<String>(Arrays.asList(array)));
    }

    /**
     * Get data after split from comma
     */
    public static ArrayList<String> splitFromComma(String data) {
        String[] array = data.split(",");
        return (new ArrayList<String>(Arrays.asList(array)));
    }

    /**
     * Check internet connection
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm != null && cm.getActiveNetworkInfo() != null;
        } else {
            return false;
        }
    }
}

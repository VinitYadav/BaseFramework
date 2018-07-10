package com.base.android.util;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.base.android.R;

public class ProgressHelper {

    private Dialog dialog;
    private Activity mActivity;

    private static volatile ProgressHelper instance = null;

    public static ProgressHelper getInstance(Activity activity) {
        if (instance == null) {
            synchronized (ProgressHelper.class) {
                // Double check
                if (instance == null) {
                    instance = new ProgressHelper(activity);
                }
            }
        }
        return instance;
    }

    private ProgressHelper(Activity activity) {
        this.mActivity = activity;
    }

    /**
     * Show progress dialog when web service call
     */
    public void show() {
        dialog = new Dialog(mActivity);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_progess);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        ProgressBar progressBar = dialog.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(0xFFFFFFFF, android.graphics.PorterDuff.Mode.MULTIPLY);

        dialog.show();
    }

    /**
     * Dismiss progress dialog
     */
    public void dismiss() {
        dialog.dismiss();
    }
}

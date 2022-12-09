package com.edu.project1.Helper;

import android.content.Context;

import com.edu.project1.R;

import io.github.muddz.styleabletoast.StyleableToast;

public class CustomToast {
    public static void errorToast(Context context, String notification){
        StyleableToast.makeText(context,notification, R.style.errorToast).show();


    }
    public static void warningToast(Context context, String notification){
        StyleableToast.makeText(context,notification, R.style.warningToast).show();

    }
    public static void successToast(Context context, String notification){
        StyleableToast.makeText(context,notification, R.style.successToast).show();
    }

}

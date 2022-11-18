package com.edu.project1.Helper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.edu.project1.R;

import io.github.muddz.styleabletoast.StyleableToast;

public class CustomToasts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public  void errorToast(Context context, String notification){
        StyleableToast.makeText(context,notification, R.style.errorToast).show();

    }
    public  void warningToast(Context context, String notification){
        StyleableToast.makeText(context,notification, R.style.warningToast).show();

    }
    public  void successToast(Context context, String notification){
        StyleableToast.makeText(context,notification, R.style.successToast).show();

    }
}
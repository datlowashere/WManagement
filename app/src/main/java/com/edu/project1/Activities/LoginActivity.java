package com.edu.project1.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.edu.project1.Dao.UserDao;
import com.edu.project1.Helper.CustomToasts;
import com.edu.project1.MainActivity;
import com.edu.project1.R;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText edUsername,edPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
//        getSupportActionBar().hide();
        setContentView(R.layout.login_activity);

        edUsername=findViewById(R.id.edUsername);
        edPassword=findViewById(R.id.edPass);

        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });


        findViewById(R.id.tvRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }

    private void checkLogin(){
        String user=edUsername.getText().toString();
        String pass=edPassword.getText().toString();

        CustomToasts customToast=new CustomToasts();

        UserDao dao=new UserDao(LoginActivity.this);

        if(user.isEmpty() || pass.isEmpty()){
            customToast.warningToast(LoginActivity.this,"Tên đăng nhập và mật khẩu không được để trống");
        }else {
            if (dao.checkLogin(user, pass) > 0) {
                customToast.successToast(LoginActivity.this,"Đăng nhập thành công");
                Intent ilogin=new Intent(getApplicationContext(), MainActivity.class);
                overridePendingTransition(R.anim.in_right_animation,R.anim.out_left_animation);
                ilogin.putExtra("username",user);
                ilogin.putExtra("pass",pass);
                startActivity(ilogin);
                finish();
            }else{
                customToast.errorToast(LoginActivity.this, "Tên đăng nhập hoặc mật khẩu sai");
            }
        }

    }


}
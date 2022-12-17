package com.edu.project1.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.edu.project1.Dao.UserDao;
import com.edu.project1.Helper.CustomToast;
import com.edu.project1.MainActivity;
import com.edu.project1.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText edUsername,edPassword;
    private TextInputLayout tilUsername,tilPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        edUsername=findViewById(R.id.edUsername);
        edPassword=findViewById(R.id.edPass);
        tilUsername=findViewById(R.id.tilUsername);
        tilPassword=findViewById(R.id.tilPass);

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

        CustomToast customToast=new CustomToast();
        UserDao dao=new UserDao(LoginActivity.this);

        int check=1;

        if(user.isEmpty()){
            tilUsername.setError("Chưa nhập tên đăng nhập!!!");
            check=-1;
        }else{
            if(!dao.checkUsername(user)){
                tilUsername.setError("Tên đăng nhập không đúng!!!");
                check=-1;
            }else{
                tilUsername.setError("");
            }
        }
        if(pass.isEmpty()){
            tilPassword.setError("Chưa nhập mật khẩu!!!");
            check=-1;
        }else{
            if(!dao.checkPass(pass)){
                tilPassword.setError("mật khẩu không không đúng!!!");
                check=-1;
            }else{
                tilPassword.setError("");
            }
        }
        if(check>0){
            customToast.successToast(LoginActivity.this,"Đăng nhập thành công");
            Intent ilogin=new Intent(getApplicationContext(), MainActivity.class);
            overridePendingTransition(R.anim.in_right_animation,R.anim.out_left_animation);
            ilogin.putExtra("username",user);
            ilogin.putExtra("pass",pass);
            startActivity(ilogin);
            finish();
        }

    }

}
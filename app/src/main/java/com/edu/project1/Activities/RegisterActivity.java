package com.edu.project1.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.edu.project1.Dao.UserDao;
import com.edu.project1.Helper.CustomToasts;
import com.edu.project1.Models.User;
import com.edu.project1.R;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText edUsername,edName,edEmail,edKhoHang,edPassword;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
//        getSupportActionBar().hide();
        setContentView(R.layout.register_activity);

        edUsername=findViewById(R.id.edRegisterUsername);
        edName=findViewById(R.id.edRegisterHoTen);
        edEmail=findViewById(R.id.edRegisterEmail);
        edKhoHang=findViewById(R.id.edRegisterKhoHang);
        edPassword=findViewById(R.id.edRegisterPass);

        findViewById(R.id.tvLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                overridePendingTransition(R.anim.in_left_animation,R.anim.out_right_animation);
            }
        });

        findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRegister();

            }
        });


    }

    private void checkRegister(){
        String user=edUsername.getText().toString();
        String name=edName.getText().toString();
        String email=edEmail.getText().toString();
        String tenKho=edKhoHang.getText().toString();
        String pass=edPassword.getText().toString();

        UserDao dao=new UserDao(RegisterActivity.this);
        User list=new User();
        list.setUsername(user);
        list.setHoTen(name);
        list.setEmail(email);
        list.setTenKhoHang(tenKho);
        list.setPassword(pass);

        CustomToasts customToasts=new CustomToasts();
        if(user.isEmpty() || name.isEmpty() || email.isEmpty() || tenKho.isEmpty() || pass.isEmpty()){
            customToasts.warningToast(RegisterActivity.this,"Phải điền đầy đủ thông tin");
        }else{
            boolean check=dao.checkUsername(user);
            if(check==false){
                try {
                    dao.insert(list);
                    customToasts.successToast(RegisterActivity.this, "Đăng ký thành công");
                    clear();
                }catch (Exception e){
                    e.printStackTrace();
                    System.out.println("Lỗi"+e);
                    customToasts.errorToast(RegisterActivity.this, "Lỗi");
                }
            }else{
                customToasts.errorToast(RegisterActivity.this,"Tên đăng nhập đã tồn tại!!!");
            }
        }
    }

    private void clear(){
        edUsername.setText("");
        edName.setText("");
        edKhoHang.setText("");
        edEmail.setText("");
        edPassword.setText("");
    }
}
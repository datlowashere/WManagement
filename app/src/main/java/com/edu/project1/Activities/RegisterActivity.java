package com.edu.project1.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;

import com.edu.project1.Dao.UserDao;
import com.edu.project1.Helper.CustomToast;
import com.edu.project1.Models.User;
import com.edu.project1.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText edUsername,edName,edEmail,edKhoHang,edPassword;
    private TextInputLayout tilUsername,tilName,tilEmail,tilKhoHang,tilPassword;
    CustomToast customToasts=new CustomToast();
    UserDao dao;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        edUsername=findViewById(R.id.edRegisterUsername);
        edName=findViewById(R.id.edRegisterHoTen);
        edEmail=findViewById(R.id.edRegisterEmail);
        edKhoHang=findViewById(R.id.edRegisterKhoHang);
        edPassword=findViewById(R.id.edRegisterPass);

        tilUsername=findViewById(R.id.tilRegisterUsername);
        tilName=findViewById(R.id.tilRegisterHoTen);
        tilEmail=findViewById(R.id.tilRegisterEmail);
        tilKhoHang=findViewById(R.id.tilRegisterKhoHang);
        tilPassword=findViewById(R.id.tilRegisterPass);

        findViewById(R.id.tvLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                overridePendingTransition(R.anim.in_left_animation,R.anim.out_right_animation);
            }
        });
//       Bắt sự kiện khi click vào nút đăng ký
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

        dao=new UserDao(RegisterActivity.this);
        User list=new User();
        Resources res = getResources();
        list.setUsername(user);
        list.setImg(drawableToByte(R.drawable.user));
        list.setHoTen(name);
        list.setEmail(email);
        list.setTenKhoHang(tenKho);
        list.setPassword(pass);
        if(checkInput()>0){
            try {
                dao.insert(list);
                customToasts.successToast(RegisterActivity.this, "Đăng ký thành công");
                clear();
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("Lỗi"+e);
                customToasts.errorToast(RegisterActivity.this, "Lỗi");
            }

        }
    }
    private int checkInput(){
        String user=edUsername.getText().toString();
        String name=edName.getText().toString();
        String email=edEmail.getText().toString();
        String tenKho=edKhoHang.getText().toString();
        String pass=edPassword.getText().toString();

        dao=new UserDao(RegisterActivity.this);

        int check=1;

        if(user.isEmpty()){
            tilUsername.setError("Chưa nhập username!!!");
            check=-1;
        }else{
            if(dao.checkUsername(user)){
                tilUsername.setError("Username đã tồn tại!!!");
                check=-1;
            }else{
                tilUsername.setError("");
            }
        }
        if(name.isEmpty()){
            tilName.setError("Chưa nhập họ tên người dùng!!!");
            check=-1;
        }else{
            if(name.matches("\\d+")){
                tilName.setError("Họ tên không bao gồm số!!!");
                check=-1;
            }else{
                tilName.setError("");
            }
        }
        if(tenKho.isEmpty()){
            tilKhoHang.setError("Chưa nhập tên kho hàng");
            check=-1;
        }else{
            tilKhoHang.setError("");
        }
        if(email.isEmpty()){
            tilEmail.setError("Chưa nhập email!!!");
            check=-1;
        }else{
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                tilEmail.setError("Email không đúng định dạng!!!");
                check=-1;
            }else {
                tilEmail.setError("");
            }
        }
        if(pass.isEmpty()){
            tilPassword.setError("Chưa nhập password!!!");
            check=-1;
        }else{
            tilPassword.setError("");
        }

        return check;
    }
    private byte[] drawableToByte(int id){
        Resources res=getResources();
        Drawable drawable=res.getDrawable(id);
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bitMapData = stream.toByteArray();
        return bitMapData;
    }
    private void clear(){
        edUsername.setText("");
        edName.setText("");
        edKhoHang.setText("");
        edEmail.setText("");
        edPassword.setText("");
    }
}
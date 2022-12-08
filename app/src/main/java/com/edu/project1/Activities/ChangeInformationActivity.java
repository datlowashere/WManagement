package com.edu.project1.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;

import com.edu.project1.Dao.UserDao;
import com.edu.project1.Helper.CustomToasts;
import com.edu.project1.MainActivity;
import com.edu.project1.Models.User;
import com.edu.project1.R;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChangeInformationActivity extends AppCompatActivity {
    private TextInputEditText edName,edWName,edEmail,edOldPass,edNewPass,edReNewPass;
    private CircleImageView imgUser;
    User obj=new User();
    UserDao dao;
    CustomToasts customToasts=new CustomToasts();

    public static final int REQUEST_CODE_GALARY=999;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_information_user);

        Intent itoChangePass=getIntent();
        String username=itoChangePass.getStringExtra("username");
        String oldpass=itoChangePass.getStringExtra("pass");

        edName=findViewById(R.id.edChangeName);
        edWName=findViewById(R.id.edChangeWareHouseName);
        edEmail=findViewById(R.id.edChangeEmail);
        edOldPass=findViewById(R.id.edOldPass);
        edNewPass=findViewById(R.id.edNewPass);
        edReNewPass=findViewById(R.id.edReNewPass);
        imgUser=findViewById(R.id.imgUserAccount);

        dao=new UserDao(ChangeInformationActivity.this);
        obj=dao.getID(username);

        Bitmap bitmap= BitmapFactory.decodeByteArray(obj.getImg(),0,obj.getImg().length);
        imgUser.setImageBitmap(bitmap);
        edName.setText(obj.getHoTen());
        edWName.setText(obj.getTenKhoHang());
        edEmail.setText(obj.getEmail());
        edOldPass.setText(obj.getPassword());


        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        ChangeInformationActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALARY
                );
            }
        });

        findViewById(R.id.btnSaveChangeInfor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=edName.getText().toString();
                String nameW=edWName.getText().toString();
                String email=edEmail.getText().toString();
                String oldPass=edOldPass.getText().toString();
                String newPass=edNewPass.getText().toString();
                String reNewPass=edReNewPass.getText().toString();

                dao=new UserDao(ChangeInformationActivity.this);
                obj=dao.getID(username);
                obj.setImg(imageViewToByte(imgUser));
                obj.setHoTen(name);
                obj.setTenKhoHang(nameW);
                obj.setEmail(email);
                obj.setPassword(newPass);

                if(name.isEmpty() || nameW.isEmpty() || email.isEmpty() || oldPass.isEmpty() || newPass.isEmpty() || reNewPass.isEmpty()){
                    customToasts.warningToast(ChangeInformationActivity.this,"Phải nhập đủ thông tin");
                }else{
                    if (checkInput()>0){
                        if (dao.update(obj) > 0) {
                            customToasts.successToast(ChangeInformationActivity.this, "Thay đổi thành công");
                            clear();
                        } else {
                            customToasts.errorToast(ChangeInformationActivity.this, "Lỗi");
                        }
                    }
                }
            }
        });
        findViewById(R.id.btnCancelSaveChangeInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CODE_GALARY){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Intent igallary=new Intent(Intent.ACTION_GET_CONTENT);
                igallary.setType("image/*");
                startActivityForResult(igallary,REQUEST_CODE_GALARY);
            }else {
                customToasts.errorToast(this,"Chưa cấp quyền truy cập bộ nhớ");
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==REQUEST_CODE_GALARY && resultCode==RESULT_OK){
            Uri uri=data.getData();
            try {
                InputStream stream=getApplicationContext().getContentResolver().openInputStream(uri);
                Bitmap bitmap= BitmapFactory.decodeStream(stream);
                imgUser.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private byte[] imageViewToByte(CircleImageView img){
        Bitmap bitmap=((BitmapDrawable) img.getDrawable()).getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] byteArr=stream.toByteArray();
        return byteArr;
    }

    private int checkInput(){
        Intent itoChangePass=getIntent();
        String oldpass=itoChangePass.getStringExtra("pass");
        int check=1;
        if(edName.getText().toString().isEmpty() || edWName.getText().toString().isEmpty() || edEmail.getText().toString().isEmpty() || edOldPass.getText().toString().isEmpty() || edNewPass.getText().toString().isEmpty() || edReNewPass.getText().toString().isEmpty()){
            customToasts.warningToast(ChangeInformationActivity.this,"Chưa điền đầy đủ thông tin");
            check=-1;
        }else {
            if(edName.getText().toString().matches("[0-9]+")){
                customToasts.errorToast(ChangeInformationActivity.this,"Tên không được là số!!");
                check=-1;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(edEmail.getText().toString()).matches()){
                customToasts.errorToast(ChangeInformationActivity.this,"Email định dạng không đúng!!");
                check=-1;
            }
            if(!edReNewPass.getText().toString().equals(edNewPass.getText().toString())){
                customToasts.errorToast(ChangeInformationActivity.this,"Mật khẩu mới không trùng");
                check=-1;
            }else if(!edOldPass.getText().toString().equals(oldpass)){
                customToasts.errorToast(ChangeInformationActivity.this,"Mật khẩu cũ không đúng");
                check=-1;
            }
        }
        return check;
    }




    private void clear(){
        edName.setText("");
        edWName.setText("");
        edEmail.setText("");
        edOldPass.setText("");
        edNewPass.setText("");
        edReNewPass.setText("");
    }


}
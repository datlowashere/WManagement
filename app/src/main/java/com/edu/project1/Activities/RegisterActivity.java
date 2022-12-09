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

import java.io.ByteArrayOutputStream;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText edUsername,edName,edEmail,edKhoHang,edPassword;
    CustomToast customToasts=new CustomToast();
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

//        Chuyển đến màn hình đăng nhập
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

//    Kiểm tra đk
    private void checkRegister(){
        String user=edUsername.getText().toString();
        String name=edName.getText().toString();
        String email=edEmail.getText().toString();
        String tenKho=edKhoHang.getText().toString();
        String pass=edPassword.getText().toString();

        UserDao dao=new UserDao(RegisterActivity.this);
        User list=new User();
        Resources res = getResources();
//     add các thông tin nhập từ textinput editext vào list trừ ảnh là lấy tạm 1 1 tệp trong drawable
        list.setUsername(user);
        list.setImg(drawableToByte(R.drawable.user));
        list.setHoTen(name);
        list.setEmail(email);
        list.setTenKhoHang(tenKho);
        list.setPassword(pass);
//        nếu các trường nhập đúng thì thêm các thông tin nhập vào bảng và ngược lại
        if(checkInput()>0){
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
//    validate đăng ký
    private int checkInput(){
        String user=edUsername.getText().toString();
        String name=edName.getText().toString();
        String email=edEmail.getText().toString();
        String tenKho=edKhoHang.getText().toString();
        String pass=edPassword.getText().toString();
        int check=1;
        if(user.isEmpty() || name.isEmpty() || email.isEmpty() || tenKho.isEmpty() || pass.isEmpty()){
            customToasts.warningToast(RegisterActivity.this,"Phải điền đầy đủ thông tin");
            check=-1;
        }
        if(name.matches("\\d+")){
            customToasts.warningToast(RegisterActivity.this,"Họ tên không được là số");
            check=-1;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            customToasts.warningToast(RegisterActivity.this,"Email không hợp lệ");
            check=-1;
        }
        return check;
    }
// chuyển tệp drawable thành byte[]
    private byte[] drawableToByte(int id){
        Resources res=getResources();
        Drawable drawable=res.getDrawable(id);
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bitMapData = stream.toByteArray();
        return bitMapData;
    }
//    xóa trắng các trường
    private void clear(){
        edUsername.setText("");
        edName.setText("");
        edKhoHang.setText("");
        edEmail.setText("");
        edPassword.setText("");
    }
}
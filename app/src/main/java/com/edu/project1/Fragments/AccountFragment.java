package com.edu.project1.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edu.project1.Activities.ChangeInformationActivity;
import com.edu.project1.Activities.InformationAppActivity;
import com.edu.project1.Activities.LoginActivity;
import com.edu.project1.Dao.UserDao;
import com.edu.project1.Helper.CustomToast;
import com.edu.project1.MainActivity;
import com.edu.project1.Models.User;
import com.edu.project1.R;
import com.google.android.material.textfield.TextInputEditText;

import de.hdodenhof.circleimageview.CircleImageView;


public class AccountFragment extends Fragment {

    private TextView tvAccountName,tvShowname,tvShownameW,tvUsername,tvPassword,tvEmail;
    private CircleImageView img;
    private TextInputEditText edName,edWName,edEmail,edOldPass,edNewPass,edReNewPass;
    User obj=new User();
    UserDao dao;
    CustomToast customToasts=new CustomToast();

    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance() {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_account, container, false);

        tvAccountName=view.findViewById(R.id.tvAccountName);
        tvShowname=view.findViewById(R.id.tvAccountShowNameUser);
        tvShownameW=view.findViewById(R.id.tvAccountShowNameWareHouse);
        tvEmail=view.findViewById(R.id.tvAccoutShowEmail);
        tvUsername=view.findViewById(R.id.tvAccountShowUsername);
        tvPassword=view.findViewById(R.id.tvAccoutShowPassword);
        img=view.findViewById(R.id.imgUserAccount);


        showInformationUser();

        view.findViewById(R.id.tvLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("Thông báo");
                builder.setMessage("Chắc chắn thoát?");
                builder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getActivity(),LoginActivity.class));
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.getWindow().setWindowAnimations(R.style.animationDialog);
                alertDialog.show();
            }
        });
        view.findViewById(R.id.btnChangeInfoAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToChangeInformationActivity();

            }
        });
        view.findViewById(R.id.tvInformationAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                moveToShowInformationAppActivity();
            }
        });
        view.findViewById(R.id.tvDeleteAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("Thông báo");
                builder.setMessage("Chắc chắn xóa tài khoản?");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserDao dao=new UserDao(getContext());
                        try {
                            dao.delete(getUsername());
                            customToasts.successToast(getContext(),"Xóa thành công");
                            startActivity(new Intent(getActivity(),LoginActivity.class));
                            return;
                        }catch (Exception e){
                            e.printStackTrace();
                            customToasts.errorToast(getContext(),"Lỗi");
                        }
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.getWindow().setWindowAnimations(R.style.animationDialog);
                alertDialog.show();

            }
        });
        return view;
    }
    private void showInformationUser(){
        User obj=new User();
        UserDao dao=new UserDao(getContext());
        obj=dao.getID(getUsername());

        Bitmap bitmap= BitmapFactory.decodeByteArray(obj.getImg(),0,obj.getImg().length);
        img.setImageBitmap(bitmap);
        tvAccountName.setText(obj.getHoTen());
        tvShowname.setText(obj.getHoTen());
        tvShownameW.setText(obj.getTenKhoHang());
        tvEmail.setText(obj.getEmail());
        tvUsername.setText(obj.getUsername());
        tvPassword.setText(obj.getPassword());

    }
    private String getUsername(){
        MainActivity activity=(MainActivity)getActivity();
        String username= activity.getUsername();
        return username;
    }
    private void moveToChangeInformationActivity () {
        MainActivity activity=(MainActivity)getActivity();
        String username= activity.getUsername();
        String oldpass=activity.getPassword();
        Intent itoChangePass = new Intent(getActivity(), ChangeInformationActivity.class);
        itoChangePass.putExtra("username",username);
        itoChangePass.putExtra("pass",oldpass);
        startActivity(itoChangePass);

    }
    private void moveToShowInformationAppActivity(){
        Intent itoShowInforApp=new Intent(getActivity(), InformationAppActivity.class);
        startActivity(itoShowInforApp);
    }
    @Override
    public void onResume() {
        super.onResume();
        showInformationUser();
    }
}
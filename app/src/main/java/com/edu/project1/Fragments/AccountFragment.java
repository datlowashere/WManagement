package com.edu.project1.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.project1.Activities.LoginActivity;
import com.edu.project1.Dao.UserDao;
import com.edu.project1.MainActivity;
import com.edu.project1.Models.User;
import com.edu.project1.R;
import com.google.android.material.textfield.TextInputEditText;


public class AccountFragment extends Fragment {


    private Dialog dialog;
    private TextInputEditText edName,edWName,edEmail,edOldPass,edNewPass,edReNewPass;
    private TextView tvAccountName,tvShowname,tvShownameW,tvUsername,tvPassword,tvEmail;

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
                alertDialog.show();
            }
        });

        view.findViewById(R.id.btnChangeInfoAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogChangeInformation();

            }
        });
        view.findViewById(R.id.tvInformationAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogShowInformationApp();
            }
        });

        view.findViewById(R.id.tvLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
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
                alertDialog.show();
            }
        });



        return view;
    }
    private void showInformationUser(){
        MainActivity activity=(MainActivity)getActivity();
        String username= activity.getUsername();
        User obj=new User();
        UserDao dao=new UserDao(getContext());
        obj=dao.getID(username);

        tvAccountName.setText(obj.getHoTen());
        tvShowname.setText(obj.getHoTen());
        tvShownameW.setText(obj.getTenKhoHang());
        tvEmail.setText(obj.getEmail());
        tvUsername.setText(obj.getUsername());
        tvPassword.setText(obj.getPassword());

    }
    private void dialogShowInformationApp(){

    }
    private void dialogChangeInformation(){
        dialog=new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_change_information_user);
        edName=dialog.findViewById(R.id.edChangeName);
        edWName=dialog.findViewById(R.id.edChangeWareHouseName);
        edEmail=dialog.findViewById(R.id.edChangeEmail);
        edOldPass=dialog.findViewById(R.id.edOldPass);
        edNewPass=dialog.findViewById(R.id.edNewPass);
        edReNewPass=dialog.findViewById(R.id.edReNewPass);



        dialog.findViewById(R.id.btnSaveChangeInfor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity=(MainActivity)getActivity();
                String username= activity.getUsername();
                String oldpass=activity.getPassword();

                String name=edName.getText().toString();
                String nameW=edWName.getText().toString();
                String email=edEmail.getText().toString();
                String oldPass=edOldPass.getText().toString();
                String newPass=edNewPass.getText().toString();
                String reNewPass=edReNewPass.getText().toString();

                UserDao dao=new UserDao(getContext());
                User obj=new User();
                obj=dao.getID(username);
                obj.setHoTen(name);
                obj.setTenKhoHang(nameW);
                obj.setEmail(email);
                obj.setPassword(newPass);

                if(name.isEmpty() || nameW.isEmpty() || email.isEmpty() || oldPass.isEmpty() || newPass.isEmpty() || reNewPass.isEmpty()){
                    Toast.makeText(getContext(),"Phải nhập đủ thông tin",Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(),"Username"+obj.getUsername(),Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(),"Mật khẩu"+obj.getPassword(),Toast.LENGTH_SHORT).show();
                }else{
                    if(!reNewPass.equals(newPass)){
                        Toast.makeText(getContext(),"Mật khẩu mới không trùng",Toast.LENGTH_SHORT).show();
                    }else if(!oldPass.equals(oldpass)){
                        Toast.makeText(getContext(),"Mật khẩu cũ không đúng",Toast.LENGTH_SHORT).show();
                    }else {
                        if (dao.update(obj) > 0) {
                            Toast.makeText(getContext(), "Thay đổi thành công", Toast.LENGTH_SHORT).show();
                            clear();
                        } else {
                            Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        dialog.findViewById(R.id.btnCancelSaveChangeInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void clear(){
        edName.setText("");
        edWName.setText("");
        edEmail.setText("");
        edOldPass.setText("");
        edNewPass.setText("");
        edReNewPass.setText("");
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
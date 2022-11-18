package com.edu.project1.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.project1.Activities.LoginActivity;
import com.edu.project1.Dao.UserDao;
import com.edu.project1.Helper.CustomToasts;
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
                alertDialog.getWindow().setWindowAnimations(R.style.animationDialog);
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
        dialog.getWindow().setWindowAnimations(R.style.animationDialog);

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

                CustomToasts customToasts=new CustomToasts();

                if(name.isEmpty() || nameW.isEmpty() || email.isEmpty() || oldPass.isEmpty() || newPass.isEmpty() || reNewPass.isEmpty()){
                    customToasts.warningToast(getContext(),"Phải nhập đủ thông tin");
                }else{
                    if(!reNewPass.equals(newPass)){
                        customToasts.errorToast(getContext(),"Mật khẩu mới không trùng");
                    }else if(!oldPass.equals(oldpass)){
                        customToasts.errorToast(getContext(),"Mật khẩu cũ không đúng");
                    }else {
                        if (dao.update(obj) > 0) {
                            customToasts.successToast(getContext(), "Thay đổi thành công");
                            clear();
                        } else {
                            customToasts.errorToast(getContext(), "Lỗi");
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
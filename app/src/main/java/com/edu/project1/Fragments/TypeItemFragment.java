package com.edu.project1.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import com.edu.project1.Adapter.TypeItemsAdapter;
import com.edu.project1.Dao.TypeItemsDao;
import com.edu.project1.Helper.CustomToast;
import com.edu.project1.MainActivity;
import com.edu.project1.Models.TypeItems;
import com.edu.project1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class TypeItemFragment extends Fragment {

    private ListView lv;
    private FloatingActionButton fab;
    private SearchView searchView;
    private Dialog dialog;
    private TextInputEditText edTenLoaiHang,edMaLoaiHang;
    private TextInputLayout tilTenLoai;
    private  List<TypeItems>list;
    private TypeItemsDao dao;
    private TypeItemsAdapter adapter;
    private TypeItems obj;
    CustomToast customToasts=new CustomToast();

    public TypeItemFragment() {
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
        View view=inflater.inflate(R.layout.fragment_type_item, container, false);
        fab=view.findViewById(R.id.fabTypeItem);
        lv=view.findViewById(R.id.lvTypeItem);
        searchView=view.findViewById(R.id.svTypeItems);

        dao=new TypeItemsDao(getActivity());
        reLoad();
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               obj=list.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Chọn chức năng");

                String[] choose = {"Sửa", "Xóa"};
                builder.setItems(choose, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                diaLogTypeItem(getContext(),1);
                                break;
                            case 1:
                                String id= String.valueOf(list.get(position).getMaLoaiHang());
                                deleteTypeItem(id);
                                break;
                        }
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setWindowAnimations(R.style.animationDialog);
                alertDialog.show();
               return false;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaLogTypeItem(getContext(),0);
            }
        });

        searchView.setQueryHint("Tên loại hàng...");
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchName(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                searchName(newText);
                return true;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                reLoad();
                return true;
            }
        });

        return view;
    }
    private void diaLogTypeItem(final Context context, final int position){
        dialog=new Dialog(context);
        dialog.setContentView(R.layout.dialog_type_item);
        dialog.getWindow().setWindowAnimations(R.style.animationDialog);
        edTenLoaiHang=dialog.findViewById(R.id.edTenLoaiHang);
        edMaLoaiHang=dialog.findViewById(R.id.edMaLoaiHang);
        tilTenLoai=dialog.findViewById(R.id.tilTenLoaiHang);

        edMaLoaiHang.setVisibility(View.GONE);

        if(position!=0){
            edMaLoaiHang.setText(String.valueOf(obj.getMaLoaiHang()));
            edTenLoaiHang.setText(obj.getTenLoaiHang());
        }
        dialog.findViewById(R.id.btnSaveLoaiHang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obj=new TypeItems();
                MainActivity activity=(MainActivity)getActivity();
                String username=activity.getUsername();
                obj.setUsername(username);
                obj.setTenLoaiHang(edTenLoaiHang.getText().toString());
              if(checkInput()>0){
                  boolean checkLoai=dao.checkTenLoai(edTenLoaiHang.getText().toString(),username);
                    if(position==0){
                        if(!checkLoai) {
                            if (dao.insert(obj) > 0) {
                                customToasts.successToast(context, "Thêm thành công");
                                edTenLoaiHang.setText("");
                            } else {
                                customToasts.errorToast(context, "Thêm thất bại");
                            }
                        }else {
                            customToasts.errorToast(context,"Loại hàng đã được tạo vui lòng tạo loại hàng khác");
                        }
                    }else{
                        obj.setMaLoaiHang(Integer.parseInt(edMaLoaiHang.getText().toString()));
                        if(dao.update(obj)>0){
                            customToasts.successToast(context,"Cập nhật thành công");
                        }else{
                            customToasts.errorToast(context,"Cập nhật thất bại");
                        }
                    }
                  reLoad();
                  dialog.dismiss();
                }

            }
        });
        dialog.findViewById(R.id.btnCancelSaveLoaiHang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
    public void deleteTypeItem(final String id){
        dialog=new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_delete);
        dialog.getWindow().setWindowAnimations(R.style.animationDialog);
        dialog.findViewById(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dao.delete(id);
                    customToasts.successToast(getContext(),"Xóa thành công");
                    reLoad();
                    dialog.dismiss();
                }catch (Exception e){
                    e.printStackTrace();
                    customToasts.errorToast(getContext(),"Lỗi: "+e);
                }

            }
        });
        dialog.findViewById(R.id.btnCancelDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void searchName(String name){
        dao=new TypeItemsDao(getContext());
        list=dao.searchByName(name);
        if(list!=null){
            lv.setAdapter(new TypeItemsAdapter(getContext(),list));
        }
    }

    private void reLoad(){
        MainActivity activity=(MainActivity)getActivity();
        String username=activity.getUsername();
        list=new ArrayList<>();
        list=dao.getAllByUser(username);
        adapter=new TypeItemsAdapter(getContext(),list);
        lv.setAdapter(adapter);
    }
    private int checkInput(){
        int check=1;
        if(edTenLoaiHang.getText().toString().isEmpty()){
            tilTenLoai.setError("Chưa điền tên loại hàng");
            check=-1;
        }else if(edTenLoaiHang.getText().toString().matches("[0-9]+")){
            tilTenLoai.setError("Tên loại hàng không bao gồm số");
            check=-1;
        }
        return check;
    }
}
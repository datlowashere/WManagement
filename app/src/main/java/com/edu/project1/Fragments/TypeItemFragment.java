package com.edu.project1.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.edu.project1.Adapter.TypeItemsAdapter;
import com.edu.project1.Dao.TypeItemsDao;
import com.edu.project1.Helper.CustomToasts;
import com.edu.project1.MainActivity;
import com.edu.project1.Models.TypeItems;
import com.edu.project1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class TypeItemFragment extends Fragment {

    private RecyclerView recyclerView;
    private ListView lv;
    private FloatingActionButton fab;
    private Dialog dialog;
    private TextInputEditText edTenLoaiHang,edMaLoaiHang;
    private  List<TypeItems>list;
    private TypeItemsDao dao;
    private TypeItemsAdapter adapter;
    private TypeItems obj;
    CustomToasts customToasts=new CustomToasts();

    public TypeItemFragment() {
    }

    public static TypeItemFragment newInstance(String param1, String param2) {
        TypeItemFragment fragment = new TypeItemFragment();
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
        View view=inflater.inflate(R.layout.fragment_type_item, container, false);
//        recyclerView=view.findViewById(R.id.rvTypeItem);
        fab=view.findViewById(R.id.fabTypeItem);
        lv=view.findViewById(R.id.lvTypeItem);

        dao=new TypeItemsDao(getActivity());
        reLoad();
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               obj=list.get(position);
//               diaLogTypeItem(getContext(),1);

               dialog=new Dialog(getContext());
               dialog.setContentView(R.layout.dialog_choose);
               dialog.getWindow().setWindowAnimations(R.style.animationDialog);
               dialog.findViewById(R.id.btnChooseUpdate).setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       diaLogTypeItem(getContext(),1);
                   }
               });
               dialog.findViewById(R.id.btnChooseDelete).setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       String id= String.valueOf(list.get(position).getMaLoaiHang());
                       deleteTypeItem(id);

                   }
               });

               dialog.show();
               return false;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaLogTypeItem(getContext(),0);
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
                    if(position==0){
                        if(dao.insert(obj)>0){
                            customToasts.successToast(context,"Thêm thành công");
                            edTenLoaiHang.setText("");
                        }else{
                            customToasts.errorToast(context,"Thêm thất bại");
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
            customToasts.warningToast(getContext(),"Chưa điền tên loại hàng");
            check=-1;
        }else if(edTenLoaiHang.getText().toString().equals("[0-9]")){
            customToasts.warningToast(getContext(),"Tên loại hàng không bao gồm số");
            check=-1;
        }
        return check;
    }
}
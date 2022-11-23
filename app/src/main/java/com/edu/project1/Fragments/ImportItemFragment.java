package com.edu.project1.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.edu.project1.Adapter.ImportItemsAdapter;
import com.edu.project1.Adapter.SpinnerTypeItemsAdapter;
import com.edu.project1.Dao.ImportDao;
import com.edu.project1.Dao.TypeItemsDao;
import com.edu.project1.Helper.CustomToasts;
import com.edu.project1.MainActivity;
import com.edu.project1.Models.ImportItems;
import com.edu.project1.Models.TypeItems;
import com.edu.project1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ImportItemFragment extends Fragment {

    private ListView lv;
    private FloatingActionButton fab;
    private SearchView searchView;
    private Dialog dialog;

    private TextView tvMaNhapHang;
    private TextInputEditText edTenHang,edSoLuongNhap,edDonGia,edNgayNhapHang,edNgaySanXuat;
    private Spinner spLoaiHang,spFilter;

    List<ImportItems> list;
    ImportItems obj;
    ImportDao dao;
    TypeItems typeItems;
    List<TypeItems>typeItemsList;
    TypeItemsDao typeItemsDao;
    ImportItemsAdapter adapter;
    SpinnerTypeItemsAdapter spinnerTypeItemsAdapter;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    CustomToasts customToasts=new CustomToasts();
    int positionML;



    public ImportItemFragment() {
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
        View view=inflater.inflate(R.layout.fragment_import_item, container, false);
        lv=view.findViewById(R.id.lvImportItem);
        fab=view.findViewById(R.id.fabImportItem);
        searchView=view.findViewById(R.id.searchViewImport);
        spFilter=view.findViewById(R.id.spFilteImport);

        reLoadData();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogImport(getContext(),0);
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               obj= list.get(position);
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("Chọn chức năng");
                String []choose={"Sửa","Xóa"};
                builder.setItems(choose, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                dialogImport(getContext(),1);
                                break;
                            case 1:
                                dialogDelete(String.valueOf(list.get(position).getMaNhapHang()));
                                break;
                        }
                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.getWindow().setWindowAnimations(R.style.animationDialog);
                alertDialog.show();
                return false;
            }
        });
        return view;
    }

    private void dialogImport(final Context context,final int position){
        dialog=new Dialog(context);
        dialog.setContentView(R.layout.dialog_import_item);
        dialog.getWindow().setWindowAnimations(R.style.animationDialog);

        tvMaNhapHang=dialog.findViewById(R.id.tvMaNhapHangDiaLogImport);
        tvMaNhapHang.setVisibility(View.GONE);
        edTenHang=dialog.findViewById(R.id.edTenHangNhap);
        edSoLuongNhap=dialog.findViewById(R.id.edSoLuongNhap);
        edDonGia=dialog.findViewById(R.id.edDonGiaNhap);
        edNgaySanXuat=dialog.findViewById(R.id.edNgaySanXuat);
        edNgayNhapHang=dialog.findViewById(R.id.edNgayNhapHang);
        spLoaiHang=dialog.findViewById(R.id.spLoaiHang);

        typeItems=new TypeItems();
        typeItemsList=new ArrayList<>();
        typeItemsDao=new TypeItemsDao(context);
        typeItemsList=typeItemsDao.getAllByUser(getUsername());
        spinnerTypeItemsAdapter=new SpinnerTypeItemsAdapter(typeItemsList,getContext());
        spLoaiHang.setAdapter(spinnerTypeItemsAdapter);

        if(position!=0){
            tvMaNhapHang.setText(String.valueOf(obj.getMaNhapHang()));
            edTenHang.setText(obj.getTenHang());

            for(int i=0;i<typeItemsList.size();i++){
                if(obj.getTenLoaiHang().equals(typeItemsList.get(i).getTenLoaiHang())){
                    positionML=i;
                }
            }
            spLoaiHang.setSelection(positionML);
            edSoLuongNhap.setText(String.valueOf(obj.getSoLuongNhap()));
            edDonGia.setText(String.valueOf(obj.getDonGia()));
            edNgaySanXuat.setText(sdf.format(obj.getNgaySanXuat()));
            edNgayNhapHang.setText(sdf.format(obj.getNgayNhapHang()));
        }

        edNgayNhapHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPickDate(edNgayNhapHang);
            }
        });
        edNgaySanXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPickDate(edNgaySanXuat);
            }
        });
        dialog.findViewById(R.id.btnSaveNhapHang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obj=new ImportItems();
                dao=new ImportDao(context);
                typeItems=(TypeItems)spLoaiHang.getSelectedItem();

                obj.setTenHang(edTenHang.getText().toString());
                obj.setTenLoaiHang(typeItems.getTenLoaiHang());
                try {
                    obj.setSoLuongNhap(Integer.parseInt(edSoLuongNhap.getText().toString()));
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    obj.setDonGia(Float.parseFloat(edDonGia.getText().toString()));
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    obj.setNgaySanXuat(sdf.parse(edNgaySanXuat.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    obj.setNgayNhapHang(sdf.parse(edNgayNhapHang.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                obj.setMaLoaiHang(typeItems.getMaLoaiHang());
                obj.setUsername(getUsername());
                if(checkInput()>0) {
                    if (position == 0) {
                        if (dao.insert(obj) > 0) {
                            customToasts.successToast(context, "Thêm thành công");
                            dialog.dismiss();
                        } else {
                            customToasts.errorToast(context, "Thêm thất bại");
                        }
                    } else {
                        obj.setMaNhapHang(Integer.parseInt(tvMaNhapHang.getText().toString()));
                        if (dao.update(obj) > 0) {
                            customToasts.successToast(context, "Cập nhật thành công");
                            dialog.dismiss();
                        } else {
                            customToasts.errorToast(context, "Cập nhật thất bại \n" +
                                    "mã" + tvMaNhapHang.getText().toString() + "tên" + obj.getTenHang() + "mãLoại" + positionML + "tên loại" + typeItems.getTenLoaiHang() + "donGia" + obj.getDonGia() + "soLuong" + obj.getSoLuongNhap() + "Ngaynhap" + obj.getNgayNhapHang() + "Ngày xuẩts" + obj.getNgaySanXuat() + "username" + obj.getUsername());
                            System.out.println("Cập nhật thất bại \n" +
                                    "mã" + tvMaNhapHang.getText().toString() + "tên" + obj.getTenHang() + "mãLoại" + positionML + "tên loại" + typeItems.getTenLoaiHang() + "donGia" + obj.getDonGia() + "soLuong" + obj.getSoLuongNhap() + "Ngaynhap" + obj.getNgayNhapHang() + "Ngày xuẩts" + obj.getNgaySanXuat() + "username" + obj.getUsername());
                        }

                    }
                    reLoadData();
                    dialog.dismiss();
                }
                }

        });
//        Hủy lưu
        dialog.findViewById(R.id.btnCancelSaveNhapHang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();


    }

    private void dialogPickDate(TextInputEditText edText){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                       edText.setText(dayOfMonth  + "/"+(monthOfYear + 1) +"/" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }
    private void dialogDelete(String id){
        dialog=new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_delete);
        dialog.getWindow().setWindowAnimations(R.style.animationDialog);
        dialog.findViewById(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    dao.delete(id);
                    customToasts.successToast(getContext(),"Xóa thành công");
                    dialog.dismiss();
                    reLoadData();
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
    private int checkInput(){
        int check=1;
//        if(edTenHang.getText().toString().isEmpty() || edSoLuongNhap.getText().toString().isEmpty() || edDonGia.getText().toString().isEmpty() || edNgayNhapHang.getText().toString().isEmpty() || edNgaySanXuat.getText().toString().isEmpty()){
//            customToasts.warningToast(getContext(),"Phải điền đầy đủ thông tin");
//            check=-1;
//        }else{
//            if(edTenHang.getText().toString().matches("[0-9]")){
//                customToasts.warningToast(getContext(),"Tên hàng phải là các ký tự");
//                check=-1;
//            }
//            if(!edDonGia.getText().toString().matches("[+-]?([0-9]*[.])?[0-9]+")){
//                customToasts.warningToast(getContext(),"Đơn giá sai");
//                check=-1;
//            }
//            if(!edSoLuongNhap.getText().toString().matches("[0-9]")){
//                customToasts.warningToast(getContext(),"Số lượng giá sai");
//                check=-1;
//            }
//        }

        return check;
    }
    private void reLoadData(){
        list=new ArrayList<>();
        dao=new ImportDao(getContext());
        list=dao.getAllByUsername(getUsername());
        adapter=new ImportItemsAdapter(getContext(),list);
        lv.setAdapter(adapter);
    }
    private String getUsername(){
        MainActivity activity=(MainActivity)getActivity();
        String username=activity.getUsername();
        return username;
    }


}
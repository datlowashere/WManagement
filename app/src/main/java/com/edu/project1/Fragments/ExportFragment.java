package com.edu.project1.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.edu.project1.Adapter.ExportItemAdapter;
import com.edu.project1.Adapter.SpinnerTypeItemsFromImportAdapter;
import com.edu.project1.Adapter.SpinnerItemsAdapter;
import com.edu.project1.Adapter.SpinnerTypeItemsAdapter;
import com.edu.project1.Adapter.TypeItemsAdapter;
import com.edu.project1.Dao.ExportDao;
import com.edu.project1.Dao.ImportDao;
import com.edu.project1.Dao.TypeItemsDao;
import com.edu.project1.Helper.CustomToast;
import com.edu.project1.MainActivity;
import com.edu.project1.Models.ExportItems;
import com.edu.project1.Models.ImportItems;
import com.edu.project1.Models.TypeItems;
import com.edu.project1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ExportFragment extends Fragment {


    private ExportItems obj;
    private List<ExportItems>list;
    private ExportDao dao;
    private ExportItemAdapter adapter;

    private ImportDao importDao;
    private ImportItems importItems;
    private List<ImportItems>importItemsList;
    private List<ImportItems>importListLoai;
    private SpinnerItemsAdapter spinnerItemsAdapter;

    private List<TypeItems>typeItemsList;
    private TypeItemsDao typeItemsDao;
    private SpinnerTypeItemsAdapter spinnerTypeItemsAdapter;

    private ListView lv;
    private FloatingActionButton fab;
    private SearchView searchView;
    private ImageView imgFilter;

    private Dialog dialog;
    private TextInputEditText edSoLuongXuat,edDonGia,edNgayXuatHang;
    private TextInputLayout tilSoLuongXuat,tilDonGia,tilNgayXuat;
    private TextView tvMaXuat,tvTenLoaiHang;
    private Spinner spTenHang,spLoaiHang;

    int positionIP,positionTL;

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    CustomToast customToasts=new CustomToast();

    public ExportFragment() {
        // Required empty public constructor
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
        View view=inflater.inflate(R.layout.fragment_export, container, false);
        lv=view.findViewById(R.id.lvExport);
        fab=view.findViewById(R.id.fabExport);
        searchView=view.findViewById(R.id.svExport);
        imgFilter=view.findViewById(R.id.imgFilterExport);

        reLoadData();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaLogExport(getContext(),0);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                obj=list.get(position);
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("Chọn chức năng");
                String[] choose={"Sửa","Xóa"};
                builder.setItems(choose, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                diaLogExport(getContext(),1);
                                break;
                            case 1:
                                dialogDelete(String.valueOf(list.get(position).getMaXuatHang()));
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

        searchView.setQueryHint("Nhập tên hàng...");
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchByName(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchByName(newText);
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                reLoadData();
                return true;
            }
        });
        imgFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeItemsDao=new TypeItemsDao(getContext());
                typeItemsList=typeItemsDao.getAllByUser(getUser());
                ListAdapter listAdapter=new TypeItemsAdapter(getContext(),typeItemsList);
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("Lọc");
                builder.setAdapter(listAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        list=new ArrayList<>();
                        dao=new ExportDao(getContext());
                        list=dao.getAllByTenLoaiHang(typeItemsList.get(which).getTenLoaiHang());
                        adapter=new ExportItemAdapter(list,getContext());
                        lv.setAdapter(adapter);
                    }
                });
                builder.setPositiveButton("Tất cả", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reLoadData();
                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.getWindow().setWindowAnimations(R.style.animationDialog2);
                alertDialog.show();

            }
        });


        return view;
    }

    private void diaLogExport(final Context context,final int possition){
        dialog=new Dialog(context);
        dialog.setContentView(R.layout.dialog_export_item);
        dialog.getWindow().setWindowAnimations(R.style.animationDialog);

        tilDonGia=dialog.findViewById(R.id.tilDonGiaXuat);
        tilSoLuongXuat=dialog.findViewById(R.id.tilSoLuongXuat);
        tilNgayXuat=dialog.findViewById(R.id.tilNgayXuatHang);

        tvMaXuat=dialog.findViewById(R.id.tvMaXH);
        tvMaXuat.setVisibility(View.GONE);
        spTenHang=dialog.findViewById(R.id.spTenHang);
        spLoaiHang=dialog.findViewById(R.id.spTenLoaiHangXuat);
        tvTenLoaiHang=dialog.findViewById(R.id.tvLoaiHangX);
        edSoLuongXuat=dialog.findViewById(R.id.edSoLuongXuat);
        edDonGia=dialog.findViewById(R.id.edDonGiaXuat);
        edNgayXuatHang=dialog.findViewById(R.id.edNgayXuatHang);

        importDao=new ImportDao(context);
        importListLoai=(List<ImportItems>)importDao.getAllByUserGroupByMaLoai(getUser());
        SpinnerTypeItemsFromImportAdapter filterTyeItemImportAdapter=new SpinnerTypeItemsFromImportAdapter(importListLoai,context);
        spLoaiHang.setAdapter(filterTyeItemImportAdapter);
        spLoaiHang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                importItemsList=new ArrayList<>();
                importDao=new ImportDao(context);
                importItemsList=importDao.getAllByMaLoaiHang(String.valueOf(importListLoai.get(position).getMaLoaiHang()));
                spinnerItemsAdapter=new SpinnerItemsAdapter(importItemsList,context);
                spTenHang.setAdapter(spinnerItemsAdapter);

                if(possition!=0){
                    for(int i=0;i<importItemsList.size();i++){
                        if(obj.getTenHang().equals(importItemsList.get(i).getTenHang())){
                            positionIP=i;
                        }
                    }
                    spTenHang.setSelection(positionIP);
                    spTenHang.setEnabled(false);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        if(possition!=0){
            tvMaXuat.setText(String.valueOf(obj.getMaXuatHang()));
            for(int i=0;i<importListLoai.size();i++){
                if(obj.getTenLoaiHang().equals(importListLoai.get(i).getTenLoaiHang())){
                    positionTL=i;
                }
            }
            spLoaiHang.setSelection(positionTL);
            spLoaiHang.setEnabled(false);
            edSoLuongXuat.setText(String.valueOf(obj.getSoLuongXuat()));
            edDonGia.setText(String.valueOf(obj.getDonGiaXuat()));
            edNgayXuatHang.setText(sdf.format(obj.getNgayXuatHang()));
        }

        edNgayXuatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPickDate(edNgayXuatHang);
            }
        });

        dialog.findViewById(R.id.btnSaveXuatHang).setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                obj=new ExportItems();
                dao=new ExportDao(context);
                importItems=(ImportItems) spTenHang.getSelectedItem();
                obj.setTenHang(importItems.getTenHang());
                obj.setTenLoaiHang(importItems.getTenLoaiHang());
                try {
                    obj.setSoLuongNhap(importItems.getSoLuongNhap());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    obj.setSoLuongXuat(Integer.parseInt(edSoLuongXuat.getText().toString()));
                }catch (NumberFormatException e){
                    e.printStackTrace();
                }
                try {
                    obj.setDonGiaXuat(Float.parseFloat(edDonGia.getText().toString()));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                try {
                    obj.setDonGia(importItems.getDonGia());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    obj.setNgayXuatHang(sdf.parse(edNgayXuatHang.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    obj.setMaNhapHang(importItems.getMaNhapHang());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                obj.setUsername(getUser());

                if(checkInput()>0){
                    boolean checkHang=dao.checkHang(importItems.getTenHang(),getUser());
                    if(possition==0){
                        if (!checkHang) {
                            if (dao.insert(obj) > 0) {
                                customToasts.successToast(context, "Thêm thành công!");
                                dialog.dismiss();
                            } else {
                                customToasts.errorToast(context, "Thêm thất bại");
                            }
                        }else {
                            customToasts.errorToast(context,"Hàng đã được xuất, vui lòng xuất hàng khác!!!");
                        }
                    }else {
                        obj.setMaXuatHang(Integer.parseInt(tvMaXuat.getText().toString()));
                        if (dao.update(obj)>0){
                            customToasts.successToast(context,"Cập nhật thành công!");
                            dialog.dismiss();
                        }else {
                            customToasts.errorToast(context,"Cập nhật thất bại");
                        }
                    }
                    reLoadData();
                    dialog.dismiss();
                }


            }
        });
        dialog.findViewById(R.id.btnCancelSaveXuatHan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();


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
                    reLoadData();
                    dialog.dismiss();
                }catch (Exception e){
                    customToasts.errorToast(getContext(),"Xóa thất bại!!!"+e);
                    e.printStackTrace();
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
    private void reLoadData(){
        dao=new ExportDao(getContext());
        list=new ArrayList<>();
        list=dao.getAllByUsername(getUser());
        adapter=new ExportItemAdapter(list,getContext());
        lv.setAdapter(adapter);
    }
    private void searchByName(String name){
        dao=new ExportDao(getContext());
        list=dao.searchByName(name);
        adapter=new ExportItemAdapter(list,getContext());
        if(list!=null){
            lv.setAdapter(adapter);
        }
    }


    private void dialogPickDate(TextInputEditText edText){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        edText.setText(year  + "-"+(monthOfYear + 1) +"-" + dayOfMonth);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    private String getUser(){
        MainActivity activity=(MainActivity) getActivity();
        String username=activity.getUsername();
        return username;
    }
    private int checkInput()  {
        int check=1;
        importItems=(ImportItems) spTenHang.getSelectedItem();
        if(edSoLuongXuat.getText().toString().isEmpty()){
            tilSoLuongXuat.setError("Chưa nhập số lượng xuất!!!");
            check=-1;
        }else{
            try {
                if(!edSoLuongXuat.getText().toString().matches("\\d+")) {
                    tilSoLuongXuat.setError("Số lượng phải là số");
                    check = -1;
                }
                if (Integer.parseInt("" + edSoLuongXuat.getText().toString()) > importItems.getSoLuongNhap()) {
                    tilSoLuongXuat.setError("Số lượng xuất phải nhỏ hơn hoặc bằng số lượng nhập: "+importItems.getSoLuongNhap());
                    check=-1;
                }else{
                    tilSoLuongXuat.setError("");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(edDonGia.getText().toString().isEmpty()){
            tilDonGia.setError("Chưa nhập đơn giá!!");
            check=-1;
        }else{
            if (!edDonGia.getText().toString().matches("[+-]?([0-9]*[.])?[0-9]+")) {
                tilDonGia.setError("Đơn giá không đúng!!!");
                check = -1;
            }else {
                tilDonGia.setError("");
            }
        }
        if(edNgayXuatHang.getText().toString().isEmpty()){
            tilNgayXuat.setError("Chưa chọn ngày xuất hàng");
            check=-1;
        }else{
            try {
                if(sdf.parse(edNgayXuatHang.getText().toString()).before(importItems.getNgayNhapHang())){
                    tilNgayXuat.setError("Ngày xuất hàng phải sau ngày nhập hàng: "+sdf.format(importItems.getNgayNhapHang()));
                    check=-1;
                }else{
                    tilNgayXuat.setError("");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return check;
    }

    @Override
    public void onResume() {
        super.onResume();
        reLoadData();
    }
}
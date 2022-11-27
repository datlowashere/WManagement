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
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.edu.project1.Adapter.ExportItemAdapter;
import com.edu.project1.Adapter.ImportItemsAdapter;
import com.edu.project1.Adapter.SpinnerItemsAdapter;
import com.edu.project1.Dao.ExportDao;
import com.edu.project1.Dao.ImportDao;
import com.edu.project1.Helper.CustomToasts;
import com.edu.project1.MainActivity;
import com.edu.project1.Models.ExportItems;
import com.edu.project1.Models.ImportItems;
import com.edu.project1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

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
    private ImportItemsAdapter importItemsAdapter;
    private List<ImportItems>importItemsList;
    private SpinnerItemsAdapter spinnerItemsAdapter;

    private ListView lv;
    private FloatingActionButton fab;
    private SearchView searchView;
    private ImageView img;

    private Dialog dialog;
    private TextInputEditText edSoLuongXuat,edDonGia,edNgayXuatHang;
    private TextView tvMaXuat,tvTenLoaiHang;
    private Spinner spTenHang;

    int positionIP;

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    CustomToasts customToasts=new CustomToasts();

    public ExportFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_export, container, false);
        lv=view.findViewById(R.id.lvExport);
        fab=view.findViewById(R.id.fabExport);
        searchView=view.findViewById(R.id.svExport);

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

        searchView.setQueryHint("search...");
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


        return view;
    }

    private void diaLogExport(final Context context,final int possition){
        dialog=new Dialog(context);
        dialog.setContentView(R.layout.dialog_export_item);
        dialog.getWindow().setWindowAnimations(R.style.animationDialog);

        tvMaXuat=dialog.findViewById(R.id.tvMaXH);
        tvMaXuat.setVisibility(View.GONE);
        spTenHang=dialog.findViewById(R.id.spTenHang);
        tvTenLoaiHang=dialog.findViewById(R.id.tvLoaiHangX);
        edSoLuongXuat=dialog.findViewById(R.id.edSoLuongXuat);
        edDonGia=dialog.findViewById(R.id.edDonGiaXuat);
        edNgayXuatHang=dialog.findViewById(R.id.edNgayXuatHang);


        importItemsList=new ArrayList<>();
        importDao=new ImportDao(context);
        importItemsList=importDao.getAllByUsername(getUser());
        spinnerItemsAdapter=new SpinnerItemsAdapter(importItemsList,context);
        spTenHang.setAdapter(spinnerItemsAdapter);


        if(possition!=0){
            tvMaXuat.setText(String.valueOf(obj.getMaXuatHang()));
            for(int i=0;i<importItemsList.size();i++){
                if(obj.getTenHang().equals(importItemsList.get(i).getTenHang())){
                    positionIP=i;
                }
            }

            spTenHang.setSelection(positionIP);
            tvTenLoaiHang.setText(obj.getTenLoaiHang());
            edSoLuongXuat.setText(String.valueOf(obj.getSoLuongXuat()));
            edDonGia.setText(String.valueOf(obj.getDonGia()));
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
                }catch (Exception e){

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
                    if(possition==0){
                        if(dao.insert(obj)>0){
                            customToasts.successToast(context,"Thêm thành công!");
                            dialog.dismiss();
                        }else{
                            customToasts.errorToast(context,"Thêm thất bại");
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
                }
                reLoadData();
                dialog.dismiss();

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
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        edText.setText(dayOfMonth  + "/"+(monthOfYear + 1) +"/" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    private String getUser(){
        MainActivity activity=(MainActivity) getActivity();
        String username=activity.getUsername();
        return username;
    }
    private int checkInput(){
        int check=1;
//        if(edDonGia.getText().toString().isEmpty() || edSoLuongXuat.getText().toString().isEmpty() || edNgayXuatHang.getText().toString().isEmpty()){
//            customToasts.warningToast(getContext(),"Phải điền đầy đủ thông tin");
//            check=-1;
//        }
//        if(!edSoLuongXuat.getText().toString().matches("[0-9]")){
//            customToasts.errorToast(getContext(),"Số lượng phải là số!");
//            check=-1;
//        }
//        if(!edDonGia.getText().toString().matches("[+-]?([0-9]*[.])?[0-9]+")){
//            customToasts.errorToast(getContext(),"Đơn giá không đúng !");
//            check=-1;
//        }
        return check;
    }
}
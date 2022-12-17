package com.edu.project1.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.project1.Activities.ChangeInformationActivity;
import com.edu.project1.Adapter.FeaturesAdapter;
import com.edu.project1.Dao.ReportDao;
import com.edu.project1.Dao.UserDao;
import com.edu.project1.MainActivity;
import com.edu.project1.Models.FeaturesModel;
import com.edu.project1.Models.TypeItems;
import com.edu.project1.Models.User;
import com.edu.project1.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class DasboardFragment extends Fragment {

    private TextView tvHello,tvUser,tvNameWareHouse;
    private CircleImageView img;
    private GridView gridView;
    private List<FeaturesModel> featureList;
    private FeaturesAdapter featuresAdapter;

    public DasboardFragment() {
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
        View view=inflater.inflate(R.layout.fragment_dasboard, container, false);

        tvHello=view.findViewById(R.id.tvHelloToolbar);
        tvUser=view.findViewById(R.id.tvNameUserr);
        tvNameWareHouse=view.findViewById(R.id.tvNameWareHouse);
        img=view.findViewById(R.id.imgUser);
        MainActivity activity=(MainActivity)getActivity();
        String username=activity.getUsername();

        UserDao dao=new UserDao(getActivity());
        User obj=dao.getID(username);
        String nameUser=obj.getHoTen();
        String nameW=obj.getTenKhoHang();

        Bitmap bitmap= BitmapFactory.decodeByteArray(obj.getImg(),0,obj.getImg().length);
        img.setImageBitmap(bitmap);

        tvUser.setText(nameUser);
        tvNameWareHouse.setText(nameW);

        gridView=view.findViewById(R.id.gridFeatures);

        featureList=new ArrayList<>();
        featureList.add(new FeaturesModel("Nhập Hàng","Thêm hàng vào kho ","Loại: "+tongLoai()+" - Nhập: "+tongNhap(), R.drawable.im));
        featureList.add(new FeaturesModel("Xuất Hàng","Xuất hàng khỏi kho","Xuất:  "+tongXuat(), R.drawable.ex));
        featureList.add(new FeaturesModel("Thống Kê","Báo cáo tổng quan","", R.drawable.barchart));
        featureList.add(new FeaturesModel("Tài Khoản","Thông tin tài khoản","", R.drawable.account));
        featuresAdapter=new FeaturesAdapter(featureList,getContext());
        gridView.setAdapter(featuresAdapter);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }
    private String getUsername(){
        MainActivity activity=(MainActivity)getActivity();
        String username=activity.getUsername();
        return username;
    }
    private int tongLoai(){
        ReportDao dao=new ReportDao(getContext());
        int tongLoai=dao.getTongLoaiHang(getUsername());
        return tongLoai;
    }
    private int tongNhap(){
        ReportDao dao=new ReportDao(getContext());
        int tongNhap=dao.getTongNhapHang(getUsername());
        return tongNhap;
    }
    private int tongXuat(){
        ReportDao dao=new ReportDao(getContext());
        int tongXuat=dao.getTongXuatHang(getUsername());
        return tongXuat;
    }
}
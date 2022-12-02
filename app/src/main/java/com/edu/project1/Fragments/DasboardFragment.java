package com.edu.project1.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

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
//        ReportDao reportDao=new ReportDao(getContext());
//        int tongLoai=reportDao.getSoTongLoai(username);
//        int tongSoNhap=reportDao.getTongSoLuongNhap(username);
//        int tongSoXuat=reportDao.getTongSoLuongXuat(username);
//        int tongSoTon=reportDao.getTongSoLuongTon(username);
//        float tongTienNhap=reportDao.getTongTienNhap(username);
//        float tongTienXuat=reportDao.getTongTienXuat(username);


        featureList=new ArrayList<>();
        featureList.add(new FeaturesModel("Loại hàng","Tổng: ","", R.drawable.box));
        featureList.add(new FeaturesModel("Nhập hàng","Nhập: ","Tiền: ", R.drawable.im));
        featureList.add(new FeaturesModel("Xuất hàng","Xuất: ","Tiền: ", R.drawable.ex));
        featureList.add(new FeaturesModel("Hàng Tồn","Tổng: ","", R.drawable.inventory));
//        featureList.add(new FeaturesModel("Doanh số","","", R.drawable.financial_profit));
//        featureList.add(new FeaturesModel("Top hàng","","", R.drawable.warehouse));
        featuresAdapter=new FeaturesAdapter(featureList,getContext());
        gridView.setAdapter(featuresAdapter);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }
}
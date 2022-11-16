package com.edu.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.project1.Adapter.FeaturesAdapter;
import com.edu.project1.Dao.UserDao;
import com.edu.project1.Fragments.AccountFragment;
import com.edu.project1.Fragments.DasboardFragment;
import com.edu.project1.Fragments.ExportFragment;
import com.edu.project1.Fragments.ImportFragment;
import com.edu.project1.Fragments.ReportFragment;
import com.edu.project1.Models.FeaturesModel;
import com.edu.project1.Models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private TextView tvHello,tvUser,tvNameWareHouse;
    private ImageView img;
    private GridView gridView;
    private List<FeaturesModel> featureList;
    private FeaturesAdapter featuresAdapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView=findViewById(R.id.myBottomNavigationView);

        toolbar=findViewById(R.id.toolbar);
        tvHello=findViewById(R.id.tvHelloToolbar);
        tvUser=findViewById(R.id.tvNameUserr);
        tvNameWareHouse=findViewById(R.id.tvNameWareHouse);
        img=findViewById(R.id.imgUser);

        gridView=findViewById(R.id.gridFunntion);
        setSupportActionBar(toolbar);


        Intent ilogin=getIntent();
        String username=ilogin.getStringExtra("username");

        UserDao dao=new UserDao(this);
        User item=dao.getID(username);
        String name=item.getHoTen();
        String nameW=item.getTenKhoHang();

        ActionBar actionBar=getSupportActionBar();
//        actionBar.setTitle("Xin  chào \n"+name);
        tvUser.setText(name);
        tvNameWareHouse.setText(nameW);

        replaceFragment(new DasboardFragment());
        bottomNavigationView.setSelectedItemId(R.id.dashboard);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dashboard:
                        replaceFragment(new DasboardFragment());
                        return true;
                    case R.id.importproduct:
                        replaceFragment(new ImportFragment());
                        return true;
                    case R.id.exportpproduct:
                        replaceFragment(new ExportFragment());
                        return true;
                    case R.id.reportproduct:
                        replaceFragment(new ReportFragment());
                        return true;
                    case R.id.logout:
                        replaceFragment(new AccountFragment());
                        return true;
                }
                return false;
            }
        });

        featureList=new ArrayList<>();
        featureList.add(new FeaturesModel("Loại hàng nhập", R.drawable.box));
        featureList.add(new FeaturesModel("Nhập hàng", R.drawable.im));
        featureList.add(new FeaturesModel("Xuất hàng", R.drawable.ex));
        featureList.add(new FeaturesModel("Hàng Tồn", R.drawable.inventory));
        featureList.add(new FeaturesModel("Doanh số", R.drawable.financial_profit));
        featureList.add(new FeaturesModel("Top hàng", R.drawable.warehouse));
        featuresAdapter=new FeaturesAdapter(featureList,MainActivity.this);
        gridView.setAdapter(featuresAdapter);

//        img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });






    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }
}
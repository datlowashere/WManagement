package com.edu.project1.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu.project1.Adapter.View2PagerImportAdapter;
import com.edu.project1.Dao.ReportDao;
import com.edu.project1.MainActivity;
import com.edu.project1.R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class ImportFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private View2PagerImportAdapter adapter;

    public ImportFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_import, container, false);



        tabLayout=view.findViewById(R.id.tabLayoutImport);
        viewPager2=view.findViewById(R.id.view2PagerImport);
        adapter=new View2PagerImportAdapter(requireActivity().getSupportFragmentManager(),getLifecycle());
        viewPager2.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(position==0){
                    tab.setText("Loại hàng");
//                    BadgeDrawable badgeDrawable=tab.getOrCreateBadge();
//                    badgeDrawable.setBackgroundColor(
//                            ContextCompat.getColor(getActivity(), android.R.color.holo_blue_dark)
//                    );
//                    badgeDrawable.setVisible(true);
//                    badgeDrawable.setNumber(badgeDrawable.getNumber()+tongLoai());
//                    badgeDrawable.setMaxCharacterCount(3);

                }else{
                    tab.setText("Nhập hàng");
//                    BadgeDrawable badgeDrawable=tab.getOrCreateBadge();
//                    badgeDrawable.setBackgroundColor(
//                            ContextCompat.getColor(getActivity(), android.R.color.holo_blue_dark)
//                    );
//                    badgeDrawable.setVisible(true);
//                    badgeDrawable.setNumber(badgeDrawable.getNumber()+tongNhap());
//                    badgeDrawable.setMaxCharacterCount(3);
                }
            }
        }).attach();

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
        Log.i("TỔNG LOẠI:",""+tongLoai);
        return tongLoai;
    }
    private int tongNhap(){
        ReportDao dao=new ReportDao(getContext());
        int tongNhap=dao.getTongNhapHang(getUsername());
        Log.i("TỔNG NHẬP:",""+tongNhap);
        return tongNhap;
    }

}
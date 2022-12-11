package com.edu.project1.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edu.project1.Adapter.View2PagerReportAdapter;
import com.edu.project1.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.textfield.TextInputEditText;


public class ReportFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private View2PagerReportAdapter adapter;

    public ReportFragment() {
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
        View view=inflater.inflate(R.layout.fragment_report, container, false);
        tabLayout=view.findViewById(R.id.tabLayoutReport);
        viewPager2=view.findViewById(R.id.view2PagerReport);
        adapter=new View2PagerReportAdapter(requireActivity().getSupportFragmentManager(),getLifecycle());
        viewPager2.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(position==0){
                    tab.setText("Hàng tồn");
                }else if(position==1){
                    tab.setText("Doanh số");
                }else{
                    tab.setText("Top hàng");
                }
            }
        }).attach();
        return view;
    }
}
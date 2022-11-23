package com.edu.project1.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu.project1.Adapter.View2PagerImportAdapter;
import com.edu.project1.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class ImportFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private View2PagerImportAdapter adapter;

    public ImportFragment() {
    }

    public static ImportFragment newInstance(String param1, String param2) {
        ImportFragment fragment = new ImportFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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
                }else{
                    tab.setText("Nhập hàng");
                }
            }
        }).attach();

        return view;
    }

}
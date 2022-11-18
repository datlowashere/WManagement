package com.edu.project1.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.edu.project1.Fragments.InventoryItemsFragment;
import com.edu.project1.Fragments.RevenueReportFragment;
import com.edu.project1.Fragments.TopItemsFragment;

public class View2PagerReportAdapter extends FragmentStateAdapter {
    public View2PagerReportAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        if(position==0){
             fragment=new InventoryItemsFragment();
        }else if(position==1){
             fragment=new RevenueReportFragment();
        }else{
             fragment=new TopItemsFragment();
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

package com.edu.project1.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.edu.project1.Fragments.ImportItemFragment;
import com.edu.project1.Fragments.TypeItemFragment;

public class View2PagerImportAdapter extends FragmentStateAdapter {

    public View2PagerImportAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        if (position==0){
            fragment=new TypeItemFragment();
        }else{
            fragment=new ImportItemFragment();
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

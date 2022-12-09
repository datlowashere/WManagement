package com.edu.project1.Fragments;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.edu.project1.Adapter.InventoryItemsAdapter;
import com.edu.project1.Dao.ReportDao;
import com.edu.project1.MainActivity;
import com.edu.project1.Models.InventoryItems;
import com.edu.project1.R;

import java.util.ArrayList;
import java.util.List;


public class InventoryItemsFragment extends Fragment {

    private ListView lv;

    private List<InventoryItems> list;
    private ReportDao dao;
    private InventoryItemsAdapter adapter;

    public InventoryItemsFragment() {
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
        View view=inflater.inflate(R.layout.fragment_inventory_items_fragments, container, false);
        lv=view.findViewById(R.id.lvInventory);
        reloadData();
        return view;
    }
    private void reloadData(){
        list=new ArrayList<>();
        dao=new ReportDao(getContext());
        list=dao.getAllHangTon(getUsername());
        adapter=new InventoryItemsAdapter(list,getContext());
        lv.setAdapter(adapter);
    }
    private String getUsername(){
        MainActivity activity=(MainActivity)getActivity();
        String username=activity.getUsername();
        return  username;
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadData();
    }
}
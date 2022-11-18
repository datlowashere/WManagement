package com.edu.project1.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu.project1.R;


public class TopItemsFragment extends Fragment {


    public TopItemsFragment() {
        // Required empty public constructor
    }


    public static TopItemsFragment newInstance(String param1, String param2) {
        TopItemsFragment fragment = new TopItemsFragment();
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
        return inflater.inflate(R.layout.fragment_top_items, container, false);
    }
}
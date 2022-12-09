package com.edu.project1.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.edu.project1.Models.AppInfor;
import com.edu.project1.R;

import java.util.ArrayList;
import java.util.List;

public class InformationAppActivity extends AppCompatActivity {

    private ListView lv;
    private List<AppInfor>list;
    private AppInforAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_show_information_app);
        lv=findViewById(R.id.lvInforApp);

        list=new ArrayList<>();
        list.add(new AppInfor("Tên App","Warehouse Management"));
        list.add(new AppInfor("Đề Tài","Quản Lý Kho Hàng"));
        list.add(new AppInfor("Môn","PRO1121 - Dự án 1"));
        list.add(new AppInfor("Thực Hiện","Lô Tiến Đạt"));
        list.add(new AppInfor("Lớp","CP17301/FA2022"));
        list.add(new AppInfor("Cập Nhật Lần Cuối","0:00 AM 05/12/2022"));
        list.add(new AppInfor("","@datlowashere"));
        adapter= new AppInforAdapter(list, this);
        lv.setAdapter(adapter);

    }

    private static class AppInforAdapter extends BaseAdapter {
        private final List<AppInfor>list;
        private final Context context;

        public AppInforAdapter(List<AppInfor> list, Context context) {
            this.list = list;
            this.context = context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
        public static class viewOfItem{
            TextView tvTitle,tvName;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            viewOfItem viewOfItem;
            if(view==null){
                view= LayoutInflater.from(context).inflate(R.layout.item_infor_app,parent,false);
                viewOfItem= new viewOfItem();
                viewOfItem.tvTitle=view.findViewById(R.id.tvTitleInforApp);
                viewOfItem.tvName=view.findViewById(R.id.tvNameInforApp);
                view.setTag(viewOfItem);
            }else {
                viewOfItem=(AppInforAdapter.viewOfItem) view.getTag();
            }
            AppInfor obj=list.get(position);
            viewOfItem.tvTitle.setText(obj.getTitle());
            viewOfItem.tvName.setText(obj.getName());
            return view;
        }
    }
}
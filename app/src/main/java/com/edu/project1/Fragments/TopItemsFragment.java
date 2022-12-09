package com.edu.project1.Fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.edu.project1.Dao.ReportDao;
import com.edu.project1.Helper.CustomToast;
import com.edu.project1.MainActivity;
import com.edu.project1.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class TopItemsFragment extends Fragment {

    private TextView tvTuNgay,tvDenNgay;
    private ReportDao dao;
    private BarChart barChartNhap,barChartXuat;

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    CustomToast customToasts=new CustomToast();

    public TopItemsFragment() {
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
        View view=inflater.inflate(R.layout.fragment_top_items, container, false);
        tvTuNgay=view.findViewById(R.id.tvTuNgayTopFragment);
        tvDenNgay=view.findViewById(R.id.tvDenNgayTopFragment);

        barChartNhap=view.findViewById(R.id.barchartTopNhap);
        barChartXuat=view.findViewById(R.id.barchartTopXuất);

        tvTuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDatePicker(tvTuNgay);
            }
        });
        tvDenNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDatePicker(tvDenNgay);
            }
        });

        view.findViewById(R.id.btnTopHang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tvTuNgay.getText().toString().isEmpty() || tvDenNgay.getText().toString().isEmpty()){
                    customToasts.warningToast(getContext(),"Chưa chọn khoảng ngày");
                }else{
                    try {
                        if(sdf.parse(tvTuNgay.getText().toString()).after(sdf.parse(tvDenNgay.getText().toString()))){
                            customToasts.warningToast(getContext(),"Ngày bắt đầu phải trước ngày kết thúc");
                        }else{
                            customToasts.successToast(getContext(),"Kéo xuống để check chi tiết :3");
                            barchartTopNhapKho();
                            barchartTopXuatKho();
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return view;
    }

    private void barchartTopXuatKho() {
        dao=new ReportDao(getContext());
        ArrayList<BarEntry> entries=new ArrayList<>();

        Integer[] sl=dao.getDSTopSLXuat(getUsername(),tvTuNgay.getText().toString(),tvDenNgay.getText().toString());
        String[] ten = dao.getDSTenTopSLXuat(getUsername(),tvTuNgay.getText().toString(),tvDenNgay.getText().toString());


        for(int i=0;i<sl.length;i++){
            entries.add(new BarEntry(i,sl[i]));
        }
        BarDataSet barDataSet=new BarDataSet(entries,"chú thích");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData barData=new BarData(barDataSet);
        barChartXuat.setData(barData);
        barChartXuat.notifyDataSetChanged();

        XAxis xAxis=barChartXuat.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(ten));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setCenterAxisLabels(false);
        xAxis.setLabelCount(ten.length);
        xAxis.setLabelRotationAngle(70);


        YAxis yAxis=barChartXuat.getAxis(YAxis.AxisDependency.LEFT);
        YAxis yAxis1=barChartXuat.getAxis(YAxis.AxisDependency.RIGHT);

        yAxis1.setStartAtZero(true);
        yAxis.setStartAtZero(true);
        yAxis.setDrawGridLines(false);

        yAxis.setGranularity(1.0f);
        yAxis.setGranularityEnabled(true);

        yAxis1.setGranularity(1.0f);
        yAxis1.setGranularityEnabled(true);

        barChartXuat.getXAxis().setSpaceMax(0.5f);
        barChartXuat.getDescription().setEnabled(true);
        barChartXuat.getDescription().setText("Số Lượng");
        barChartXuat.getDescription().setPosition(100, 50);
        barChartXuat.setFitBars(true);
        barChartXuat.animateY(3000);

    }

    private void barchartTopNhapKho() {
        dao=new ReportDao(getContext());
        ArrayList<BarEntry> entries=new ArrayList<>();

        Integer[] sl=dao.getDSTopSLNhap(getUsername(),tvTuNgay.getText().toString(),tvDenNgay.getText().toString());
        String[] ten = dao.getDSTenTopSLNhap(getUsername(),tvTuNgay.getText().toString(),tvDenNgay.getText().toString());

        for(int i=0;i<sl.length;i++){
            entries.add(new BarEntry(i,sl[i]));
        }
        BarDataSet barDataSet=new BarDataSet(entries,"Chú thích");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData barData=new BarData(barDataSet);
        barChartNhap.setData(barData);
        barChartNhap.notifyDataSetChanged();

        XAxis xAxis=barChartNhap.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(ten));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setCenterAxisLabels(false);
        xAxis.setLabelCount(ten.length);
        xAxis.setLabelRotationAngle(70);


        YAxis yAxis=barChartNhap.getAxis(YAxis.AxisDependency.LEFT);
        YAxis yAxis1=barChartNhap.getAxis(YAxis.AxisDependency.RIGHT);

        yAxis1.setStartAtZero(true);
        yAxis.setStartAtZero(true);
        yAxis.setDrawGridLines(false);

        yAxis.setGranularity(1.0f);
        yAxis.setGranularityEnabled(true);

        yAxis1.setGranularity(1.0f);
        yAxis1.setGranularityEnabled(true);

        barChartNhap.getXAxis().setSpaceMax(0.5f);
        barChartNhap.getDescription().setEnabled(true);
        barChartNhap.getDescription().setText("Số Lượng");
        barChartNhap.getDescription().setPosition(100, 50);
        barChartNhap.setFitBars(true);
        barChartNhap.animateY(3000);
    }

    private void dialogDatePicker(TextView tv){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        tv.setText(year  + "-"+(monthOfYear + 1) +"-" + dayOfMonth);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    private String getUsername(){
        MainActivity activity=(MainActivity)getActivity();
        String username=activity.getUsername();
        return username;
    }

}
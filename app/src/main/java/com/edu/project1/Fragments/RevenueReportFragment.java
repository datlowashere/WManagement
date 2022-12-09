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


public class RevenueReportFragment extends Fragment {

    private TextView tvTuNgay,tvDenNgay;
    private ReportDao dao;
    private BarChart barchartTQ,barChartNhap,barChartXuat;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    CustomToast customToasts=new CustomToast();



    public RevenueReportFragment() {
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
        View view=inflater.inflate(R.layout.fragment_revenue_report, container, false);
        tvTuNgay=view.findViewById(R.id.tvTuNgay);
        tvDenNgay=view.findViewById(R.id.tvDenNgay);
        barChartNhap=view.findViewById(R.id.barchartNhapKho);
        barChartXuat=view.findViewById(R.id.barchartXuatKho);
        barchartTQ=view.findViewById(R.id.barchartTongQuan);

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
        view.findViewById(R.id.btnDoanhThu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tvTuNgay.getText().toString().isEmpty() || tvDenNgay.getText().toString().isEmpty()){
                    customToasts.warningToast(getContext(),"Chưa chọn khoảng ngày");
                }else{
                    try {
                        if(sdf.parse(tvTuNgay.getText().toString()).after(sdf.parse(tvDenNgay.getText().toString()))){
                            customToasts.warningToast(getContext(),"Ngày bắt đầu phải trước ngày kết thúc");
                        }else{
                            customToasts.successToast(getContext(),"Kéo xuống để check doanh thu :3");
                            barchartTQ();
                            barchartNhapKho();
                            barchartXuatKho();
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        return view;
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

    private void barchartTQ(){
        dao=new ReportDao(getContext());
        ArrayList<BarEntry> entries=new ArrayList<>();

        float[] dsTien=dao.getTongNhapXuatTheoGiaiDoan(getUsername(),tvTuNgay.getText().toString(),tvDenNgay.getText().toString());
        float tong=dao.getTongTienNhapXuatTheoGiaiDoan(getUsername(),tvTuNgay.getText().toString(),tvDenNgay.getText().toString());
        String[] ten={"Nhập Kho","Xuất Kho"};

        for(int i=0;i<dsTien.length;i++){
            entries.add(new BarEntry(i,dsTien[i]));
        }
        @SuppressLint("DefaultLocale") BarDataSet barDataSet=new BarDataSet(entries,"Tổng: "+String.format("%,.2f",tong)+" đ");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData barData=new BarData(barDataSet);
        barchartTQ.setData(barData);
        barchartTQ.notifyDataSetChanged();

        XAxis xAxis=barchartTQ.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(ten));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setCenterAxisLabels(false);
        xAxis.setLabelCount(ten.length);


        YAxis yAxis=barchartTQ.getAxis(YAxis.AxisDependency.LEFT);
        YAxis yAxis1=barchartTQ.getAxis(YAxis.AxisDependency.RIGHT);

        yAxis1.setStartAtZero(true);
        yAxis.setStartAtZero(true);
        yAxis.setDrawGridLines(false);

        yAxis.setGranularity(1.0f);
        yAxis.setGranularityEnabled(true);

        yAxis1.setGranularity(1.0f);
        yAxis1.setGranularityEnabled(true);

        barchartTQ.getXAxis().setSpaceMax(0.5f);

        barchartTQ.getDescription().setEnabled(true);
        barchartTQ.getDescription().setText("VNĐ");
        barchartTQ.getDescription().setPosition(100, 50);
        barchartTQ.setFitBars(true);
        barchartTQ.animateY(3000);

    }

    private void barchartNhapKho() {
        dao=new ReportDao(getContext());
        ArrayList<BarEntry> entries=new ArrayList<>();

        Float[] dsTien=dao.getDSTienNhapTheoGiaiDoan(getUsername(),tvTuNgay.getText().toString(),tvDenNgay.getText().toString());
        String[] dsNgay= new String[0];
        try {
            dsNgay = dao.getDSNgayNhapHangTheoGiaiDoan(getUsername(),tvTuNgay.getText().toString(),tvDenNgay.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Float tong=dao.getTongTienNhapTheoGiaiDoan(getUsername(),tvTuNgay.getText().toString(),tvDenNgay.getText().toString());

        for(int i=0;i<dsTien.length;i++){
            entries.add(new BarEntry(i,dsTien[i]));
        }
        BarDataSet barDataSet=new BarDataSet(entries,"Tổng: "+String.format("%,.2f",tong)+" đ");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData barData=new BarData(barDataSet);
        barChartNhap.setData(barData);
        barChartNhap.notifyDataSetChanged();

        XAxis xAxis=barChartNhap.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dsNgay));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setCenterAxisLabels(false);
        xAxis.setLabelCount(dsNgay.length);
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
        barChartNhap.getDescription().setText("VNĐ");
        barChartNhap.getDescription().setPosition(100, 50);
        barChartNhap.setFitBars(true);
        barChartNhap.animateY(3000);


    }
    private void barchartXuatKho(){
        dao=new ReportDao(getContext());
        ArrayList<BarEntry> entries=new ArrayList<>();

        Float[] dsTien=dao.getDSTienXuatTheoGiaiDoan(getUsername(),tvTuNgay.getText().toString(),tvDenNgay.getText().toString());
        String[] dsNgay= new String[0];
        try {
            dsNgay = dao.getDSNgayXuatHangTheoGiaiDoan(getUsername(),tvTuNgay.getText().toString(),tvDenNgay.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Float tong=dao.getTongTienXuatTheoGaiDoan(getUsername(),tvTuNgay.getText().toString(),tvDenNgay.getText().toString());

        for(int i=0;i<dsTien.length;i++){
            entries.add(new BarEntry(i,dsTien[i]));
        }
        BarDataSet barDataSet=new BarDataSet(entries,"Tổng: "+String.format("%,.2f",tong)+" đ");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData barData=new BarData(barDataSet);
        barChartXuat.setData(barData);
        barChartXuat.notifyDataSetChanged();

        XAxis xAxis=barChartXuat.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dsNgay));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setCenterAxisLabels(false);
        xAxis.setLabelCount(dsNgay.length);
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
        barChartXuat.getDescription().setText("VNĐ");
        barChartXuat.getDescription().setPosition(100, 50);
        barChartXuat.setFitBars(true);
        barChartXuat.animateY(3000);

    }

    private String getUsername(){
        MainActivity activity=(MainActivity)getActivity();
        String username=activity.getUsername();
        return username;
    }

}
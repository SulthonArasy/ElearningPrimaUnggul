package com.sulthon.elearningprimaunggul.ui.soal;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.sulthon.elearningprimaunggul.R;

import java.util.ArrayList;

public class SoalAdapter extends RecyclerView.Adapter<SoalAdapter.SoalViewHolder> {


    private ArrayList<Soal> dataList;
    public SoalAdapter(ArrayList<Soal> dataList) {
        this.dataList = dataList;
    }


    @Override
    public SoalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_soal, parent, false);
        return new SoalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SoalViewHolder holder, int position) {
        holder.txtPertanyaan.setText(dataList.get(position).getPertanyaan());
        holder.rgJawaban.setText(dataList.get(position).getJawaban());
        holder.rgJawaban.setText(dataList.get(position).getJawaban());
        holder.rgJawaban.setText(dataList.get(position).getJawaban());
        holder.rgJawaban.setText(dataList.get(position).getJawaban());
        holder.rgJawaban.setText(dataList.get(position).getJawaban());

    }

    @Override
    public int getItemCount() {
        return (dataList != null)? dataList.size() : 0;
    }
    public class SoalViewHolder extends RecyclerView.ViewHolder{
        private TextView txtPertanyaan;
        private RadioButton rgJawaban;

        public SoalViewHolder(View itemView) {
            super(itemView);
            txtPertanyaan = (TextView) itemView.findViewById(R.id.txt_pertanyaan);
            rgJawaban = (RadioButton) itemView.findViewById(R.id.rg_jawaban);
            rgJawaban = (RadioButton) itemView.findViewById(R.id.rg_jawaban);
            rgJawaban = (RadioButton) itemView.findViewById(R.id.rg_jawaban);
            rgJawaban = (RadioButton) itemView.findViewById(R.id.rg_jawaban);
            rgJawaban = (RadioButton) itemView.findViewById(R.id.rg_jawaban);
        }
    }


}

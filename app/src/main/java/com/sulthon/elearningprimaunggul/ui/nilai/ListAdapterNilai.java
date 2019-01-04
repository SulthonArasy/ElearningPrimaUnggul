package com.sulthon.elearningprimaunggul.ui.nilai;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.sulthon.elearningprimaunggul.R;

import java.util.ArrayList;

public class ListAdapterNilai extends RecyclerView.Adapter<ListAdapterNilai.NilaiViewHolder> {


    private ArrayList<Nilai> dataList;
    private Context context;

    public ListAdapterNilai(ArrayList<Nilai> dataList) {

        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public NilaiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_row_nilai, parent, false);
        return new NilaiViewHolder(view);
    }


    @Override
    public void onBindViewHolder(NilaiViewHolder holder, int position) {
        holder.txtNis.setText(dataList.get(position).getNis());
        holder.txtNama.setText(dataList.get(position).getNama());
        holder.txtNilai.setText(dataList.get(position).getNilai());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class NilaiViewHolder extends RecyclerView.ViewHolder{
        private TextView txtNis, txtNama, txtNilai;

        public NilaiViewHolder(View itemView) {
            super(itemView);
            txtNis = (TextView) itemView.findViewById(R.id.txt_nis);
            txtNama = (TextView) itemView.findViewById(R.id.txt_nama_nilai);
            txtNilai = (TextView) itemView.findViewById(R.id.txt_nilai_siswa);
        }
    }
}
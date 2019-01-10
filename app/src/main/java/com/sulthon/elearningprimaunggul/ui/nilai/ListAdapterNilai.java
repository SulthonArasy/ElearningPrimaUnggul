package com.sulthon.elearningprimaunggul.ui.nilai;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sulthon.elearningprimaunggul.R;
import com.sulthon.elearningprimaunggul.data.api.nilai.read.NilaiItem;

import java.util.List;

public class ListAdapterNilai extends RecyclerView.Adapter<ListAdapterNilai.NilaiViewHolder> {
    private List<NilaiItem> dataList;

    ListAdapterNilai(List<NilaiItem> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public NilaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_nilai, parent, false);
        return new NilaiViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull NilaiViewHolder holder, int position) {
        String strNilai = "Nilai " + dataList.get(position).getNilai();
        String strKet = "Dari " + dataList.get(position).getKeterangan() + " soal";
        holder.txtNis.setText(dataList.get(position).getNis());
        holder.txtNama.setText(dataList.get(position).getNamaSiswa());
        holder.txtNilai.setText(strNilai);
        holder.txtKeterangan.setText(strKet);
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    class NilaiViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNis, txtNama, txtNilai, txtKeterangan;

        NilaiViewHolder(View itemView) {
            super(itemView);
            txtNis = itemView.findViewById(R.id.txt_nis);
            txtNama = itemView.findViewById(R.id.txt_nama_nilai);
            txtNilai = itemView.findViewById(R.id.txt_nilai_siswa);
            txtKeterangan = itemView.findViewById(R.id.txt_keterangan);
        }
    }
}
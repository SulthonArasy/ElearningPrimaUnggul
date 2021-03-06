package com.sulthon.elearningprimaunggul.ui.pelajaran;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sulthon.elearningprimaunggul.R;
import com.sulthon.elearningprimaunggul.data.api.pelajaran.read.PelajaranItem;
import com.sulthon.elearningprimaunggul.ui.listmateri.ListMateriActivity;

import java.util.List;

public class PelajaranAdapter extends RecyclerView.Adapter<PelajaranAdapter.PelajaranActivityViewHolder> {

    private List<PelajaranItem> dataList;
    private Activity activity;
    private boolean isGuru;

    PelajaranAdapter(Activity activity, List<PelajaranItem> dataList, boolean isGuru) {
        this.dataList = dataList;
        this.activity = activity;
        this.isGuru = isGuru;
    }

    @NonNull
    @Override
    public PelajaranActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_pelajaran, parent, false);
        return new PelajaranActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PelajaranActivityViewHolder holder, int position) {
        holder.txtNama.setText(dataList.get(position).getNama());
        if (!isGuru) holder.linearOpsi.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    class PelajaranActivityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtNama;
        private CardView cardView;
        private Button btnEdit;
        private Button btnHapus;
        private LinearLayout linearOpsi;

        PelajaranActivityViewHolder(View itemView) {
            super(itemView);
            txtNama = itemView.findViewById(R.id.txt_nama_Pelajaran);
            cardView = itemView.findViewById(R.id.cardview);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnHapus = itemView.findViewById(R.id.btn_hapus);
            linearOpsi = itemView.findViewById(R.id.linear_opsi);

            cardView.setOnClickListener(this);
            btnEdit.setOnClickListener(this);
            btnHapus.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.cardview:
                    Intent i = new Intent(activity, ListMateriActivity.class);
                    i.putExtra("idpelajaran", dataList.get(getAdapterPosition()).getId());
                    activity.startActivity(i);
                    break;
                case R.id.btn_edit:
                    ((ListPelajaranActivity) activity).showUpdatePelajaran(dataList.get(getAdapterPosition()));
                    break;
                case R.id.btn_hapus:
                    ((ListPelajaranActivity) activity).showDeletePelajaran(dataList.get(getAdapterPosition()));
                    break;
            }
        }
    }
}
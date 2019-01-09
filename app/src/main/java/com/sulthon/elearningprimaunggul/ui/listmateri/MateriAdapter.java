package com.sulthon.elearningprimaunggul.ui.listmateri;

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
import com.sulthon.elearningprimaunggul.data.api.materi.read.MateriItem;
import com.sulthon.elearningprimaunggul.ui.materi.MateriActivity;

import java.util.List;

public class MateriAdapter extends RecyclerView.Adapter<MateriAdapter.ActivityListMateriViewHolder> {

    private List<MateriItem> dataList;
    private Activity activity;
    private boolean isGuru;

    MateriAdapter(List<MateriItem> dataList, Activity activity, boolean isGuru) {
        this.dataList = dataList;
        this.activity = activity;
        this.isGuru = isGuru;
    }

    @NonNull
    @Override
    public ActivityListMateriViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_materi, parent, false);
        return new ActivityListMateriViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityListMateriViewHolder holder, int position) {
        holder.txtNamaMateri.setText(dataList.get(position).getNama());
        if (!isGuru) holder.linearOpsi.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    class ActivityListMateriViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView CardViewMateri;
        private TextView txtNamaMateri;
        private Button btnEdit;
        private Button btnHapus;
        private LinearLayout linearOpsi;

        ActivityListMateriViewHolder(View itemView) {
            super(itemView);
            txtNamaMateri = itemView.findViewById(R.id.txt_nama_materi);
            CardViewMateri = itemView.findViewById(R.id.view_materi);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnHapus = itemView.findViewById(R.id.btn_hapus);
            linearOpsi = itemView.findViewById(R.id.linear_opsi);

            CardViewMateri.setOnClickListener(this);
            btnHapus.setOnClickListener(this);
            btnEdit.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.view_materi:
                    Intent i = new Intent(activity, MateriActivity.class);
                    i.putExtra("data", dataList.get(getAdapterPosition()));
                    activity.startActivity(i);
                    break;
                case R.id.btn_edit:
                    ((ListMateriActivity) activity).showUpdateMateri(dataList.get(getAdapterPosition()));
                    break;
                case R.id.btn_hapus:
                    ((ListMateriActivity) activity).showDeleteMateri(dataList.get(getAdapterPosition()));
                    break;
            }
        }
    }
}
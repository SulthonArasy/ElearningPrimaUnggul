package com.sulthon.elearningprimaunggul.ui.materi;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sulthon.elearningprimaunggul.R;
import com.sulthon.elearningprimaunggul.ViewMateriActivity;
import com.sulthon.elearningprimaunggul.data.api.materi.read.MateriItem;

import java.util.List;

public class MateriAdapter extends RecyclerView.Adapter<MateriAdapter.ActivityListMateriViewHolder> {

    private List<MateriItem> dataList;
    private Activity activity;

    MateriAdapter(List<MateriItem> dataList, Activity activity) {
        this.dataList = dataList;
        this.activity = activity;
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

        ActivityListMateriViewHolder(View itemView) {
            super(itemView);
            txtNamaMateri = itemView.findViewById(R.id.txt_nama_materi);
            CardViewMateri = itemView.findViewById(R.id.view_materi);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnHapus = itemView.findViewById(R.id.btn_hapus);

            CardViewMateri.setOnClickListener(this);
            btnHapus.setOnClickListener(this);
            btnEdit.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.view_materi:
                    Intent i = new Intent(activity, ViewMateriActivity.class);
                    i.putExtra("data", dataList.get(getAdapterPosition()));
                    activity.startActivity(i);
                    break;
                case R.id.btn_edit:
                    ((ActivityListMateri) activity).showUpdateMateri(dataList.get(getAdapterPosition()));
                    break;
                case R.id.btn_hapus:
                    ((ActivityListMateri) activity).showDeleteMateri(dataList.get(getAdapterPosition()));
                    break;
            }
        }
    }
}
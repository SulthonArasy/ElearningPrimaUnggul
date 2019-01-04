package com.sulthon.elearningprimaunggul.ui.pelajaran;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sulthon.elearningprimaunggul.R;
import com.sulthon.elearningprimaunggul.ui.materi.ActivityListMateri;

import java.util.ArrayList;

public class PelajaranAdapter extends RecyclerView.Adapter<PelajaranAdapter.PelajaranActivityViewHolder> {

    private ArrayList<Pelajaran> dataList;
    private Context context;

    public PelajaranAdapter(Context context, ArrayList<Pelajaran> dataList) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public PelajaranActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_pelajaran, parent, false);
        return new PelajaranActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PelajaranActivityViewHolder holder, int position) {
        holder.txtNama.setText(dataList.get(position).getNamaPelajaran());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,ActivityListMateri.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class PelajaranActivityViewHolder extends RecyclerView.ViewHolder{
        private TextView txtNama;
        private CardView cardView;

        public PelajaranActivityViewHolder(View itemView) {
            super(itemView);
            txtNama = (TextView) itemView.findViewById(R.id.txt_nama_Pelajaran);
            cardView = itemView.findViewById(R.id.cardview);
        }

    }
}
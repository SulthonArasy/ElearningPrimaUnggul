package com.sulthon.elearningprimaunggul.ui.pelajaran;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sulthon.elearningprimaunggul.R;
import com.sulthon.elearningprimaunggul.data.api.pelajaran.read.PelajaranItem;
import com.sulthon.elearningprimaunggul.ui.materi.ActivityListMateri;

import java.util.List;

public class PelajaranAdapter extends RecyclerView.Adapter<PelajaranAdapter.PelajaranActivityViewHolder> {

    private List<PelajaranItem> dataList;
    private Context context;

    PelajaranAdapter(Context context, List<PelajaranItem> dataList) {
        this.dataList = dataList;
        this.context = context;
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
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    class PelajaranActivityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtNama;
        private CardView cardView;

        PelajaranActivityViewHolder(View itemView) {
            super(itemView);
            txtNama = itemView.findViewById(R.id.txt_nama_Pelajaran);
            cardView = itemView.findViewById(R.id.cardview);

            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(context, ActivityListMateri.class);
            i.putExtra("idpelajaran", dataList.get(getAdapterPosition()).getId());
            context.startActivity(i);
        }
    }
}
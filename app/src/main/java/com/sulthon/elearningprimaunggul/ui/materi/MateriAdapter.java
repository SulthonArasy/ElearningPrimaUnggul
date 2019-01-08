package com.sulthon.elearningprimaunggul.ui.materi;

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
import com.sulthon.elearningprimaunggul.ViewMateriActivity;
import com.sulthon.elearningprimaunggul.data.api.materi.MateriItem;

import java.util.List;

public class MateriAdapter extends RecyclerView.Adapter<MateriAdapter.ActivityListMateriViewHolder> {

    private List<MateriItem> dataList;
    private Context context;

    MateriAdapter(List<MateriItem> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ActivityListMateriViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_materi, parent, false);
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

        ActivityListMateriViewHolder(View itemView) {
            super(itemView);
            txtNamaMateri = itemView.findViewById(R.id.txt_nama_materi);
            CardViewMateri = itemView.findViewById(R.id.view_materi);

            CardViewMateri.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(context, ViewMateriActivity.class);
            i.putExtra("data", dataList.get(getAdapterPosition()));
            context.startActivity(i);
        }
    }
}
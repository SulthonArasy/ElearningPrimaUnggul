package com.sulthon.elearningprimaunggul.ui.materi;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.sulthon.elearningprimaunggul.R;
import com.sulthon.elearningprimaunggul.ViewMateriActivity;

import java.util.ArrayList;

public class MateriAdapter extends RecyclerView.Adapter<MateriAdapter.ActivityListMateriViewHolder> {

    private ArrayList<Materi> dataList;
    private Context context;

    public MateriAdapter(ArrayList<Materi> dataList, Context context) {

        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public ActivityListMateriViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_materi, parent, false);
        return new ActivityListMateriViewHolder(view);
    }

    /*@Override
    public void onBindViewHolder(ActivityListMateriViewHolder holder, int position) {
        holder.txtNamaMateri.setText(dataList.get(position).getNamaMateri());
    }*/

    @Override
    public void onBindViewHolder(ActivityListMateriViewHolder holder, int position) {
        holder.txtNamaMateri.setText(dataList.get(position).getNamaMateri());
        holder.CardViewMateri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,ViewMateriActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class ActivityListMateriViewHolder extends RecyclerView.ViewHolder{
        private CardView CardViewMateri;
        private TextView txtNamaMateri;

        public ActivityListMateriViewHolder(View itemView) {
            super(itemView);
            txtNamaMateri = (TextView) itemView.findViewById(R.id.txt_nama_materi);
            CardViewMateri = itemView.findViewById(R.id.view_materi);
        }
    }
}
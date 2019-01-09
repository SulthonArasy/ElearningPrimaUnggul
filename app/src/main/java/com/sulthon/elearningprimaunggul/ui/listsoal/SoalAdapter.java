package com.sulthon.elearningprimaunggul.ui.listsoal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sulthon.elearningprimaunggul.R;
import com.sulthon.elearningprimaunggul.data.api.soal.read.SoalItem;

import java.util.List;

public class SoalAdapter extends RecyclerView.Adapter<SoalAdapter.ActivityListMateriViewHolder> {

    private List<SoalItem> dataList;
    private Activity activity;

    SoalAdapter(List<SoalItem> dataList, Activity activity) {
        this.dataList = dataList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ActivityListMateriViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_soal, parent, false);
        return new ActivityListMateriViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ActivityListMateriViewHolder holder, int position) {
        String no = (position + 1) + ". ";
        holder.txtNo.setText(no);
        holder.txtPertanyaan.setText(dataList.get(position).getPertanyaan());
        switch (dataList.get(position).getJawaban()) {
            case "jwb_a":
                holder.rbA.setChecked(true);
                break;
            case "jwb_b":
                holder.rbB.setChecked(true);
                break;
            case "jwb_c":
                holder.rbC.setChecked(true);
                break;
            case "jwb_d":
                holder.rbD.setChecked(true);
                break;
            case "jwb_e":
                holder.rbE.setChecked(true);
                break;
            default:
                holder.rbA.setChecked(true);
                break;
        }
        holder.radioGroup.setActivated(false);
        holder.btnHapus.setOnClickListener(null);
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    class ActivityListMateriViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNo, txtPertanyaan;
        private Button btnHapus;
        private RadioGroup radioGroup;
        private RadioButton rbA, rbB, rbC, rbD, rbE;

        ActivityListMateriViewHolder(View v) {
            super(v);

            txtNo = v.findViewById(R.id.txt_nomor);
            txtPertanyaan = v.findViewById(R.id.txt_pertanyaan);
            btnHapus = v.findViewById(R.id.btn_hapus);
            radioGroup = v.findViewById(R.id.radioGroup);
            rbA = v.findViewById(R.id.rb_a);
            rbB = v.findViewById(R.id.rb_b);
            rbC = v.findViewById(R.id.rb_c);
            rbD = v.findViewById(R.id.rb_d);
            rbE = v.findViewById(R.id.rb_e);

            rbA.setEnabled(false);
            rbB.setEnabled(false);
            rbC.setEnabled(false);
            rbD.setEnabled(false);
            rbE.setEnabled(false);
        }
    }
}
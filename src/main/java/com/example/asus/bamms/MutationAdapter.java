package com.example.asus.bamms;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.bamms.Interface.ItemClickListener;
import com.example.asus.bamms.Model.Transaction;
import com.example.asus.bamms.ViewHolder.mutationViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MutationAdapter extends RecyclerView.Adapter<mutationViewHolder>  {
    List<Transaction> listData = new ArrayList<>();

    Context context;

    ItemClickListener clickListener;

    public MutationAdapter(List<Transaction> listData, Context context, ItemClickListener clickListener) {
        this.listData = listData;
        this.context = context;
        this.clickListener = clickListener;
    }


    @NonNull
    @Override
    public mutationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.mutation_item, parent, false);

        mutationViewHolder viewHolder = new mutationViewHolder(v);
        viewHolder.setItemClickListener(clickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull mutationViewHolder holder, int position) {
        holder.mutation_date.setText("Date: "+listData.get(position).getDate());
        holder.mutation_type.setText("Transaction type: "+listData.get(position).getType());
        holder.mutation_amount.setText("Amount: "+listData.get(position).getAmount().toString()+",00 rupiah");
        holder.mutation_to.setText("Receiver number: "+(listData.get(position).getReceivernum().equals("")?"-":listData.get(position).getReceivernum()));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}

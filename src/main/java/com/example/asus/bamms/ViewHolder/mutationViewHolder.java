package com.example.asus.bamms.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.asus.bamms.Interface.ItemClickListener;
import com.example.asus.bamms.R;

public class mutationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mutation_date,mutation_type,mutation_amount,mutation_to;


    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public mutationViewHolder(View itemView) {
        super(itemView);

        mutation_date = (TextView)itemView.findViewById(R.id.mutationdate);
        mutation_type = (TextView)itemView.findViewById(R.id.mutationtype);
        mutation_amount = (TextView)itemView.findViewById(R.id.mutationamount);
        mutation_to = (TextView)itemView.findViewById(R.id.mutationfromto);

        itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}

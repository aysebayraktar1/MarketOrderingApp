package com.example.yeni.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yeni.Interface.ItemClickListener;
import com.example.yeni.R;

public class ProductsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView product_name;
    public ImageView product_image;
    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ProductsViewHolder(View itemView) {
        super(itemView);
        product_name =itemView.findViewById(R.id.product_name);
        product_image =itemView.findViewById(R.id.product_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view , getAdapterPosition() , false);
    }
}

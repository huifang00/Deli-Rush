package com.example.delirush.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.delirush.CartListData;
import com.example.delirush.R;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{
    private ArrayList<CartListData> cartData;

    // Create constructor
    public CartAdapter(ArrayList<CartListData> cartData){
        this.cartData = cartData;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.cart_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CartListData myListData = cartData.get(position);
        holder.foodIndex.setText(cartData.get(position).getFoodIndex());
        holder.food.setText(cartData.get(position).getFood());
        holder.quantity.setText(cartData.get(position).getQuatity());
        holder.total.setText(cartData.get(position).getTotal());
    }

    @Override
    public int getItemCount() {
        return cartData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView foodIndex;
        public TextView food;
        public TextView quantity;
        public TextView total;
        public LinearLayout linearlayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.foodIndex = (TextView) itemView.findViewById(R.id.foodIndex);
            this.food = (TextView) itemView.findViewById(R.id.food);
            this.quantity = (TextView) itemView.findViewById(R.id.quantity);
            this.total = (TextView) itemView.findViewById(R.id.total);
            linearlayout = (LinearLayout)itemView.findViewById(R.id.linearlayout);
        }
    }
}
package com.example.delirush.adapter;
/*****************************
 * Code is referred from
 *    Title: Android RecyclerView List Example
 *    Author: -
 *    Date: -
 *    Code version: 1.0
 *    Availability: https://www.javatpoint.com/android-recyclerview-list-example
 *
 *****************************/
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.delirush.CartListData;
import com.example.delirush.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private final ArrayList<CartListData> cartData;

    /**
     * Create the constructor
     *
     * @param cartData
     */
    public CartAdapter(ArrayList<CartListData> cartData) {
        this.cartData = cartData;
    }

    /**
     * Create new ViewHolder and initialize fields
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.cart_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    /**
     * Update the ViewHolder contents with the item at the given positio
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DecimalFormat df = new DecimalFormat("0.00");
        holder.food.setText(cartData.get(position).getFood());
        holder.quantity.setText(String.valueOf(cartData.get(position).getQuantity()));
        holder.total.setText(df.format(cartData.get(position).getTotal()));
    }

    /**
     * Return the total number of item held by the adapter
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return cartData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView food;
        public TextView quantity;
        public TextView total;
        public LinearLayout linearlayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.food = (TextView) itemView.findViewById(R.id.food);
            this.quantity = (TextView) itemView.findViewById(R.id.quantity);
            this.total = (TextView) itemView.findViewById(R.id.total);
            linearlayout = (LinearLayout) itemView.findViewById(R.id.linearlayout);
        }
    }
}
package com.example.delirush.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.delirush.OrderListData;
import com.example.delirush.R;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{
    private ArrayList<OrderListData> orderData;

    /**
     * Create the constructor
     * @param orderData
     */
    public OrderAdapter(ArrayList<OrderListData>  orderData) {
        this.orderData = orderData;
    }

    /**
     * Create new ViewHolder and initialize fields
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.order_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    /**
     * Update the ViewHolder contents with the item at the given position
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        final OrderListData myListData = orderData.get(position);
        holder.orderID.setText(orderData.get(position).getOrderID());
        holder.orderFoodStall.setText(orderData.get(position).getOrderFoodStall());
        holder.orderStatus.setText(orderData.get(position).getOrderStatus());
    }

    /**
     * Return the total number of item held by the adapter
     * @return
     */
    @Override
    public int getItemCount() {
        return orderData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView orderID;
        public TextView orderFoodStall;
        public TextView orderStatus;
        public RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.orderID = (TextView) itemView.findViewById(R.id.orderID);
            this.orderFoodStall = (TextView) itemView.findViewById(R.id.orderFoodStall);
            this.orderStatus = (TextView) itemView.findViewById(R.id.orderStatus);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
        }
    }
}
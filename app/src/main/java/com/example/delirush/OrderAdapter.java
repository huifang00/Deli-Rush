package com.example.delirush;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{
    private ArrayList<OrderListData> orderData;

    public OrderAdapter(ArrayList<OrderListData>  orderData) {
        this.orderData = orderData;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.order_list_main, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        final OrderListData myListData = orderData.get(position);
        holder.orderID.setText(orderData.get(position).getOrderID());
        holder.orderFoodStall.setText(orderData.get(position).getOrderFoodStall());
        holder.orderStatus.setText(orderData.get(position).getOrderStatus());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"click on item: "+myListData.getOrderID(),Toast.LENGTH_LONG).show();
            }
        });
    }

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
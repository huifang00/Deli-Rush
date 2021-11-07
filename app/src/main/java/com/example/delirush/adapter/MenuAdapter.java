package com.example.delirush.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.delirush.ChineseStallActivity;
import com.example.delirush.HomeActivity;
import com.example.delirush.R;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder>{
    private ArrayList<String> menuData;
    private Activity activity;

    public MenuAdapter(Activity activity, ArrayList<String>  menuData) {
        this.activity = activity;
        this.menuData = menuData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.menu_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Set text on text view
        holder.menu.setText(menuData.get(position));
        holder.menu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Get clicked item position
                int position = holder.getAdapterPosition();
                String price = "";
                // the price should be set by the seller side and retrieve from database, however this was not implemented
                switch (position) {
                    case 0:
                        // price for chicken rice
                        price = "5.50";
                        break;
                    case 1:
                        // price for fried rice
                        price = "6.50";
                        break;
                    case 2:
                        // price for Butter Milk Chicken Rice
                        price = "6.50";
                        break;
                    case 3:
                        // price for Chicken Porridge
                        price = "5.00";
                        break;
                    case 4:
                        // price for Curry Noodle
                        price = "5.00";
                        break;
                }
                Intent intent = new Intent(activity, ChineseStallActivity.class).
                        setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("openDialog", "open");
                intent.putExtra("price", price);
                activity.startActivity(intent);
                // display pop up dialog to set the count
                // selection got add to cart and cancel
                // add to cart then continue
                // place order at cart
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Initialize variable
        TextView menu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Assign variable
            menu = itemView.findViewById(R.id.menu);
        }
    }
}

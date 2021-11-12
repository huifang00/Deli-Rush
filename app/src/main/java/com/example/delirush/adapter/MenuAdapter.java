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
import com.example.delirush.MenuListData;
import com.example.delirush.R;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder>{
    private ArrayList<MenuListData> menuData;
    private Activity activity;

    public MenuAdapter(Activity activity, ArrayList<MenuListData>  menuData) {
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
        holder.menu.setText(menuData.get(position).getFood());
        holder.menu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Get clicked item position
                int position = holder.getAdapterPosition();
                Intent intent = new Intent(activity, ChineseStallActivity.class).
                        setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("openDialog", "open");
                intent.putExtra("food", menuData.get(position).getFood());
                intent.putExtra("price", menuData.get(position).getPrice());
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

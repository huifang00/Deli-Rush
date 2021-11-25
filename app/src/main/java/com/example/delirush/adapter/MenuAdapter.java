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
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.delirush.FoodStallActivity.BeverageStallActivity;
import com.example.delirush.FoodStallActivity.ChineseStallActivity;
import com.example.delirush.FoodStallActivity.MalayStallActivity;
import com.example.delirush.HomeActivity;
import com.example.delirush.MenuListData;
import com.example.delirush.R;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private final ArrayList<MenuListData> menuData;
    private final Activity activity;

    /**
     * Create the constructor
     *
     * @param activity
     * @param menuData
     */
    public MenuAdapter(Activity activity, ArrayList<MenuListData> menuData) {
        this.activity = activity;
        this.menuData = menuData;
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.menu_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    /**
     * Update the ViewHolder contents with the item at the given position
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.menu.setText(menuData.get(position).getFood());
        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Intent intent;
                switch (HomeActivity.getSelectedStall()) {
                    case 0:
                        intent = new Intent(activity, ChineseStallActivity.class).
                                setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        break;
                    case 1:
                        intent = new Intent(activity, MalayStallActivity.class).
                                setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        break;
                    case 2:
                        intent = new Intent(activity, BeverageStallActivity.class).
                                setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + HomeActivity.getSelectedStall());
                }
                intent.putExtra("openDialog", "open");
                intent.putExtra("food", menuData.get(position).getFood());
                intent.putExtra("price", menuData.get(position).getPrice());
                activity.startActivity(intent);
            }
        });
    }

    /**
     * Return the total number of item held by the adapter
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return menuData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView menu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            menu = itemView.findViewById(R.id.menu);
        }
    }
}

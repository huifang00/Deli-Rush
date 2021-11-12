package com.example.delirush.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.delirush.CartActivity;
import com.example.delirush.HomeActivity;
import com.example.delirush.MainActivity;
import com.example.delirush.OrderActivity;
import com.example.delirush.R;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    Activity activity;
    ArrayList<String> arrayList;

    /**
     * Create the constructor
     * @param activity
     * @param arrayList
     */
    public MainAdapter(Activity activity, ArrayList<String> arrayList){
        this.activity = activity;
        this.arrayList = arrayList;
    }

    /**
     * Create new ViewHolder and initialize fields
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.drawer_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    /**
     * Update the ViewHolder contents with the item at the given position
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        // Set text on text view
        holder.textView.setText(arrayList.get(position));
        holder.textView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Get clicked item position
                int position = holder.getAdapterPosition();
                // Check condition
                switch (position){
                    case 0:
                        // When position is equal to 0, navigate to home page
                        activity.startActivity(new Intent(activity, HomeActivity.class).
                                setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        break;
                    case 1:
                        // When position is equal to 1, navigate to cart page
                        activity.startActivity(new Intent(activity, CartActivity.class).
                                setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        break;
                    case 2:
                        // When position is equal to 2, navigate to order page
                        activity.startActivity(new Intent(activity, OrderActivity.class).
                                setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        break;
                    case 3:
                        // When position is equal to 3, proceed to logoout process
                        // Initialize alert dialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setTitle("Logout");
                        builder.setMessage("Are you sure you want to logout?");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Finish all activity
                                activity.finishAffinity();
                                activity.startActivity(new Intent(activity, MainActivity.class).
                                        setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                            }
                        });
                        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                        break;
                }
            }
        });
    }

    /**
     * Return the total number of item held by the adapter
     * @return
     */
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);
        }
    }
}
package com.example.delirush;

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

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    // Initialize variable
    Activity activity;
    ArrayList<String> arrayList;

    // Create constructor
    public MainAdapter(Activity activity, ArrayList<String> arrayList){
        this.activity = activity;
        this.arrayList = arrayList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Initialize view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drawer_main,parent, false);

        return new ViewHolder(view);
    }

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
                        // Set header for toolbar
                        // When position is equal to 0
                        // Redirect to home page
                        activity.startActivity(new Intent(activity, HomeActivity.class).
                                setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case 1:
                        // When position is equal to 1
                        // Redirect to cart page
                        activity.startActivity(new Intent(activity, CartActivity.class).
                                setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case 2:
                        // When position is equal to 2
                        // Redirect to order page
                        activity.startActivity(new Intent(activity, OrderActivity.class).
                                setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case 3:
                        // When position is equal to 3
                        // Initialize alert dialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        // Set title
                        builder.setTitle("Logout");
                        // Set message
                        builder.setMessage("Are you sure you want to logout?");
                        // Positive yes button
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Finish all activity
                                activity.finishAffinity();
                                // Exit app
                                activity.startActivity(new Intent(activity, MainActivity.class).
                                        setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                            }
                        });
                        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Disimiss dialog
                                dialog.dismiss();
                            }
                        });
                        // Show dialog
                        builder.show();
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        // Return array list size
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Initialize variable
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Assign variable
            textView = itemView.findViewById(R.id.text_view);
        }
    }
}
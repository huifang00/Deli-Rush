package com.example.delirush;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.delirush.adapter.MainAdapter;
import com.example.delirush.adapter.OrderAdapter;
import com.example.delirush.service.AlarmService;
import com.example.delirush.service.StatusService;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    //Initialize variable
    DrawerLayout drawerLayout;
    ImageView btMenu;
    RecyclerView recyclerView;
    static ArrayList<String> arrayList = new ArrayList<>();
    MainAdapter adapter;

    public static void closeDrawer(DrawerLayout drawerLayout) {
        // Check condition
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            // When drawer is open
            // Close drawer
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Assign variable
        drawerLayout = findViewById(R.id.drawer_layout);
        btMenu = findViewById(R.id.bt_menu);
        recyclerView = findViewById(R.id.recycler_view);

        // Clear array list
        arrayList.clear();

        // Add menu item in array list
        arrayList.add("Home");
        arrayList.add("My Cart");
        arrayList.add("My Orders");
        arrayList.add("Logout");

        // Initialize adpater
        adapter = new MainAdapter(this, arrayList);

        // Set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set adapter
        recyclerView.setAdapter(adapter);

        btMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open drawer
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras == null){
            return;
        }
        String orderID = extras.getString("orderID");
        String ringing = extras.getString("ringing");
        if(ringing.equals("ringing")){
            display(orderID);
            extras.remove("orderID");
            extras.remove("ringing");
        }
    }

    @Override
    protected  void onPause(){
        super.onPause();
        // Close drawer
        HomeActivity.closeDrawer(drawerLayout);
    }

    public void onSelectChinese(View view) {
        // navigate to the chinese stall food menu
        Toast.makeText(getApplicationContext(),"Entering Chinese Food Stall",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), ChineseStallActivity.class).
                setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    public void onSelectMalay(View view){
        // navigate to the malay stall food menu
        Toast.makeText(getApplicationContext(),"Entering Malay Food Stall",Toast.LENGTH_SHORT).show();
    }

    public void onSelectBeverages(View view){
        // navigate to the beverage stall food menu
        Toast.makeText(getApplicationContext(),"Entering Beverage Stall",Toast.LENGTH_SHORT).show();
    }

    public void display(String orderID){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        String msg = "Food order ID: " + orderID + " ready to be collected.";
        builder1.setMessage(msg);
        builder1.setCancelable(false);  //prevent getting dismissed by back key
        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        for(int i=0;i<OrderActivity.getOrderData().size();i++){
                            if(OrderActivity.getOrderData().get(i).getOrderID().equals(orderID)){
                                OrderActivity.getOrderData().get(i).setOrderStatus("On My Way");
                                RecyclerView orderRecyclerView = (RecyclerView) findViewById(R.id.orderRecyclerView);
                                OrderAdapter adapter = new OrderAdapter(OrderActivity.getOrderData());
                                orderRecyclerView.setHasFixedSize(true);
                                orderRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                orderRecyclerView.setAdapter(adapter);
                                break;
                            }
                        }
                        // update the status if it is on order page
//                        startActivity(new Intent(getApplicationContext(), OrderActivity.class).
//                                setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        startService(new Intent(getApplicationContext(), StatusService.class));
                        stopService(new Intent(getApplicationContext(), AlarmService.class));
                        dialog.dismiss();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
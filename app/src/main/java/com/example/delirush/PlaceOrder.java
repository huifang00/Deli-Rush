package com.example.delirush;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class PlaceOrder extends AppCompatActivity {
    //Initialize variable
    DrawerLayout drawerLayout;
    ImageView btMenu;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        // Assign variable
        drawerLayout = findViewById(R.id.drawer_layout);
        btMenu = findViewById(R.id.bt_menu);
        recyclerView = findViewById(R.id.recycler_view);

        // Set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set adapter
        recyclerView.setAdapter(new MainAdapter(this, HomeActivity.arrayList));

        btMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open drawer
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    public void onPlaceOrder(View view) {
        ArrayList<OrderListData> orderData = OrderActivity.getOrderData();
        // update the latest order id with add 1 of previous order
        // this should be update from food seller side as well
        // the order id should be generated randomly from database
        // however food seller side waas not implemented
        int id = Integer.parseInt(orderData.get(0).getOrderID())+1;
        Collections.reverse(orderData);
        String orderID = String.valueOf(id);
        orderData.add(new OrderListData(orderID, "Indian", "Order Placed"));
        OrderActivity.setOrderData(orderData);
        new Thread(new Runnable() {
            public void run() {
                try {
                    // update the status to ready after 5 seconds
                    // (This should be updated from food seller, but food seller was not implemented yet
                    TimeUnit.MILLISECONDS.sleep(5000);
                    System.out.println(orderData.get(0));
                    orderData.get(0).setOrderStatus("Ready");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        // pass the position showing in the list
        startService(new Intent(getApplicationContext(),StatusService.class));
    }

    @Override
    public void onBackPressed() {
//        moveTaskToBack(true);
        super.onBackPressed();
    }
}
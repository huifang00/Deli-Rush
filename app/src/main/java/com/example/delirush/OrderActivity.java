package com.example.delirush;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class OrderActivity extends AppCompatActivity {
    //Initialize variable
    DrawerLayout drawerLayout;
    ImageView btMenu;
    RecyclerView recyclerView;
    // make the order data global to the service
    private static ArrayList<OrderListData> orderData = new ArrayList<OrderListData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

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

        // List of data
        // Need get the last Order ID and update new one
//        ArrayList<OrderListData> orderData = new ArrayList<OrderListData>();
        orderData.add(new OrderListData("0", "Chinese", "Ready"));
        orderData.add(new OrderListData("1", "Malay", "Collected"));
        Collections.reverse(orderData   );
        RecyclerView orderRecyclerView = (RecyclerView) findViewById(R.id.orderRecyclerView);
        OrderAdapter adapter = new OrderAdapter(orderData);
        orderRecyclerView.setHasFixedSize(true);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderRecyclerView.setAdapter(adapter);
        startService(new Intent(getApplicationContext(),StatusService.class));
    }

    @Override
    protected  void onPause(){
        super.onPause();
        // Close drawer
        HomeActivity.closeDrawer(drawerLayout);
    }

    public static ArrayList<OrderListData> getOrderData() {
        return orderData;
    }

    public static void setOrderData(ArrayList<OrderListData> orderData) {
        OrderActivity.orderData = orderData;
    }

}
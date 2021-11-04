package com.example.delirush;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class OrderActivity extends AppCompatActivity {
    //Initialize variable
    DrawerLayout drawerLayout;
    ImageView btMenu;
    RecyclerView recyclerView;

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
        ArrayList<OrderListData> orderData = new ArrayList<OrderListData>();
        orderData.add(new OrderListData("0", "Chinese", "Ready"));
        orderData.add(new OrderListData("1", "Malay", "Collected"));
        Collections.reverse(orderData   );
        RecyclerView orderRecyclerView = (RecyclerView) findViewById(R.id.orderRecyclerView);
        OrderAdapter adapter = new OrderAdapter(orderData);
        orderRecyclerView.setHasFixedSize(true);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderRecyclerView.setAdapter(adapter);
        updateStatus(orderData);
    }

    @Override
    protected  void onPause(){
        super.onPause();
        // Close drawer
        HomeActivity.closeDrawer(drawerLayout);
    }

    // call this all the time
    public void updateStatus(ArrayList<OrderListData>orderData){
        for(int position=0; position<orderData.size();position++){
            OrderListData order = orderData.get(position);
        }
    }
}
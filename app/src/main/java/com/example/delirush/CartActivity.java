package com.example.delirush;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.delirush.adapter.CartAdapter;
import com.example.delirush.adapter.MainAdapter;
import com.example.delirush.adapter.OrderAdapter;
import com.example.delirush.service.StatusService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class CartActivity extends AppCompatActivity {
    //Initialize variable
    DrawerLayout drawerLayout;
    ImageView btMenu;
    RecyclerView recyclerView;
    static ArrayList<CartListData> cartList = new ArrayList<>();
//    ArrayList<OrderListData> orderData;
    private ArrayList<OrderListData> orderData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

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

        // retrieve the user id
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        String id = sharedPreferences.getString("userID", "");
        TextView userID = findViewById(R.id.userID);
        userID.setText(id);

        RecyclerView cartRecyclerView = (RecyclerView) findViewById(R.id.cartRecyclerView);
        // Clear cart list
        cartList.clear();

        // Add menu item in cart list
        cartList.add(new CartListData("1", "Chicken Rice", "1", "RM5.00"));
        cartList.add(new CartListData("2", "Mineral Water", "1", "RM1.50"));

        // Initialize adpater
        CartAdapter adapter = new CartAdapter(cartList);

        // Set layout manager
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set adapter
        cartRecyclerView.setAdapter(adapter);
        orderData = (ArrayList<OrderListData>) PrefConfig.readListFromPref(this);
    }

    @Override
    protected  void onPause(){
        super.onPause();
        // Close drawer
        HomeActivity.closeDrawer(drawerLayout);
    }

    public void onPlaceOrder(View view) {
        // update the latest order id with add 1 of previous order
        // this should be update from food seller side as well
        // the order id should be generated randomly from database
        // however food seller side was not implemented
        int id;
        if(orderData.get(0) == null)
            id = 0;
        else
            id = Integer.parseInt(orderData.get(0).getOrderID())+1;
        Collections.reverse(orderData);
        String orderID = String.valueOf(id);
        orderData.add(new OrderListData(orderID, "Chinese", "Order Placed"));
        Collections.reverse(orderData);
        PrefConfig.writeListInPref(getApplicationContext(), orderData);
        new Thread(new Runnable() {
            public void run() {
                try {
                    // update the status to ready after 5 seconds
                    // (This should be updated from food seller, but food seller was not implemented yet
                    TimeUnit.MILLISECONDS.sleep(5000);
                    int i = 0;
                    for(i=0;i<orderData.size();i++) {
                        if (orderData.get(i).getOrderID().equals(orderID)) {
                            orderData.get(i).setOrderStatus("Ready");
                            PrefConfig.writeListInPref(getApplicationContext(), orderData);
                            break;
                        }
                    }
                    orderData = (ArrayList<OrderListData>) PrefConfig.readListFromPref(getApplicationContext());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        // pass the position showing in the list
        startService(new Intent(getApplicationContext(), StatusService.class));
        startActivity(new Intent(getApplicationContext(), OrderActivity.class).
                setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
}
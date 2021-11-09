package com.example.delirush;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.delirush.adapter.MainAdapter;
import com.example.delirush.adapter.OrderAdapter;
import com.example.delirush.service.AlarmService;
import com.example.delirush.service.StatusService;
import java.util.ArrayList;
import java.util.Collections;

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

        // retrieve the user id
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        String id = sharedPreferences.getString("userID", "");
        TextView userID = findViewById(R.id.userID);
        userID.setText(id);

        RecyclerView orderRecyclerView = (RecyclerView) findViewById(R.id.orderRecyclerView);
        OrderAdapter adapter = new OrderAdapter(orderData);
        orderRecyclerView.setHasFixedSize(true);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderRecyclerView.setAdapter(adapter);

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

    public static ArrayList<OrderListData> getOrderData() {
        return orderData;
    }

    public static void setOrderData(ArrayList<OrderListData> orderData) {
        OrderActivity.orderData = orderData;
        // reverse the list of orders so the top one is always the lastest order
        Collections.reverse(orderData);
    }

    public static ArrayList<String> getOrderStatusList(){
        ArrayList<String> orderStatus = new ArrayList<String>();
        for(int i=0;i<orderData.size();i++){
            orderStatus.add(orderData.get(i).getOrderStatus());
        }
        return orderStatus;
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
                                OrderAdapter adapter = new OrderAdapter(orderData);
                                orderRecyclerView.setHasFixedSize(true);
                                orderRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                orderRecyclerView.setAdapter(adapter);
                                break;
                            }
                        }
//                        update the order status to on the way
//                        startService(new Intent(getApplicationContext(), OrderActivity.class).
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
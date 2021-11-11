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
import com.example.delirush.service.Alarm_Service;
import com.example.delirush.service.Notification_Service;
import com.example.delirush.service.Status_Service;
import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {
    //Initialize variable
    DrawerLayout drawerLayout;
    ImageView btMenu;
    RecyclerView recyclerView;
    private ArrayList<OrderListData> orderData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // read from shared preferences
        orderData = (ArrayList<OrderListData>) PrefConfigOrderList.readListFromPref(this);

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
        // the extras may have the placed_cliked key pass from cart activity
        // so if orderID and ringing are null which are required to pass from alarm service
        // don't run the code below it
        if(extras.getString("orderID") != null && extras.getString("orderID") != null) {
            String orderID = extras.getString("orderID");
            String ringing = extras.getString("ringing");
            if (ringing.equals("ringing")) {
                display(orderID);
                extras.remove("orderID");
                extras.remove("ringing");
            }
        }
    }

    @Override
    protected  void onPause(){
        super.onPause();
        // Close drawer
        HomeActivity.closeDrawer(drawerLayout);
    }

    public ArrayList<String> getOrderStatusList(){
        ArrayList<String> orderStatus = new ArrayList<String>();
        for(int i=0;i<orderData.size();i++){
            orderStatus.add(orderData.get(i).getOrderStatus());
        }
        return orderStatus;
    }

    public void display(String orderID){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String msg = "Food order ID: " + orderID + " ready to be collected.";
        builder.setMessage(msg);
        builder.setCancelable(false);  //prevent getting dismissed by back key
        builder.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        for(int i=0;i<orderData.size();i++){
                            if(orderData.get(i).getOrderID().equals(orderID)){
                                orderData.get(i).setOrderStatus("On My Way");
                                RecyclerView orderRecyclerView = (RecyclerView) findViewById(R.id.orderRecyclerView);
                                OrderAdapter adapter = new OrderAdapter(orderData);
                                PrefConfigOrderList.writeListInPref(getApplicationContext(), orderData);
                                orderRecyclerView.setHasFixedSize(true);
                                orderRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                orderRecyclerView.setAdapter(adapter);
                                break;
                            }
                        }
                        startService(new Intent(getApplicationContext(), Status_Service.class));
                        stopService(new Intent(getApplicationContext(), Alarm_Service.class));
                        stopService(new Intent(getApplicationContext(), Notification_Service.class));
                        dialog.dismiss();
                    }
                });
        AlertDialog alert11 = builder.create();
        alert11.show();
    }

    @Override
    public void onBackPressed() {
        Bundle extras = getIntent().getExtras();
        // if placed clicked key was found (not null)
        // if place order button is clicked and direct to order activity
        // when back button is clicked, it should direct to the home page instead of cart page
        if(extras.getString("place_clicked")!=null &&
                extras.getString("place_clicked").equals("clicked")){
            startActivity(new Intent(getApplicationContext(), HomeActivity.class).
                    setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            extras.remove("place_clicked");
            return;
        }
        super.onBackPressed();
    }
}
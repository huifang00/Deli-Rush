package com.example.delirush;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
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
    // Declare variables
    private DrawerLayout drawerLayout;
    private ImageView btMenu;
    private RecyclerView recyclerView;
    public static ArrayList<OrderListData> orderData  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // Assign variable
        drawerLayout = findViewById(R.id.drawer_layout);
        btMenu = findViewById(R.id.bt_menu);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MainAdapter(this, HomeActivity.arrayList));

        btMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open drawer
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        // Retrieve the user id
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
        HomeActivity.closeDrawer(drawerLayout);
    }

    /**
     * Display the pop up with order ID
     * @param orderID
     */
    public void display(String orderID){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String msg = "Food order ID: " + orderID + " ready to be collected.";
        builder.setMessage(msg);
        builder.setCancelable(false);  //prevent getting dismissed by back key
        builder.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Database dbHandler = new Database(getApplicationContext(), null, null, 1);
                        dbHandler.updateOrder(Integer.parseInt(orderID), "On My Way");
//                        dbHandler.readOrder();
                        // clear the notification from the status bar
                        NotificationManagerCompat.from(getApplicationContext()).cancelAll();
                        // display the updated order list with latest status
                        RecyclerView orderRecyclerView = (RecyclerView) findViewById(R.id.orderRecyclerView);
                        OrderAdapter adapter = new OrderAdapter(orderData);
                        orderRecyclerView.setHasFixedSize(true);
                        orderRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        orderRecyclerView.setAdapter(adapter);
                        // start and stop services
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
        super.onBackPressed();
        finish();
    }
}
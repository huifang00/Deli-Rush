package com.example.delirush;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.delirush.adapter.CartAdapter;
import com.example.delirush.adapter.MainAdapter;
import com.example.delirush.service.Status_Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class CartActivity extends AppCompatActivity {
    //Initialize variable
    private DrawerLayout drawerLayout;
    private ImageView btMenu;
    private RecyclerView recyclerView, cartRecyclerView;
    private ArrayList<CartListData> cartData;
    private ArrayList<OrderListData> orderData;
    private float total = 0;
    private DecimalFormat df = new DecimalFormat("0.00");
    private ImageView clear_cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // read order data & cart data
        orderData = (ArrayList<OrderListData>) PrefConfigOrderList.readListFromPref(this);
        cartData = (ArrayList<CartListData>) PrefConfigCartList.readListFromPref(this);

        // variable of clear cart button
        clear_cart = findViewById(R.id.clear_cart);

        // variable for cart list
        cartRecyclerView = (RecyclerView) findViewById(R.id.cartRecyclerView);
        // update the view of cart page
        updateView(cartRecyclerView);

        // variable for side drawer
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
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        // if cart is not empty, display the delete button
        if(!cartData.isEmpty()){
            clear_cart.setVisibility(View.VISIBLE);
        }
        clear_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartData.clear();
                PrefConfigCartList.writeListInPref(getApplicationContext(), cartData);
                // update the view of cart page
                updateView(cartRecyclerView);
            }
        });

        // retrieve the user id
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        String id = sharedPreferences.getString("userID", "");
        TextView userID = findViewById(R.id.userID);
        userID.setText(id);
    }

    @Override
    protected  void onPause(){
        super.onPause();
        HomeActivity.closeDrawer(drawerLayout);
    }

    public void onPlaceOrder(View view) {
        if(cartData.isEmpty()) {
            Toast.makeText(this, "Cart List is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        /*
         Update the latest order id with add 1 of previous order
         the order id is unique and should be generated randomly from database
         this should be update at the food seller side
         database and food seller side are not implemented yet
         */
        int id;
        if(orderData.isEmpty())
            id = 1;
        else
            id = Integer.parseInt(orderData.get(0).getOrderID())+1;
        Collections.reverse(orderData);
        String orderID = String.valueOf(id);
        orderData.add(new OrderListData(orderID, "Chinese", "Order Placed"));
        Collections.reverse(orderData);
        PrefConfigOrderList.writeListInPref(getApplicationContext(), orderData);
        displayPaymentDialog(orderID);

        // Clear the cart list
        cartData.clear();
        PrefConfigCartList.writeListInPref(getApplicationContext(), cartData);
        // update the view of cart page
        updateView(cartRecyclerView);

    }

    private void displayPaymentDialog(String orderID) {
        String msg = "Payment page is not implemented yet. By assuming it is implemented, it will direct to the order page after paid successfully for the order.";
        new AlertDialog.Builder(this)
                .setTitle("PROCEED TO PAYMENT" + "\n" + "Total: RM " + df.format(total))
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startService(new Intent(getApplicationContext(), Status_Service.class));
                        startActivity(new Intent(getApplicationContext(), OrderActivity.class).
                                setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        dialog.dismiss();
                        startTimer(orderID);
                    }
                }).setCancelable(false)  //prevent getting dismissed by back key
                .create().show();
    }

    private void startTimer(String orderID) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    /*
                    Update the status to ready after 5 seconds
                     (This should be updated from food seller, but food seller was not implemented yet
                     */
                    TimeUnit.MILLISECONDS.sleep(5000);
                    for(int i=0;i<orderData.size();i++) {
                        if (orderData.get(i).getOrderID().equals(orderID)) {
                            orderData.get(i).setOrderStatus("Ready");
                            PrefConfigOrderList.writeListInPref(getApplicationContext(), orderData);
                            break;
                        }
                    }
                    orderData = (ArrayList<OrderListData>) PrefConfigOrderList.readListFromPref(getApplicationContext());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void updateView(RecyclerView cartRecyclerView){
        // Initialize adapter
        CartAdapter adapter = new CartAdapter(cartData);

        // Set layout manager
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set adapter
        cartRecyclerView.setAdapter(adapter);

        // calculate the total
        TextView total_sum_text = findViewById(R.id.total_sum_text);
        if(cartData.isEmpty()){
            total = 0;
        }
        for(int i=0;i<cartData.size();i++){
            total+=Float.parseFloat(cartData.get(i).getTotal());
        }
        total_sum_text.setText(df.format(total));

        // If cart empty make clear button invisible
        if(cartData.isEmpty())
            clear_cart.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
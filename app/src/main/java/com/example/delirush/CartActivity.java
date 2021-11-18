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
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.delirush.adapter.CartAdapter;
import com.example.delirush.adapter.MainAdapter;
import com.example.delirush.service.Status_Service;
import com.example.delirush.service.TimerService;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class CartActivity extends AppCompatActivity {
    //Initialize variable
    private DrawerLayout drawerLayout;
    private ImageView btMenu;
    private RecyclerView recyclerView, cartRecyclerView;
    public static ArrayList<CartListData> cartData = new ArrayList<>();
    private float total = 0;
    private DecimalFormat df = new DecimalFormat("0.00");
    private ImageView clear_cart;
    private static int orderStall;
    private TextView stall_cart;
    private Database dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // read order data & cart data
        dbHandler = new Database(this, null, null, 1);
        dbHandler.readCart();
//        dbHandler.readOrder();

//        orderData = (ArrayList<OrderListData>) PrefConfigOrderList.readListFromPref(this);
//        cartData = (ArrayList<CartListData>) PrefConfigCartList.readListFromPref(this);

        // variable of clear cart button
        clear_cart = findViewById(R.id.clear_cart);

        // variable of stall name text view
        stall_cart = findViewById(R.id.stall_cart);

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
                // Alert window for final confirmation before clear the cart
                clearCartDialog();
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

    /**
     * Triggered when place order button is clicked
     * @param view
     */
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
//        int id;
//        if(orderData.isEmpty())
//            id = 1;
//        else
//            id = Integer.parseInt(orderData.get(0).getOrderID())+1;
//        Collections.reverse(orderData);
        OrderListData order = new OrderListData(1, getStallName(), "Order Placed");
        dbHandler.addOrder(order);
//        order.setOrderID(dbHandler.getOrderID());   // update the id generated from database
//        OrderActivity.orderData.add(order);
//        dbHandler.readOrder();
//        String orderID = String.valueOf(id);
//        String foodStall = getStallName();
//        orderData.add(new OrderListData(orderID, foodStall, "Order Placed"));
        Collections.reverse(OrderActivity.orderData);
//        PrefConfigOrderList.writeListInPref(getApplicationContext(), OrderActivity.orderData);
        // read the order id from the last row of database
        String orderID = String.valueOf(dbHandler.getOrderID());
        paymentDialog(orderID);

        // Clear the cart list
        dbHandler.deleteCart();
//        cartData.clear();
//        PrefConfigCartList.writeListInPref(getApplicationContext(), cartData);
        // update the view of cart page
        updateView(cartRecyclerView);
    }

    /**
     * Display the payment dialog before redirecting to payment page
     * However, the payment page was not implemented yet as need direct to a third party page such as FPX
     * @param orderID
     */
    private void paymentDialog(String orderID) {
        String msg = "The payment page has yet to be implemented but it is assumed to direct to the order page after payment has been made successfully.";
        new AlertDialog.Builder(this)
                .setTitle("PROCEED TO PAYMENT" + "\n" + "Total: RM " + df.format(total))
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), TimerService.class);
                        intent.putExtra("orderId_fromCart", orderID);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            startForegroundService(intent);
                        } else {
                            startService(intent);
                        }
                        startService(new Intent(getApplicationContext(), Status_Service.class));
                        startActivity(new Intent(getApplicationContext(), OrderActivity.class).
                                setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        dialog.dismiss();
                    }
                }).setCancelable(false)  //prevent getting dismissed by back key
                .create().show();
    }

    /**
     * Display alert window for final confirmation before clear the cart
     */
    private void clearCartDialog() {
        // Display the log out dialog
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Clear Cart");
        builder.setMessage("Are you sure you want to clear your cart?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbHandler.deleteCart();
//                cartData.clear();
//                PrefConfigCartList.writeListInPref(getApplicationContext(), cartData);
                // update the view of cart page
                updateView(cartRecyclerView);
            }
        });
        // If cancel button is clicked dismiss the dialog and stop the process of proceed to previous page
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                return;
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    /**
     * Update the view of cart page
     * Including list, toolbar, total on cart page
     * @param cartRecyclerView
     */
    public void updateView(RecyclerView cartRecyclerView){
        CartAdapter adapter = new CartAdapter(cartData);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
        if(cartData.isEmpty()){
            clear_cart.setVisibility(View.INVISIBLE);
            setOrderStall(-1);
            // Store stall id of order into shared preference
            SharedPreferences sharedPreferences = getSharedPreferences("stallID",MODE_PRIVATE);

            // Edit stall id of order in shared preference
            SharedPreferences.Editor edit_stallId = sharedPreferences.edit();
            edit_stallId.putInt("stallID",getOrderStall());
            edit_stallId.apply();
        }
        // Set the display of stall name in cart
        String stall = getStallName();
        stall_cart.setText(stall);
    }

    /**
     * Getter method for retrieve private variable orderStall
     * @return
     */
    public static int getOrderStall() {
        return orderStall;
    }

    /**
     * Setter method for set private variable orderStall
     * @param orderStall
     */
    public static void setOrderStall(int orderStall) { CartActivity.orderStall = orderStall; }

    /**
     * Get the stall name based on the stall id
     * @return
     */
    private String getStallName(){
        switch(orderStall){
            case 0: return "Chinese Stall";
            case 1: return "Malay Stall";
            case 2: return "Beverage Stall";
            default: return "-";
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
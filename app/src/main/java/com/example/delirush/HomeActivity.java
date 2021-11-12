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
import android.widget.Toast;

import com.example.delirush.adapter.MainAdapter;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    //Initialize variable
    private DrawerLayout drawerLayout;
    private ImageView btMenu;
    private RecyclerView recyclerView;
    static ArrayList<String> arrayList = new ArrayList<>();
    private MainAdapter adapter;
    private ArrayList<OrderListData> orderData;

    /**
     * Close the drawer
     * @param drawerLayout
     */
    public static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    /**
     * Set the view of drawer and add action to each row
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Assign variable
        drawerLayout = findViewById(R.id.drawer_layout);
        btMenu = findViewById(R.id.bt_menu);
        recyclerView = findViewById(R.id.recycler_view);

        arrayList.clear();

        // Add menu item in array list
        arrayList.add("Home");
        arrayList.add("My Cart");
        arrayList.add("My Orders");
        arrayList.add("Logout");

        adapter = new MainAdapter(this, arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        // Retrieve the user id
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        String id = sharedPreferences.getString("userID", "");
        TextView userID = findViewById(R.id.userID);
        userID.setText(id);
        orderData = (ArrayList<OrderListData>) PrefConfigOrderList.readListFromPref(this);
    }

    @Override
    protected  void onPause(){
        super.onPause();
        HomeActivity.closeDrawer(drawerLayout);
    }

    /**
     * Navigate to the chinese stall food menu
     * @param view
     */
    public void onSelectChinese(View view) {
        Toast.makeText(getApplicationContext(),"Entering Chinese Food Stall",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), ChineseStallActivity.class).
                setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    /**
     * Navigate to the malay stall food menu
     * @param view
     */
    public void onSelectMalay(View view){
        Toast.makeText(getApplicationContext(),"Entering Malay Food Stall",Toast.LENGTH_SHORT).show();
    }

    /**
     * Navigate to the beverage stall food menu
     * @param view
     */
    public void onSelectBeverages(View view){
        Toast.makeText(getApplicationContext(),"Entering Beverage Stall",Toast.LENGTH_SHORT).show();
    }

    /**
     * Pop up dialog to ask confirmation on log out when back button is clicked on home page
     */
    @Override
    public void onBackPressed() {
        // Display the log out dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class).
                        setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
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
        builder.show();
    }
}
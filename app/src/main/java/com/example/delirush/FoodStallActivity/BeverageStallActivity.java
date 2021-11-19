package com.example.delirush.FoodStallActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.delirush.CartActivity;
import com.example.delirush.CartListData;
import com.example.delirush.HomeActivity;
import com.example.delirush.MenuListData;
import com.example.delirush.PrefConfigCartList;
import com.example.delirush.R;
import com.example.delirush.adapter.MainAdapter;
import com.example.delirush.adapter.MenuAdapter;

import java.util.ArrayList;

public class BeverageStallActivity extends AppCompatActivity implements QuantityDialog.QuantityDialogListener {
    //Initialize variable
    DrawerLayout drawerLayout;
    ImageView btMenu;
    RecyclerView recyclerView;
    private ArrayList<MenuListData> beverage_menu = new ArrayList<MenuListData>();
    private static String price = "";
    private String food = "";
    private ArrayList<CartListData> cartData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stall);
        ImageView stallView = findViewById(R.id.stallView);
        stallView.setBackgroundResource(R.drawable.beverages);

        // Read cart Data
        cartData = (ArrayList<CartListData>) PrefConfigCartList.readListFromPref(this);
        // Assign variable
        drawerLayout = findViewById(R.id.drawer_layout);
        btMenu = findViewById(R.id.bt_menu);
        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(new MainAdapter(this, HomeActivity.arrayList));

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

        createMenu();
        Bundle extras = getIntent().getExtras();
        if(extras==null){
            return;
        }
        String openDialog = extras.getString("openDialog");
        food = extras.getString("food");
        price = extras.getString("price");
        if(openDialog.equals("open")) {
            openDialog(food);
            extras.remove("openDialog");
        }
    }

    /**
     * Create the list for the menu in stall
     */
    private void createMenu() {
        RecyclerView menu_recyclerView = (RecyclerView) findViewById(R.id.menu_recyclerView);
        beverage_menu.clear();

        // Add menu item in array list
        beverage_menu.add(new MenuListData("Mineral Water", "1.00"));
        beverage_menu.add(new MenuListData("Orange Juice", "2.50"));
        beverage_menu.add(new MenuListData("100 Plus", "1.80"));
        beverage_menu.add(new MenuListData("Coffee", "2.20"));

        MenuAdapter adapter = new MenuAdapter(this, beverage_menu);
        menu_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        menu_recyclerView.setAdapter(adapter);
    }

    /**
     * Open the dialog of setting the quantity of the food
     * @param food
     */
    private void openDialog(String food) {
        QuantityDialog quantityDialog = new QuantityDialog(food);
        quantityDialog.show(getSupportFragmentManager(),"quantityDialog");
        quantityDialog.setCancelable(false);    //prevent getting dismissed by back key
    }

    /**
     * Apply the text changes in library to the cart Activity
     * @param quantity
     */
    @Override
    public void applyTexts(String quantity) {
        if (!cartData.isEmpty()) {
            if (CartActivity.getOrderStall() != HomeActivity.getSelectedStall()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Seems like you have selected from different stall.");
                builder.setMessage("If proceed with this action, the previous cart will be removed.");
                // If proceed button is clicked dismiss the dialog -> clear the cart -> add into cart
                builder.setPositiveButton("PROCEED", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Clear the cart
                        cartData.clear();
                        PrefConfigCartList.writeListInPref(getApplicationContext(), cartData);
                        dialog.dismiss();
                        StallActivity.updateCart(getApplicationContext(), quantity, food, price, 2);
                    }
                });
                // If cancel button is clicked dismiss the dialog and stop adding the food
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setCancelable(false);
                builder.show();
            } else {
                StallActivity.updateCart(getApplicationContext(), quantity, food, price, 2);
            }
        } else {
            StallActivity.updateCart(getApplicationContext(), quantity, food, price, 2);
        }
    }

    /**
     * Getter method for retrieve private variable price
     * @return
     */
    public static String getPrice() {
        return price;
    }

    /**
     * Setter method for set private variable price
     * @param price
     */
    public static void setPrice(String price) {
        BeverageStallActivity.price = price;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
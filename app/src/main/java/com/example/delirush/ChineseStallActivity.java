package com.example.delirush;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.delirush.adapter.MainAdapter;
import com.example.delirush.adapter.MenuAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChineseStallActivity extends AppCompatActivity implements QuantityDialog.QuantityDialogListener{
    //Initialize variable
    DrawerLayout drawerLayout;
    ImageView btMenu;
    RecyclerView recyclerView;
    static ArrayList<String> chinese_menu = new ArrayList<>();
    static String price = "";
    static String food = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinese_stall);

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

        createMenu();
        Bundle extras = getIntent().getExtras();
        if(extras==null){
            return;
        }
        String openDialog = extras.getString("openDialog");
        food = extras.getString("food");
        price = extras.getString("price");
        if(openDialog.equals("open")) {
            openDialog();
            extras.remove("openDialog");
        }
    }

    private void createMenu() {
        RecyclerView menu_recyclerView = (RecyclerView) findViewById(R.id.menu_recyclerView);
        // Clear cart list
        chinese_menu.clear();

        // Add menu item in array list
        chinese_menu.add("Chicken Rice");
        chinese_menu.add("Fried Rice");
        chinese_menu.add("Butter Milk Chicken Rice");
        chinese_menu.add("Chicken Porridge");
        chinese_menu.add("Curry Noodle");

        // Initialize adpater
        MenuAdapter adapter = new MenuAdapter(this, chinese_menu);

        // Set layout manager
        menu_recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set adapter
        menu_recyclerView.setAdapter(adapter);
    }

    private void openDialog() {
        QuantityDialog quantityDialog = new QuantityDialog();
        quantityDialog.show(getSupportFragmentManager(),"quantityDialog");
    }


    @Override
    public void applyTexts(String quantity) {
        if(quantity.equals("0"))
            return;
        DecimalFormat df = new DecimalFormat("0.00");
        String food = ChineseStallActivity.food;
        String price = ChineseStallActivity.price;
        boolean found = false;
        int add_on = Integer.parseInt(quantity);
        float total = Float.parseFloat(price);
        for(int i=0; i<CartActivity.cartList.size();i++){
            if(CartActivity.cartList.get(i).getFood().equals(food)){
                int prev_qty = Integer.parseInt(CartActivity.cartList.get(i).getQuatity());
                int new_qty = prev_qty + add_on;
                total = new_qty * total;
                CartActivity.cartList.get(i).setQuatity(String.valueOf(new_qty));
                CartActivity.cartList.get(i).setTotal(df.format(total));
                found = true;
                break;
            }
        }
        // if no previous similar item is added into cart create new row in the display cart list
        if(!found){
            int foodIndex = CartActivity.cartList.size() + 1;
            String foodIndex_str = String.valueOf(foodIndex);
            total = add_on * total;
            CartActivity.cartList.add(new CartListData(foodIndex_str, food, String.valueOf(add_on), df.format(total)));
        }
    }
}
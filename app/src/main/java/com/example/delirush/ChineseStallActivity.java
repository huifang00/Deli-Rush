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
    static ArrayList<MenuListData> chinese_menu = new ArrayList<MenuListData>();
    static String price = "";
    static String food = "";
    private ArrayList<CartListData> cartData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinese_stall);

        // read cart Data
        cartData = (ArrayList<CartListData>) PrefConfigCartList.readListFromPref(this);
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
            openDialog(food);
            extras.remove("openDialog");
        }
    }

    private void createMenu() {
        RecyclerView menu_recyclerView = (RecyclerView) findViewById(R.id.menu_recyclerView);
        // Clear cart list
        chinese_menu.clear();

        // Add menu item in array list
        chinese_menu.add(new MenuListData("Chicken Rice", "5.50"));
        chinese_menu.add(new MenuListData("Fried Rice", "6.00"));
        chinese_menu.add(new MenuListData("Butter Milk Chicken Rice", "6.50"));
        chinese_menu.add(new MenuListData("Chicken Porridge", "4.50"));
        chinese_menu.add(new MenuListData("Curry Noodle", "5.00"));

        // Initialize adpater
        MenuAdapter adapter = new MenuAdapter(this, chinese_menu);

        // Set layout manager
        menu_recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set adapter
        menu_recyclerView.setAdapter(adapter);
    }

    private void openDialog(String food) {
        QuantityDialog quantityDialog = new QuantityDialog(food);
        quantityDialog.show(getSupportFragmentManager(),"quantityDialog");
    }


    @Override
    public void applyTexts(String quantity) {
        DecimalFormat df = new DecimalFormat("0.00");
        String food = ChineseStallActivity.food;
        String price = ChineseStallActivity.price;
        boolean found = false;
        int new_quantity = Integer.parseInt(quantity);
        float total = Float.parseFloat(price);
        for(int i=0; i<cartData.size();i++){
            if(cartData.get(i).getFood().equals(food)){
                found = true;
                if(new_quantity == 0){
                    cartData.remove(i);
                    break;
                }
                total = new_quantity * total;
                cartData.get(i).setQuatity(String.valueOf(new_quantity));
                cartData.get(i).setTotal(df.format(total));
                break;
            }
        }
        // if no previous similar item is added into cart create new row in the display cart list
        if(!found){
            // if the quantity selected is 0, return without doing anything
            if(quantity.equals("0"))
                return;
            // else add into the cart list
            else{
                int foodIndex = cartData.size() + 1;
                String foodIndex_str = String.valueOf(foodIndex);
                total = new_quantity * total;
                cartData.add(new CartListData(foodIndex_str, food, String.valueOf(new_quantity), df.format(total)));
            }
        }
        PrefConfigCartList.writeListInPref(getApplicationContext(), cartData);
    }
}
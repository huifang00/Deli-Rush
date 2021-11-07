package com.example.delirush;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.delirush.adapter.MainAdapter;
import com.example.delirush.adapter.MenuAdapter;

import java.util.ArrayList;

public class ChineseStallActivity extends AppCompatActivity{
    //Initialize variable
    DrawerLayout drawerLayout;
    ImageView btMenu;
    RecyclerView recyclerView;
    static ArrayList<String> chinese_menu = new ArrayList<>();
    static String price = "";
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

        createMenu();
        Bundle extras = getIntent().getExtras();
        if(extras==null){
            return;
        }
        String openDialog = extras.getString("openDialog");
        price = extras.getString("price");
        if(openDialog.equals("open")) {
            openDialog();
            extras.remove("openDialog");
            extras.remove("price");
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


//    @Override
//    public void applyTexts(String username, String password) {
//
//    }
}
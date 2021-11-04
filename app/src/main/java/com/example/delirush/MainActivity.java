package com.example.delirush;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onCustomer(View view) {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }

    public void onSeller(View view) {
//        Intent loginIntent = new Intent(this, LoginActivity.class);
//        startActivity(loginIntent);
        Toast.makeText(getApplicationContext(),"Seller Log In",Toast.LENGTH_SHORT).show();
    }
}

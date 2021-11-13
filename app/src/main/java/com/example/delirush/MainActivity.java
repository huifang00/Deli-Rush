package com.example.delirush;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.delirush.service.Alarm_Service;
import com.example.delirush.service.Notification_Service;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView appName1 = findViewById(R.id.appName1);

        Typeface typeface1 = ResourcesCompat.getFont(
                this,
                R.font.permanentmarker_regular);
        appName1.setTypeface(typeface1);

        TextView appName2 = findViewById(R.id.appName2);

        Typeface typeface2 = ResourcesCompat.getFont(
                this,
                R.font.permanentmarker_regular);
        appName2.setTypeface(typeface2);

        // When the notification is pop up, the application should be started from the order page
        if(Notification_Service.notified){
            Intent order_intent = new Intent(getApplicationContext(), OrderActivity.class).
                    setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            order_intent.putExtra("orderID", Alarm_Service.orderID);  // let the order ID be static?
            order_intent.putExtra("ringing", "ringing");
            startActivity(order_intent);
        }
    }

    /**
     * Trigerred when customer account button is clicked
     * @param view
     */
    public void onCustomer(View view) {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }

    /**
     * Triggered when seller account button is clicked
     * @param view
     */
    public void onSeller(View view) {
        Toast.makeText(getApplicationContext(),"Seller Log In",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

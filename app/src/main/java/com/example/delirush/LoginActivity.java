package com.example.delirush;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Credentials;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private int failed = 0;

    /* Class to hold credentials */
    class Credentials
    {
        String id = "123456";
        String password = "pw123";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    //        public void onLogIn(View view) {
//            Intent loginIntent = new Intent(this, HomeActivity.class);
//            EditText editId = (EditText) findViewById(R.id.editId);
//            String id = editId.getText().toString();
//            loginIntent.putExtra("id", id);
//            startActivity(loginIntent);
//        }
    public void onLogIn(View view) {
        Button loginbtn = (Button) findViewById(R.id.loginbtn);
        TextView attemptsLeft = (TextView) findViewById(R.id.attemptsLeft);
        EditText editId = (EditText) findViewById(R.id.editId);
        EditText editPassword = (EditText) findViewById(R.id.editPassword);

        // get user input
        String id = editId.getText().toString();
        String password = editPassword.getText().toString();

        // Check if user enter the id column
        if (id.isEmpty()) {
            // Display message toast to prompt for id input
            Toast.makeText(LoginActivity.this, "Please enter your staff/student ID", Toast.LENGTH_LONG).show();
        }
        // Check if user enter the password column
        else if (password.isEmpty()) {
            // Display message toast to prompt for password input
            Toast.makeText(LoginActivity.this, "Please enter your password", Toast.LENGTH_LONG).show();
        } else {
            // validate the id and password, check is the right password entered?
            boolean isValid = validate(id, password);

            // if wrong password, decrease the attempts remaining
            if (!isValid) {

                // increase the failed attempts
                failed++;

                // display the failed attempts remaining
                attemptsLeft.setText("Attempts Remaining: " + String.valueOf(5-failed));

                // if no more attempts left, disable the button
                if (failed == 5) {
                    loginbtn.setEnabled(false);
                    Toast.makeText(this, "No attempts left. Try again later.", Toast.LENGTH_LONG).show();
                }
                // display incorrect password message
                else {
                    Toast.makeText(this, "Incorrect Password", Toast.LENGTH_LONG).show();
                }
            }
            // if the id matches with the password, proceed to next page
            else {
                ArrayList<OrderListData> orderData = new ArrayList<OrderListData>();
                orderData.add(new OrderListData("0", "Chinese", "Collected"));
                orderData.add(new OrderListData("1", "Malay", "Collected"));
                OrderActivity.setOrderData(orderData);
                startActivity(new Intent(this, HomeActivity.class));
            }

        }
    }

    // Validate whether the id corresponding to the right password
    private boolean validate(String id, String password) {
        Credentials credentials = new Credentials();
        if (id.equals(credentials.id) && password.equals(credentials.password)) {
            return true;
        }
        return false;
    }
}
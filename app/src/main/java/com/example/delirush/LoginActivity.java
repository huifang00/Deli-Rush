package com.example.delirush;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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
    private ArrayList<OrderListData> orderData;
    private ArrayList<CartListData> cartData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Clear previous data

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
    }

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
            Toast.makeText(LoginActivity.this, "Please enter your staff/student ID", Toast.LENGTH_SHORT).show();
        }
        // Check if user enter the password column
        else if (password.isEmpty()) {
            // Display message toast to prompt for password input
            Toast.makeText(LoginActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(this, "No attempts left. Try again later.", Toast.LENGTH_SHORT).show();
                }
                // display incorrect password message
                else {
                    Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                }
            }
            // if the id matches with the password, proceed to next page
            else {
                // take order data from memory(shared preference)
                orderData = (ArrayList<OrderListData>) PrefConfigOrderList.readListFromPref(this);
                // avoid null pointer exception
                if(orderData == null){
                    orderData = new ArrayList<OrderListData>();
                    PrefConfigOrderList.writeListInPref(getApplicationContext(), orderData);
                }

                // take cart data from memory(shared preference)
                cartData = (ArrayList<CartListData>) PrefConfigCartList.readListFromPref(this);
                // avoid null pointer exception
                if(cartData == null){
                    cartData = new ArrayList<CartListData>();
                    PrefConfigCartList.writeListInPref(getApplicationContext(), cartData);
                }

                // store data into SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);

                // editor to edit the file
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                // store the key and value as the data fetched
                myEdit.putString("userID",editId.getText().toString());

                // apply the changes
                myEdit.commit();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class).
                        setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
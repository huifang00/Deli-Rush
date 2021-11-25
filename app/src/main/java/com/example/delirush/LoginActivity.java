package com.example.delirush;
/*****************************
 * Code is referred from
 *    Title: Android RecyclerView List Example
 *    Author: Professor DK
 *    Date: 25/5/2020
 *    Code version: 1.0
 *    Availability: https://www.youtube.com/watch?v=LCrhddpsgKU
 *
 *****************************/
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

public class LoginActivity extends AppCompatActivity {
    // Declare variables
    private int failed;
    private Button loginbtn;
    private TextView attemptsLeft;
    private EditText editId, editPassword;

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

        // Set the font style
        TextView appName1 = findViewById(R.id.appName1);
        Typeface typeface1 = ResourcesCompat.getFont(this, R.font.permanentmarker_regular);
        appName1.setTypeface(typeface1);

        TextView appName2 = findViewById(R.id.appName2);
        Typeface typeface2 = ResourcesCompat.getFont(this, R.font.permanentmarker_regular);
        appName2.setTypeface(typeface2);

        // Initialize variable
        loginbtn = (Button) findViewById(R.id.loginbtn);
        attemptsLeft = (TextView) findViewById(R.id.attemptsLeft);
        editId = (EditText) findViewById(R.id.editId);
        editPassword = (EditText) findViewById(R.id.editPassword);
        failed = 0;
    }

    /**
     * Triggered when the log in button is clicked, to check whether is there any empty input
     * Verify if the password and user id matched
     * @param view
     */
    public void onLogIn(View view) {
        // Get user input
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
            boolean isValid = validate(id, password);

            if (!isValid) {
                // Increase the failed attempts
                failed++;

                // Display the failed attempts remaining
                attemptsLeft.setText("Attempts Remaining: " + String.valueOf(5-failed));

                // Disable the button when there is no attempts left
                if (failed == 5) {
                    loginbtn.setEnabled(false);
                    Toast.makeText(this, "No attempts left. Try again later.", Toast.LENGTH_SHORT).show();
                }

                // Display incorrect password message
                else {
                    Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                }
            }
            // If the id matches with the password, proceed to next page
            else {
                Database dbHandler = new Database(this, null, null, 1);
                // Take order data from database
                dbHandler.readOrder();

                // Take cart data from memory(shared preference)
                dbHandler.readCart();

                // Store user id into shared preference
                SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);

                // Edit user id in shared preference
                SharedPreferences.Editor edit_userId = sharedPreferences.edit();
                edit_userId.putString("userID",editId.getText().toString());
                edit_userId.apply();

                // Take current stall ID in the cart list
                sharedPreferences = getSharedPreferences("stallID", MODE_PRIVATE);
                int orderStall = sharedPreferences.getInt("stallID", -1);
                CartActivity.setOrderStall(orderStall);

                startActivity(new Intent(getApplicationContext(), HomeActivity.class).
                        setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }

        }
    }

    /**
     * Validate whether the id corresponding to the right password
     * @param id
     * @param password
     * @return
     */
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
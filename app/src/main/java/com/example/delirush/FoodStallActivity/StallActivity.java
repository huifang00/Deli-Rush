package com.example.delirush.FoodStallActivity;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.delirush.CartActivity;
import com.example.delirush.CartListData;
import com.example.delirush.Database;
import com.example.delirush.HomeActivity;

public class StallActivity {
    /**
     * Update the data display in cart page
     * Including the list of cart, total to be paid, stall name where the food is ordered from
     * Also, save the stall id in shared preference for future usage
     * @param context
     * @param quantity
     * @param food
     * @param price
     * @param stallID
     */
    public static void updateCart(Context context, String quantity, String food, String price, int stallID) {
        Database dbHandler = new Database(context, null, null, 1);
        int quantity_int = Integer.parseInt(quantity);
        float total = Float.parseFloat(price);

        // if the food name is found in the cart table, update the cart
        if (dbHandler.findProduct(food))
            dbHandler.updateCart(food, quantity_int, quantity_int * total);
        else {
            // if none of the food is found from cart, and quantity is 0, then don't do anything
            if (quantity_int == 0)
                return;
                // else add to the cart
            else
                dbHandler.addCart(new CartListData(food, quantity_int, quantity_int * total));
        }

        dbHandler.readCart();

        // Update the current stall id in the cart
        CartActivity.setOrderStall(HomeActivity.getSelectedStall());

        // Store stall id of order into shared preference
        SharedPreferences sharedPreferences = context.getSharedPreferences("stallID", MODE_PRIVATE);

        // Edit stall id of order in shared preference
        SharedPreferences.Editor edit_stallId = sharedPreferences.edit();
        edit_stallId.putInt("stallID",stallID);
        edit_stallId.apply();
    }
}

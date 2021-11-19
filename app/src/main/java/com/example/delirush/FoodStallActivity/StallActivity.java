package com.example.delirush.FoodStallActivity;

import static android.content.Context.MODE_PRIVATE;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.delirush.CartActivity;
import com.example.delirush.CartListData;
import com.example.delirush.PrefConfigCartList;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class StallActivity {
    /**
     * Update the data diplay in cart page
     * Including the list of cart, total to be paid, stall name where the food is ordered from
     * Also, save the stall id in shared preference for future usage
     * @param context
     * @param quantity
     * @param food
     * @param price
     * @param stallID
     */
    public static void updateCart(Context context, String quantity, String food, String price, int stallID) {
        ArrayList<CartListData> cartData = (ArrayList<CartListData>) PrefConfigCartList.readListFromPref(context);
        DecimalFormat df = new DecimalFormat("0.00");
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
        // If no previous similar item is added into cart create new row in the display cart list
        if(!found){
            // If the quantity selected is 0, return without doing anything
            if(quantity.equals("0"))
                return;
                // Else add into the cart list
            else{
                int foodIndex = cartData.size() + 1;
                String foodIndex_str = String.valueOf(foodIndex);
                total = new_quantity * total;
                cartData.add(new CartListData(foodIndex_str, food, String.valueOf(new_quantity), df.format(total)));
            }
        }
        // Update the cart list
        PrefConfigCartList.writeListInPref(context, cartData);

        // Update the current stall id in the cart
        CartActivity.setOrderStall(stallID);

        // Store stall id of order into shared preference
        SharedPreferences sharedPreferences = context.getSharedPreferences("stallID",MODE_PRIVATE);

        // Edit stall id of order in shared preference
        SharedPreferences.Editor edit_stallId = sharedPreferences.edit();
        edit_stallId.putInt("stallID",stallID);
        edit_stallId.apply();
    }
}

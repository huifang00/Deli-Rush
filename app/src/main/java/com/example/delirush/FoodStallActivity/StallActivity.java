package com.example.delirush.FoodStallActivity;

import static android.content.Context.MODE_PRIVATE;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.delirush.CartActivity;
import com.example.delirush.CartListData;
import com.example.delirush.Database;
import com.example.delirush.HomeActivity;
import com.example.delirush.PrefConfigCartList;

import java.text.DecimalFormat;
import java.util.ArrayList;

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
        System.out.println(dbHandler);
//        ArrayList<CartListData> cartData = (ArrayList<CartListData>) PrefConfigCartList.readListFromPref(context);
        DecimalFormat df = new DecimalFormat("0.00");
//        boolean found = false;
        int quantity_int = Integer.parseInt(quantity);
        float total = Float.parseFloat(price);

        // if the food name is found in the cart table, update the cart
        if(dbHandler.findProduct(food))
            dbHandler.updateCart(food, quantity_int, df.format(quantity_int * total));
        // else add to the cart
        else
            dbHandler.addCart(new CartListData(food, quantity_int, df.format(quantity_int * total)));

//        for(int i=0; i<cartData.size();i++){
//            if(cartData.get(i).getFood().equals(food)){
//                found = true;
//                if(new_quantity == 0){
//                    cartData.remove(i);
//                    break;
//                }
//                total = new_quantity * total;
//                cartData.get(i).setQuantity(new_quantity);
//                cartData.get(i).setTotal(df.format(total));
//                break;
//            }
//        }
        // If no previous similar item is added into cart create new row in the display cart list
//        if(!found){
//            // If the quantity selected is 0, return without doing anything
//            if(new_quantity == 0)
//                return;
//            // Else add into the cart list
//            else{
//                total = new_quantity * total;
//                dbHandler.addCart(new CartListData(food, new_quantity, df.format(total)));
////                cartData.add(new CartListData(food, new_quantity, df.format(total)));
//            }
//        }
        // Update the static variable of cart list
        dbHandler.readCart();
//        PrefConfigCartList.writeListInPref(context, cartData);

        // Update the current stall id in the cart
        CartActivity.setOrderStall(HomeActivity.getSelectedStall());

        // Store stall id of order into shared preference
        SharedPreferences sharedPreferences = context.getSharedPreferences("stallID",MODE_PRIVATE);

        // Edit stall id of order in shared preference
        SharedPreferences.Editor edit_stallId = sharedPreferences.edit();
        edit_stallId.putInt("stallID",stallID);
        edit_stallId.apply();
    }
}

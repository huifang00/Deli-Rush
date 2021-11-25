package com.example.delirush.FoodStallActivity;
/*****************************
 * Code is referred from
 *    Title: Custom Dialog + Sending Information to Activity - Android Studio Tutorial
 *    Author: Coding in Flow
 *    Date: 6/10/2017
 *    Code version: 1.0
 *    Availability: https://www.youtube.com/watch?v=ARezg1D9Zd0
 *
 *****************************/
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.delirush.CartActivity;
import com.example.delirush.HomeActivity;
import com.example.delirush.R;

public class QuantityDialog extends AppCompatDialogFragment {
    private EditText quantity;
    private QuantityDialogListener listener;
    private String food;
    private String price;

    public QuantityDialog(String food) {
        this.food = food;
    }

    /**
     * Create the dialog to set the quantity of food
     *
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String curr_quantity = "0";

        for (int i = 0; i < CartActivity.cartData.size(); i++) {
            if (CartActivity.cartData.get(i).getFood().equals(food)) {
                curr_quantity = String.valueOf(CartActivity.cartData.get(i).getQuantity());
                break;
            }
        }

        switch (HomeActivity.getSelectedStall()) {
            case 0:
                price = ChineseStallActivity.getPrice();
                break;
            case 1:
                price = MalayStallActivity.getPrice();
                break;
            case 2:
                price = BeverageStallActivity.getPrice();
                break;
        }
        // Build the alert dialog to change the quantity
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.activity_quantity_dialog, null);
        quantity = view.findViewById(R.id.quantity_dialog);
        quantity.setText(curr_quantity);

        builder.setView(view)
                .setTitle(food)
                .setMessage("Price per quantity: RM" + price)
                .setPositiveButton("ADD TO CART", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String edit_quantity = quantity.getText().toString();
                        dialogInterface.dismiss();
                        listener.applyTexts(edit_quantity);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (QuantityDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement QuantityDialogListener");
        }
    }

    // Listener on the Quantity Dialog, if there is any changes
    public interface QuantityDialogListener {
        void applyTexts(String edit_quantity);
    }
}
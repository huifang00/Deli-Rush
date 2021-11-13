package com.example.delirush.FoodStallActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.delirush.CartListData;
import com.example.delirush.FoodStallActivity.BeverageStallActivity;
import com.example.delirush.FoodStallActivity.ChineseStallActivity;
import com.example.delirush.FoodStallActivity.MalayStallActivity;
import com.example.delirush.HomeActivity;
import com.example.delirush.PrefConfigCartList;
import com.example.delirush.R;

import java.util.ArrayList;


public class QuantityDialog extends AppCompatDialogFragment {
    private EditText quantity;
    private QuantityDialogListener listener;
    private String food;
    private String price;

    public QuantityDialog(String food){
        this.food = food;
    }

    /**
     * Create the dialog to set the quantity of food
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String curr_quantity = "0";
        ArrayList<CartListData> cartData = (ArrayList<CartListData>) PrefConfigCartList.readListFromPref(getActivity().getApplicationContext());

        for(int i=0;i<cartData.size();i++){
            if(cartData.get(i).getFood().equals(food)){
                curr_quantity = cartData.get(i).getQuatity();
                break;
            }
        }

        switch(HomeActivity.getSelectedStall()){
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
        builder.setCancelable(false);  //prevent getting dismissed by back key
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
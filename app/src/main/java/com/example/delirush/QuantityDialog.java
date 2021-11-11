package com.example.delirush;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;


public class QuantityDialog extends AppCompatDialogFragment {
    private EditText quantity;
    private QuantityDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.activity_quantity_dialog, null);

        builder.setView(view)
                .setTitle(ChineseStallActivity.food)
                .setMessage("Price per quantity: RM" + ChineseStallActivity.price)
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
        quantity = view.findViewById(R.id.quantity);
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

    public interface QuantityDialogListener {
        void applyTexts(String edit_quantity);
    }
}
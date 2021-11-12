package com.example.delirush;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PrefConfigCartList {
    private static String LIST_KEY = "cart_list";

    /**
     * Write the list into shared preferences
     * @param context
     * @param list
     */
    public static void writeListInPref(Context context, List<CartListData> list){
        Gson gson = new Gson();
        String jsonString = gson.toJson(list);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LIST_KEY, jsonString);
        editor.apply();
    }

    /**
     * Read the list from preferences
     * @param context
=     */
    public static List<CartListData> readListFromPref(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = pref.getString(LIST_KEY, "");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<CartListData>>() {}.getType();
        List<CartListData> list = gson.fromJson(jsonString, type);
        return list;
    }
}

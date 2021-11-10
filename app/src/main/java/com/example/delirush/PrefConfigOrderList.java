package com.example.delirush;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PrefConfigOrderList {
    private static String LIST_KEY = "list_key";
    public static void writeListInPref(Context context, List<OrderListData> list){
        Gson gson = new Gson();
        String jsonString = gson.toJson(list);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LIST_KEY, jsonString);
        editor.apply();
    }

    public static List<OrderListData> readListFromPref(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = pref.getString(LIST_KEY, "");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<OrderListData>>() {}.getType();
        List<OrderListData> list = gson.fromJson(jsonString, type);
        return list;
    }
}

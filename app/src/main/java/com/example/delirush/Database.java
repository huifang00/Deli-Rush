package com.example.delirush;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;

public class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "deliRushDB.db";
    private static final String TABLE_CART = "cartTable";
    private static final String FOOD_ID = "foodId";
    private static final String FOOD = "food";
    private static final String QUANTITY = "quantity";
    private static final String TOTAL = "total";

    private static final String TABLE_ORDER = "orderTable";
    private static final String ORDER_ID = "orderId";
    private static final String FOOD_STALL = "foodStall";
    private static final String STATUS = "status";

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        System.out.println("constructor database");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("onCreateDatabase");
        String CREATE_PRODUCTS_TABLE;
        CREATE_PRODUCTS_TABLE = "CREATE TABLE "
                + TABLE_CART + "("
                + FOOD_ID + " INTEGER PRIMARY KEY,"
                + FOOD + " TEXT, "
                + QUANTITY + " INTEGER,"
                + TOTAL + " REAL"
                + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
        CREATE_PRODUCTS_TABLE = "CREATE TABLE "
                + TABLE_ORDER + "("
                + ORDER_ID + " INTEGER PRIMARY KEY,"
                + FOOD_STALL + " TEXT, "
                + STATUS + " TEXT" + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER);
        onCreate(db);
    }

    public void addCart(CartListData cart){
        System.out.println("addCart1");
        ContentValues values = new ContentValues();
        values.put(FOOD, cart.getFood());
        values.put(QUANTITY, cart.getQuantity());
        values.put(TOTAL, cart.getTotal());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_CART, null, values);
        db.close();
        System.out.println("addCart2");
    }

    public void deleteCart(){
        SQLiteDatabase db = this.getWritableDatabase();
        // clear previous data
        CartActivity.cartData.clear();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        db.execSQL("CREATE TABLE "
                + TABLE_CART + "("
                + FOOD_ID + " INTEGER PRIMARY KEY,"
                + FOOD + " TEXT, "
                + QUANTITY + " INTEGER,"
                + TOTAL + " REAL"
                + ")");
        db.close();
    }

    // update the static variable of cart data
    public void readCart(){
        String query = "SELECT * FROM " + TABLE_CART;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        // clear previous data and synchronize with database
        CartActivity.cartData.clear();
        if(cursor.moveToFirst()) {
            while(!cursor.isAfterLast()){
                String food = cursor.getString(1);
                int quantity = cursor.getInt(2);
                String total = cursor.getString(3);
                CartActivity.cartData.add(new CartListData(food, quantity, total));
                cursor.moveToNext();
            }
        }
    }

    public void updateCart(String food, int quantity, String total){
        SQLiteDatabase db = this.getWritableDatabase();
        String UPDATE_ORDER_STATUS;
        // if the new quantity is 0, remove the row from database
        if (quantity == 0)
             UPDATE_ORDER_STATUS = "DELETE FROM " + TABLE_CART +
                    " WHERE "  + FOOD + "=\"" + food +"\"";
        // else update new quantity and total
        else
            UPDATE_ORDER_STATUS = "UPDATE " + TABLE_CART + " SET " +
                    QUANTITY + "=" + quantity + ", " +
                    TOTAL + "=" + total +
                    " WHERE "  + FOOD + "=\"" + food +"\"";
        db.execSQL(UPDATE_ORDER_STATUS);
        db.close();
        System.out.println("updateCart2");
    }

    public boolean findProduct(String food){
        boolean result = false;
        String query = "SELECT * FROM " + TABLE_CART + " WHERE " +
                FOOD + " =\"" + food + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // if there is data found return true
        if(cursor.moveToFirst()) {
            cursor.close();
            result = true;
        }else {
            result = false;
        }
        db.close();
        return result;
    }

    // update the static variable of cart data
    public void readOrder(){
        String query = "SELECT * FROM " + TABLE_ORDER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        // clear previous data and synchronize with database
        OrderActivity.orderData.clear();
        if(cursor.moveToFirst()) {
            while(!cursor.isAfterLast()){
                int orderID = Integer.parseInt(cursor.getString(0));
                String orderFoodStall = cursor.getString(1);
                String orderStatus = cursor.getString(2);
                OrderActivity.orderData.add(new OrderListData(orderID, orderFoodStall, orderStatus));
                cursor.moveToNext();
            }
        }
    }

    public int getOrderID(){
        String query = "SELECT * FROM " + TABLE_ORDER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToLast();
        int id = Integer.parseInt(cursor.getString(0));
        cursor.close();
        db.close();
        return id;
    }

    public void addOrder(OrderListData order){
        ContentValues values = new ContentValues();
        values.put(FOOD_STALL, order.getOrderFoodStall());
        values.put(STATUS, order.getOrderStatus());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_ORDER, null, values);
        db.close();
    }

    public void updateOrder(int orderID, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        String UPDATE_ORDER_STATUS = "UPDATE " + TABLE_ORDER + " SET " +
                STATUS + "=\"" + status +
                "\"WHERE "  + ORDER_ID + "=\"" + orderID + "\"";
        db.execSQL(UPDATE_ORDER_STATUS);
        db.close();
        // update the static variable of order data
    }

    public int findReadyOrder(){
        int result = -1;
        String query = "SELECT * FROM " + TABLE_ORDER + " WHERE " +
                STATUS + " =\"" + "Ready" + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // if there is data found return true
        if(cursor.moveToFirst()) {
            result = Integer.parseInt(cursor.getString(0));
        }else {
            result = -1;
        }
        db.close();
        return result;
    }

    public boolean findPlacedOrder(){
        boolean result = false;
        String query = "SELECT * FROM " + TABLE_ORDER + " WHERE " +
                STATUS + " =\"" + "Order Placed" + "" + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // if there is data found return true
        if(cursor.moveToFirst()) {
            result = true;
        }else {
            result = false;
        }
        db.close();
        return result;
    }
}

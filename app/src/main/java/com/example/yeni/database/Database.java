package com.example.yeni.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.yeni.Common.Common;
import com.example.yeni.model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {
        private static final String DB_NAME="marketOrderingAppDBs.db";
        private static final int DB_VER=1;

        public Database(Context context){

            super(context, DB_NAME, null,  DB_VER);
        }

    public List<Order> getCarts()
    {
        SQLiteDatabase db=getReadableDatabase();
        SQLiteQueryBuilder qb= new SQLiteQueryBuilder();

        String[] sqlSelect={"ProductId","ProductName","Quantity","Price","Discount","PhoneNumber"};
        String sqlTable="OrderDetail";

        qb.setTables(sqlTable);

        Cursor c=qb.query(db, sqlSelect,null,null,null,null,null);

        final List<Order> result= new ArrayList<>();
        if(c.moveToFirst()){
            do{
                if(Common.currentUser.getPhoneNumber().equals(c.getString(c.getColumnIndex("PhoneNumber"))))
                {
                    result.add(new Order(
                        c.getString(c.getColumnIndex("ProductId")),
                        c.getString(c.getColumnIndex("ProductName")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("Price")),
                        c.getString(c.getColumnIndex("Discount")),
                        c.getString(c.getColumnIndex("PhoneNumber"))
                        ));
                }

            }while(c.moveToNext());
        }
        return result;
    }

    public void addToCart(Order order)
    {

        SQLiteDatabase db= getReadableDatabase();

        String query= String.format("INSERT INTO OrderDetail(ProductId, ProductName, Quantity, Price, Discount, PhoneNumber) VALUES('%s','%s','%s','%s','%s' ,'%s'  );",
                order.getProductId(),
                order.getProductName(),
                order.getQuantity(),
                order.getPrice(),
                order.getDiscount(),
                Common.currentUser.getPhoneNumber());

        db.execSQL(query);


    }

        public void cleanCart()
        {
            SQLiteDatabase db= getReadableDatabase();
            String query= String.format("DELETE FROM OrderDetail");
            db.execSQL(query);
        }
}



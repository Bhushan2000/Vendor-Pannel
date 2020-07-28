package com.vendor.vendorpannel.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseForPractise extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Add_Product.db";

    public static final String TABLE_NAME = "Add_Product_table";
    public static final String COl_1 = "ID";
    public static final String COl_2 = "PRODUCT_TITLE";
    public static final String COl_3 = "PRODUCT_CATEGORY";
    public static final String COl_4 = "PRODUCT_SUBCATEGORY";

    public static final String COl_5 = "PRODUCT_PRICE";
    public static final String COl_6 = "PRODUCT_DISCOUNT_IN_PERCENTAGE";
    public static final String COl_7 = "PRODUCT_DISCOUNT_IN_RUPEES";

    public static final String COl_8 = "PRODUCT_NO_OF_PIECES";
    public static final String COl_9 = "PRODUCT_MIN_COMPULSORY_STOCK";

    public static final String COl_10 = "PRODUCT_GST_IN_PERCENTAGE";
    public static final String COl_11 = "PRODUCT_GST_IN_RUPEES";
    public static final String COl_12 = "PRODUCT_DESCRIPTION";


    public DatabaseForPractise(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,PRODUCT_TITLE TEXT,PRODUCT_CATEGORY TEXT, PRODUCT_SUBCATEGORY TEXT ,PRODUCT_PRICE INTEGER, PRODUCT_DISCOUNT_IN_PERCENTAGE INTEGER ,PRODUCT_DISCOUNT_IN_RUPEES INTEGER,PRODUCT_NO_OF_PIECES INTEGER, PRODUCT_MIN_COMPULSORY_STOCK INTEGER,PRODUCT_GST_IN_PERCENTAGE INTEGER,PRODUCT_GST_IN_RUPEES INTEGER,PRODUCT_DESCRIPTION TEXT )");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(
                db
        );


    }

    public boolean insertData(String productTitle, String productCategory, String productSubCategory
                                ,String productPrice,String productPriceInDiscount, String productPriceRupees
                                ,String noOfStock,String minCompulsoryStock, String productGSTInRupees,
                                 String productGSTInDiscount,String description) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COl_2, productTitle);
        contentValues.put(COl_3, productCategory);
        contentValues.put(COl_4, productSubCategory);

        contentValues.put(COl_5, productPrice);
        contentValues.put(COl_6, productPriceInDiscount);
        contentValues.put(COl_7, productPriceRupees);
        contentValues.put(COl_8, noOfStock);
        contentValues.put(COl_9, minCompulsoryStock);
        contentValues.put(COl_10, productGSTInDiscount);
        contentValues.put(COl_11, productGSTInRupees);
        contentValues.put(COl_12, description);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;

        } else {
            return true;

        }


    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_NAME, null);
        return res;


    }

    public boolean updateData(String id, String name, String surname, String marks) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COl_1, id);
        contentValues.put(COl_2, name);
        contentValues.put(COl_3, surname);
        contentValues.put(COl_4, marks);
        db.update(TABLE_NAME, contentValues, "id = ?", new String[]{id});
        return true;


    }

    public Integer deleteData(String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "id = ?", new String[]{id});


    }
}

package com.vendor.vendorpannel.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ParseException;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Database extends SQLiteOpenHelper {
    private Database mDbHelper;

    public static final String DATABASE_NAME = "Documents.db";

    public static final String TABLE_NAME1 = "PANdetails_table";
    public static final String TABLE_NAME2 = "Aadhardetails_table";
    public static final String TABLE_NAME3 = "AddProducts_table";
    // PAN DATA
    public static final String COl_1 = "ID";
    public static final String COl_2 = "NAME";
    public static final String COl_3 = "FATHERNAME";
    public static final String COl_4 = "PANCARDNUMBER";
    public static final String COl_5 = "DOB";
//    public static final String COl_6 = "SIGNATURE";
//    public static final String COl_7 = "PHOTO";

    // AADHAR DATA
    public static final String COl_8 = "ID";
    public static final String COl_9 = "NAME";
    public static final String COl_10 = "VIDNUMBER";
    public static final String COl_11 = "AADHARCARDNUMBER";
    //    public static final String COl_12 = "DOB"
    public static final String COl_13 = "GENDER";
//    public static final String COl_14 = "PHOTO";


    // Add product
    public static final String COl_15 = "ID";
    public static final String COl_16 = "PRODUCT_TITLE";
    public static final String COl_17 = "PRODUCT_CATEGORY";
    public static final String COl_18 = "PRODUCT_SUBCATEGORY";
    public static final String COl_20 = "PRODUCT_IMAGES";

    public static final String COl_21 = "PRODUCT_PRICE";
    public static final String COl_22 = "PRODUCT_DISCOUNT_IN_PERCENTAGE";
    public static final String COl_23 = "PRODUCT_DISCOUNT_IN_RUPEES";

    public static final String COl_24 = "PRODUCT_NO_OF_PIECES";
    public static final String COl_25 = "PRODUCT_MIN_COMPULSORY_STOCK";

    public static final String COl_26 = "PRODUCT_GST_IN_PERCENTAGE";
    public static final String COl_27 = "PRODUCT_GST_IN_RUPEES";
    public static final String COl_28 = "PRODUCT_DESCRIPTION";


    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);


    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        java.util.Date date = new java.util.Date();
        long datetime = date.getTime();
        // FOR PAN
        db.execSQL("create table " + TABLE_NAME1 + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,FATHERNAME TEXT, PANCARDNUMBER INTEGER)");
        // FOR AADHAR
        db.execSQL("create table " + TABLE_NAME2 + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,VIDNUMBER INTEGER, AADHARCARDNUMBER INTEGER ,GENDER TEXT)");
        // FOR ADD PRODUCT
        db.execSQL("create table " + TABLE_NAME3 + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, PRODUCT_TITLE TEXT , PRODUCT_CATEGORY TEXT, PRODUCT_SUBCATEGORY TEXT    )");





    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
        onCreate(
                db
        );


    }
    private String getDateTime() {

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


    // FOR PAN
    public boolean insertData(String name, String fatherName, String panNumber ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();



        contentValues.put(COl_2, name);
        contentValues.put(COl_3, fatherName);
        contentValues.put(COl_4, panNumber);
//        contentValues.put(COl_5,  datetime);
//        contentValues.put(COl_6,signature);
//        contentValues.put(COl_7,photo);




        long result = db.insert(TABLE_NAME1, null, contentValues);
        if (result == -1) {
            return false;

        } else {
            return true;

        }


    }
    // FOR AADHAR
    public boolean insertData1(String name, String vidNumber, String aadharNumber,String gender ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COl_9, name);
        contentValues.put(COl_10, vidNumber);
        contentValues.put(COl_11, aadharNumber);
//        contentValues.put(COl_5,dob);
        contentValues.put(COl_13, gender);
//        contentValues.put(COl_7,photo);
        long result = db.insert(TABLE_NAME2, null, contentValues);
        if (result == -1) {
            return false;

        } else {
            return true;

        }


    }

//    public Database open() throws SQLException {
//        SQLiteDatabase db = this.getWritableDatabase();
//        return this;
//    }
//
//    public void close() {
//        mDbHelper.close();
//    }

    // FOR ADD PRODUCT
    public boolean insertData2(String productTitle, String productCategory, String productSubCategory
//            ,String productPrice
//            ,String productPriceInDiscount, String productPriceRupees
//                               ,String noOfStock,String minCompulsoryStock, String productGSTInRupees,String productGSTInDiscount,String description
    ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COl_16, productTitle);
        contentValues.put(COl_17, productCategory);
        contentValues.put(COl_18, productSubCategory);
//        contentValues.put(COl_20, imageBytes);
//        contentValues.put(COl_21, productPrice);
//        contentValues.put(COl_22, productPriceInDiscount);
//        contentValues.put(COl_23, productPriceRupees);
//        contentValues.put(COl_24, noOfStock);
//        contentValues.put(COl_25, minCompulsoryStock);
//        contentValues.put(COl_26, productGSTInDiscount);
//        contentValues.put(COl_27, productGSTInRupees);
//        contentValues.put(COl_28, description);


        long result = db.insert(TABLE_NAME3, null, contentValues);
        if (result == -1) {
            return false;

        } else {
            return true;

        }


    }

//
//    public static String formatDateTime(Context context, String timeToFormat) {
//
//        String finalDateTime = "";
//
//        SimpleDateFormat iso8601Format = new SimpleDateFormat(
//                "yyyy-MM-dd HH:mm:ss");
//
//        Date date = null;
//        if (timeToFormat != null) {
//            try {
//                date = iso8601Format.parse(timeToFormat);
//            } catch (ParseException | java.text.ParseException e) {
//                date = null;
//            }
//
//            if (date != null) {
//                long when = date.getTime();
//                int flags = 0;
//                flags |= android.text.format.DateUtils.FORMAT_SHOW_TIME;
//                flags |= android.text.format.DateUtils.FORMAT_SHOW_DATE;
//                flags |= android.text.format.DateUtils.FORMAT_ABBREV_MONTH;
//                flags |= android.text.format.DateUtils.FORMAT_SHOW_YEAR;
//
//                finalDateTime = android.text.format.DateUtils.formatDateTime(context,
//                        when + TimeZone.getDefault().getOffset(when), flags);
//            }
//        }
//        return finalDateTime;
//    }


}

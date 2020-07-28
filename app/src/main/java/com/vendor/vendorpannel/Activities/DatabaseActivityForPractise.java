package com.vendor.vendorpannel.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.vendor.vendorpannel.Databases.Database;
import com.vendor.vendorpannel.Databases.DatabaseForPractise;
import com.vendor.vendorpannel.R;

public class DatabaseActivityForPractise extends AppCompatActivity {
    private DatabaseForPractise db;
    private EditText name, surname, marks, id;
    private Button submit, viewAll, delete;

    // Product Info
    private EditText productTitle;
    //    private EditText category;
    private ImageView productImage;
    //    private EditText subCategory;
    private Spinner subCategory;
    private Spinner category;
    //pricing info
    private EditText productPrice;
    private EditText productPriceDiscou;
    private EditText productPriceInRupe;
    //Stock info
    private EditText noOfPieces;
    private EditText minCompulsoryStock;
    //General info
    private EditText gstInPercentage;
    private EditText gstInRupees;
    private EditText productDescription;

    private String string1;
    private String string2;
    private ImageButton addImage;
    private Uri ImageUri;
    private Uri resultUri;
    private Uri imageUri;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_for_practise);


        db = new DatabaseForPractise(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        getSupportActionBar().setTitle("Add new Product");


        // Product Info
        productTitle = findViewById(R.id.productTitle);
//        category = findViewById(R.id.category);
        productImage = findViewById(R.id.productImage);

//        subCategory = findViewById(R.id.subCategory);

        category = findViewById(R.id.category);
        subCategory = findViewById(R.id.subCategory);
//pricing info
        productPrice = findViewById(R.id.product_price);
        productPriceDiscou = findViewById(R.id.productPriceInDiscount);
        productPriceInRupe = findViewById(R.id.productPriceDiscountInRupees);
//Stock info
        noOfPieces = findViewById(R.id.noOfPieces);
        minCompulsoryStock = findViewById(R.id.minCompulsoryStock);
//General info
        gstInPercentage = findViewById(R.id.gstInPercentage);
        gstInRupees = findViewById(R.id.gstInRupees);
        productDescription = findViewById(R.id.productDescription);


        viewAll = findViewById(R.id.view_all);
        delete = findViewById(R.id.delete);
        id = findViewById(R.id.id);
        submit = findViewById(R.id.submit);

        addImage = findViewById(R.id.addImage);





        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addImageDialog();
            }
        });
//
//
//        // Check spinner values
//
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                string1 = (String) category.getItemAtPosition(category.getSelectedItemPosition());
                Toast.makeText(DatabaseActivityForPractise.this, "Selected Item " + string1, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(DatabaseActivityForPractise.this, "Selected Item please", Toast.LENGTH_SHORT).show();

            }
        });
        subCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                string2 = (String) subCategory.getItemAtPosition(subCategory.getSelectedItemPosition());
                Toast.makeText(DatabaseActivityForPractise.this, "Selected Item " + string2, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(DatabaseActivityForPractise.this, "Selected Item please", Toast.LENGTH_SHORT).show();

            }
        });


        addData();
        ViewAll();

    }

    public void addData() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isInserted = db.insertData(

                        productTitle.getText().toString(),
//                        category.getText().toString(),
//                        subCategory.getText().toString(),

                        string1,
                        string2,
                        productPrice.getText().toString()
                        , productPriceDiscou.getText().toString(),
                        productPriceInRupe.getText().toString()
                        , noOfPieces.getText().toString(),
                        minCompulsoryStock.getText().toString(),
                        gstInPercentage.getText().toString(),
                        gstInRupees.getText().toString(),
                        productDescription.getText().toString()

                );
                if (isInserted) {
                    Toast.makeText(DatabaseActivityForPractise.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DatabaseActivityForPractise.this, "Data Not Inserted", Toast.LENGTH_SHORT).show();


                }

            }
        });
    }

    public void ViewAll() {
        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = db.getAllData();
                if (res.getCount() == 0) {
                    // show message

                    showMessage("error", "Nothing Found");


                    return;
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    while (res.moveToNext()) {
                        stringBuilder.append("Id:" + "\t" + res.getString(0) + "\n");
                        stringBuilder.append("productTitle:" + "\t" + res.getString(1) + "\n");
                        stringBuilder.append("category:" + "\t" + res.getString(2) + "\n");
                        stringBuilder.append("subCategory:" + "\t" + res.getString(3) + "\n");
                        stringBuilder.append("productPrice:" + "\t" + res.getString(4) + "\n");
                        stringBuilder.append("productPriceDiscount:" + "\t" + res.getString(5) + "\n");
                        stringBuilder.append("productPriceInRupees:" + "\t" + res.getString(6) + "\n");
                        stringBuilder.append("noOfPieces:" + "\t" + res.getString(7) + "\n");
                        stringBuilder.append("minCompulsoryStock:" + "\t" + res.getString(8) + "\n");
                        stringBuilder.append("gstInPercentage:" + "\t" + res.getString(9) + "\n");
                        stringBuilder.append("gstInRupees:" + "\t" + res.getString(10) + "\n");
                        stringBuilder.append("productDescription:" + "\t" + res.getString(11) + "\n\n");


                    }

                    // Show all data
                    showMessage("Data", stringBuilder.toString());

                }

            }
        });
    }

    public void showMessage(String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
        deleteData();


    }

    public void deleteData() {
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deleteRows = db.deleteData(id.getText().toString());
                if (deleteRows > 0) {


                    Toast.makeText(DatabaseActivityForPractise.this, "Data  deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DatabaseActivityForPractise.this, "Data Not   deleted", Toast.LENGTH_SHORT).show();


                }
            }
        });
    }
    private void addImageDialog() {
        String options[] = {"Take a photo", "Select a photo from gallery"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Choose Action");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {


                } else if (which == 1) {
                    Intent gallery = new Intent();
                    gallery.setAction(Intent.ACTION_GET_CONTENT);
                    gallery.setType("image/*");
                    startActivityForResult(Intent.createChooser(gallery, "Select Picture"), 1);

                }
            }
        });

        builder.create().show();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();


        }
        return true;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            ImageUri = data.getData();
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);

            // for Adding Image In Database

            if (null != ImageUri) {
                productImage.setImageURI(imageUri);
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {



                resultUri = result.getUri();
                Glide.with(this).load(resultUri).into(productImage);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();

                Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

}
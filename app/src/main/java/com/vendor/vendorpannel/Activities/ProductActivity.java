package com.vendor.vendorpannel.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.vendor.vendorpannel.Databases.Database;
import com.vendor.vendorpannel.Databases.Utils;
import com.vendor.vendorpannel.R;

import java.io.IOException;
import java.io.InputStream;

public class ProductActivity extends AppCompatActivity {

    private static final String TAG = "ProductActivity";
    private LinearLayout discountLayout, gstLayout;
    private Switch aSwitch, bSwitch;
    private Dialog loadingDialogue;

    private ImageView productImageView;
    final int REQUEST_EXTERNAL_STORAGE = 100;
    private Database db;
    private Uri selectedImageUri;
    private Uri imageUri;
    private Uri resultUri;
    private Toolbar toolbar;

    // Product Info
    private EditText productTitle;
    private Spinner category, subCategory;
    private ImageView productImage;
    private ImageButton addImage;


    private String string1;
    private String string2;

     private EditText productPrice;
     private EditText productPriceDiscount;
     private EditText productPriceInRupees;
     //Stock info
     private EditText noOfPieces;
    private EditText minCompulsoryStock;
    //General info
    private EditText gstInPercentage;
     private EditText gstInRupees;
     private EditText productDescription;
     // Add product button
     private Button AddProduct;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);


        // Product Info
        productTitle = findViewById(R.id.productTitle);
        category = findViewById(R.id.category);
        productImage = findViewById(R.id.productImage);
        subCategory = findViewById(R.id.subCategory);

        //pricing info
        productPrice = findViewById(R.id.productPrice);
        productPriceDiscount = findViewById(R.id.productPriceDiscount);
        productPriceInRupees = findViewById(R.id.productPriceRupees);

        //Stock info
        noOfPieces = findViewById(R.id.noOfPieces);
        minCompulsoryStock = findViewById(R.id.minCompulsoryStock);

        //General info
        gstInPercentage = findViewById(R.id.gstInPercent);
        gstInRupees = findViewById(R.id.gstInRupees);
        productDescription = findViewById(R.id.productDescription);


        AddProduct = findViewById(R.id.AddProduct);


        aSwitch = findViewById(R.id.switch1);
        bSwitch = findViewById(R.id.switch2);
        discountLayout = findViewById(R.id.discountLayout);
        gstLayout = findViewById(R.id.gstLayout);
        addImage = findViewById(R.id.addImage);
        productImageView = findViewById(R.id.imageView);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        getSupportActionBar().setTitle("Add new Product");


        ///////////////////////////////////loading dialogue/////////////////////////////////////////////

        loadingDialogue = new Dialog(this);
        loadingDialogue.setContentView(R.layout.loadingprogressdialogue);
        loadingDialogue.setCancelable(false);
        loadingDialogue.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialogue.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


/////////////////////////////////////loading dialogue/////////////////////////////////////////////


        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    discountLayout.setVisibility(View.VISIBLE);
                } else {
                    discountLayout.setVisibility(View.GONE);
                }
            }
        });
        bSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    gstLayout.setVisibility(View.VISIBLE);
                } else {
                    gstLayout.setVisibility(View.GONE);
                }
            }
        });
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addImageDialog();
            }
        });


        // Check spinner values

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                string1 = (String) category.getItemAtPosition(category.getSelectedItemPosition());
                Toast.makeText(ProductActivity.this, "Selected Item " + string1, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(ProductActivity.this, "Selected Item please", Toast.LENGTH_SHORT).show();

            }
        });
        subCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                string2 = (String) subCategory.getItemAtPosition(subCategory.getSelectedItemPosition());
                Toast.makeText(ProductActivity.this, "Selected Item " + string2, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(ProductActivity.this, "Selected Item please", Toast.LENGTH_SHORT).show();

            }
        });


        db = new Database(this);


        AddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!TextUtils.isEmpty(productTitle.getText().toString())) {
                    if (!TextUtils.isEmpty(category.getSelectedItem().toString())) {
                        if (!TextUtils.isEmpty(subCategory.getSelectedItem().toString())) {
//                            if (productImage.getDrawable()!=null) {


//                                saveImageInDB();


                            boolean isInserted = db.insertData2(productTitle.getText().toString(),
                                    string1, string2
//                                    ,productPrice.getText().toString()
//                                    ,productPriceDiscount.getText().toString(),
//                                    productPriceInRupees.getText().toString()
//                                    ,noOfPieces.getText().toString(),
//                                    minCompulsoryStock.getText().toString(),
//                                    gstInPercentage.getText().toString(),
//                                    gstInRupees.getText().toString(),
//                                    productDescription.getText().toString()
                            );


                            if (isInserted) {
                                Toast.makeText(ProductActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();


//                                    Toast.makeText(PANActivity.this, PanCardNumber + " is Matching",
//                                            Toast.LENGTH_LONG).show();


//                                    loadingDialogue.show();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {


                                        startActivity(new Intent(ProductActivity.this, VerificationActivity.class));
                                        finish();
                                    }
                                }, 5000);

                            } else {
                                Toast.makeText(ProductActivity.this, "Data Not Inserted", Toast.LENGTH_SHORT).show();


                            }


//                            }else{
//                            Toast.makeText(ProductActivity.this, "Select Image", Toast.LENGTH_SHORT).show();

//
//                            }

                        } else {
                            TextView errorText = (TextView) category.getSelectedView();
                            errorText.setError("Select Category");
                            errorText.setTextColor(Color.RED);//just to highlight that this is an error
                            errorText.setText("my actual error text");//changes the selected item text to this

                        }

                    } else {
                        TextView errorText1 = (TextView) subCategory.getSelectedView();
                        errorText1.setError("Select subCategory");
                        errorText1.setTextColor(Color.RED);//just to highlight that this is an error
                        errorText1.setText("my actual error text");//changes the selected item text to this
                    }

                } else {

                    productTitle.setError("Please enter  product Title");
                    productTitle.requestFocus();

                }
            }
        });


    }

    private void addImageDialog() {
        String options[] = {"Take a photo", "Select a photo from gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
            Uri ImageUri = data.getData();
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);
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

//    boolean saveImageInDB() {
//
//        try {
//
//            InputStream iStream = getContentResolver().openInputStream(resultUri);
//            byte[] inputData = Utils.getBytes(iStream);
////            db.insertData2(productTitle.getText().toString(),category.getSelectedItem().toString(),category.getSelectedItem().toString());
//
//            return true;
//        } catch (IOException ioe) {
//            Log.e(TAG, "<saveImageInDB> Error : " + ioe.getLocalizedMessage());
//            db.close();
//            return false;
//        }
//
//    }
}
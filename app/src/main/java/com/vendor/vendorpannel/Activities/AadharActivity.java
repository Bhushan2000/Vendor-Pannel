package com.vendor.vendorpannel.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.bumptech.glide.Glide;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.vendor.vendorpannel.Databases.Database;
import com.vendor.vendorpannel.R;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class AadharActivity extends AppCompatActivity {

    private CircleImageView profile;
    private Dialog loadingDialogue;


    private Button conti;

    private EditText etName;
    private EditText etVIDNumber;
    private EditText etAadharCardNumber;
    private EditText etDOB;

    private CircleImageView imgPhoto;

    // Gender
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    private Button btnDisplay;
    private String genderSelected;


    private Toolbar toolbar;

    String Name;
    String VIDNumber;
    String AadharCardNumber;
    String DOB;

    //database reference
    private Database db;


    @SuppressLint("WrongViewCast")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aadhar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        getSupportActionBar().setTitle("Aadhar Verification");

        ///////////////////////////////////loading dialogue/////////////////////////////////////////////

        loadingDialogue = new Dialog(this);
        loadingDialogue.setContentView(R.layout.loadingprogressdialogue);
        loadingDialogue.setCancelable(false);
        loadingDialogue.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialogue.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


/////////////////////////////////////loading dialogue/////////////////////////////////////////////


        //initializing view objects

        conti = findViewById(R.id.btnContinue);

        etName = findViewById(R.id.etName);
        ;
        etVIDNumber = findViewById(R.id.etVidNumber);
        etAadharCardNumber = findViewById(R.id.etAadharCardNumber);
        etDOB = findViewById(R.id.etDOB);

        imgPhoto = findViewById(R.id.imgPhoto);

        //initializing awesomevalidation object
        /*
         * The library provides 3 types of validation
         * BASIC
         * COLORATION
         * UNDERLABEL
         * */
        final AwesomeValidation awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);


        awesomeValidation.addValidation(this, R.id.etName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);

        awesomeValidation.addValidation(this, R.id.etDOB, "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$", R.string.nameerror);


        radioSexGroup = (RadioGroup) findViewById(R.id.radioGroup);

        btnDisplay = (Button) findViewById(R.id.button);






            btnDisplay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int selectedId = radioSexGroup.getCheckedRadioButtonId();
                    radioSexButton    = (RadioButton) findViewById(selectedId);
                    genderSelected = radioSexButton.getText().toString();
                    Toast.makeText(AadharActivity.this, radioSexButton.getText(), Toast.LENGTH_SHORT).show();





                }
            });












        imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AadharActivity.this, "camera", Toast.LENGTH_SHORT).show();


                Intent gallery = new Intent();
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), 1);

            }
        });

        // instance of database
        db = new Database(this);

        conti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  Name = etName.getText().toString();
                  VIDNumber = etVIDNumber.getText().toString();
                  AadharCardNumber = etAadharCardNumber.getText().toString();
                  DOB = etDOB.getText().toString();


                if (!TextUtils.isEmpty(Name)) {
                    if (!TextUtils.isEmpty(VIDNumber)) {
                        if (!TextUtils.isEmpty(AadharCardNumber)) {
                            if (!TextUtils.isEmpty(DOB)) {
                                if (!TextUtils.isEmpty(btnDisplay.getText().toString())){
                                if (awesomeValidation.validate()) {


                                    // Aadhar Number Validation

                                    // Regex to check valid Aadhar number.
                                    String regex = "^[2-9]{1}[0-9]{3}\\s[0-9]{4}\\s[0-9]{4}$";
                                    // Compile the ReGex
                                    Pattern p = Pattern.compile(regex);

                                    // Pattern class contains matcher() method
                                    // to find matching between given string
                                    // and regular expression.
                                    Matcher m = p.matcher(AadharCardNumber);





                                    // If the string is empty
                                    // return false
                                    if (AadharCardNumber == null) {
                                        etAadharCardNumber.setError("Aadhar No Should be 12 digits");
                                        etAadharCardNumber.requestFocus();
                                    }else{

                                        // Return if the string
                                        // matched the ReGex
                                        m.matches();
                                        boolean isInserted = db.insertData1(etName.getText().toString(), etVIDNumber.getText().toString(), etAadharCardNumber.getText().toString() ,genderSelected);

                                        if (isInserted) {
                                            Toast.makeText(AadharActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();


//                                    Toast.makeText(AadharActivity.this, AadharCardNumber + " is Matching",
//                                            Toast.LENGTH_LONG).show();


//                                            loadingDialogue.show();

                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {


                                                    startActivity(new Intent(AadharActivity.this, VerificationActivity.class));
                                                    finish();
                                                }
                                            }, 5000);

                                        } else {
                                            Toast.makeText(AadharActivity.this, "Data Not Inserted", Toast.LENGTH_SHORT).show();


                                        }


                                    }

                                    // aadhar  Number Validation



                                } else {
                                    Toast.makeText(AadharActivity.this, "error", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                radioSexButton.setError("Please select gender");
                                radioSexButton.requestFocus();
                            }


                            } else {
                                etDOB.setError("Please enter  DOB");
                                etDOB.requestFocus();
                            }

                        } else {
                            etAadharCardNumber.setError("Please enter aadhar Number");
                            etAadharCardNumber.requestFocus();
                        }

                    } else {
                        etVIDNumber.setError("Please enter VID name");
                        etVIDNumber.requestFocus();
                    }

                } else {
                    etName.setError("Please enter name");
                    etName.requestFocus();

                }


            }
        });


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
                Uri resultUri = result.getUri();
                Glide.with(this).load(resultUri).into(imgPhoto);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();

                Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
                finish();
            return true;


        }
        return super.onOptionsItemSelected(item);

    }
}
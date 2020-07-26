package com.vendor.vendorpannel.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.net.ParseException;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.bumptech.glide.Glide;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.vendor.vendorpannel.Databases.Database;
import com.vendor.vendorpannel.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class PanVerificationActivity extends AppCompatActivity {

    private CircleImageView profile;
    private Dialog loadingDialogue;


    private Button conti;
    private ImageView imgCamera;
    private EditText etName;
    private EditText etFatherName;
    private EditText etPanCardNumber;
    private EditText etDOB;
    private CircleImageView imgPhoto;


    // signature
    private SignaturePad signaturePad;
    private Button saveButton, clearButton;

    // image
    private static final int PICK_IMAGE = 1;
    Uri imageUri;
    private Database db;
    private Toolbar toolbar;


    // insert data
    String Name;
    String FatherName;
    String PanCardNumber;
    String DOB;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pan_verification);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        getSupportActionBar().setTitle("PAN Verification");





        ///////////////////////////////////loading dialogue/////////////////////////////////////////////

        loadingDialogue = new Dialog(this);
        loadingDialogue.setContentView(R.layout.loadingprogressdialogue);
        loadingDialogue.setCancelable(false);
        loadingDialogue.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialogue.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


/////////////////////////////////////loading dialogue/////////////////////////////////////////////


        conti = findViewById(R.id.btnContinue);
        etPanCardNumber = findViewById(R.id.etPanCardNumber);
        etName = findViewById(R.id.etName);
        etFatherName = findViewById(R.id.etFatherName);
        etDOB = findViewById(R.id.etDOB);
        imgPhoto = findViewById(R.id.imgPhoto);

        db = new Database(this);



        //initializing awesomevalidation object
        /*
         * The library provides 3 types of validation
         * BASIC
         * COLORATION
         * UNDERLABEL
         * */
        final AwesomeValidation awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);


//        awesomeValidation.addValidation(this, R.id.etName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);

        awesomeValidation.addValidation(this, R.id.etDOB, "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$", R.string.dateerror);


        // signature
        signaturePad = findViewById(R.id.signaturePad);
        saveButton = findViewById(R.id.saveButton);
        clearButton = findViewById(R.id.clearButton);


        //disable both buttons at start
        saveButton.setEnabled(false);
        clearButton.setEnabled(false);


        signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {

            }

            @Override
            public void onSigned() {
                saveButton.setEnabled(true);
                clearButton.setEnabled(true);
            }

            @Override
            public void onClear() {
                saveButton.setEnabled(false);
                clearButton.setEnabled(false);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //write code for saving the signature here
                Toast.makeText(PanVerificationActivity.this, "Signature Saved", Toast.LENGTH_SHORT).show();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signaturePad.clear();
            }
        });


        imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PanVerificationActivity.this, "camera", Toast.LENGTH_SHORT).show();


                Intent gallery = new Intent();
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), 1);

            }
        });


//                String signature = signaturePad.getSignatureSvg();

        conti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Name = etName.getText().toString();
                FatherName = etFatherName.getText().toString();
                PanCardNumber = etPanCardNumber.getText().toString();
                DOB = etDOB.getText().toString();


                if (!TextUtils.isEmpty(Name)) {
                    if (!TextUtils.isEmpty(FatherName)) {
                        if (!TextUtils.isEmpty(PanCardNumber)) {
                            if (!TextUtils.isEmpty(DOB)) {
//                                if (!TextUtils.isEmpty(signature)) {
//                                    if (awesomeValidation.validate()) {




                                // Pan Number Validation
                                Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");


                                Matcher matcher = pattern.matcher(PanCardNumber);

                                if (matcher.matches()) {




                                    boolean isInserted = db.insertData(etName.getText().toString(), etFatherName.getText().toString(), etPanCardNumber.getText().toString());
                                    if (isInserted) {
                                        Toast.makeText(PanVerificationActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();


//                                    Toast.makeText(PANActivity.this, PanCardNumber + " is Matching",
//                                            Toast.LENGTH_LONG).show();


//                                        loadingDialogue.show();

                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {


                                                startActivity(new Intent(PanVerificationActivity.this, VerificationActivity.class));
                                                finish();
                                            }
                                        }, 5000);

                                    } else {
                                        Toast.makeText(PanVerificationActivity.this, "Data Not Inserted", Toast.LENGTH_SHORT).show();


                                    }


                                } else {
                                    loadingDialogue.dismiss();

                                    Toast.makeText(PanVerificationActivity.this, PanCardNumber + " is Not Matching",
                                            Toast.LENGTH_LONG).show();
                                }


//                                    } else {
//                                        Toast.makeText(PANActivity.this, "error", Toast.LENGTH_SHORT).show();
//
//                                    }

                            } else {
                                etDOB.setError("Please enter  DOB");
                                etDOB.requestFocus();
                            }

                        } else {
                            etPanCardNumber.setError("Please enter Pan Number");
                            etPanCardNumber.requestFocus();
                        }

                    } else {
                        etFatherName.setError("Please enter Father name");
                        etFatherName.requestFocus();
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
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.clear();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public static long getTimeMillis(String dateString, String dateFormat) throws ParseException, java.text.ParseException {
        /*Use date format as according to your need! Ex. - yyyy/MM/dd HH:mm:ss */
        String myDate = dateString;//"2017/12/20 18:10:45";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat/*"yyyy/MM/dd HH:mm:ss"*/);
        Date date = sdf.parse(myDate);
        long millis = date.getTime();

        return millis;
    }




}
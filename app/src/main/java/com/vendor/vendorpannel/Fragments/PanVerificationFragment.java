package com.vendor.vendorpannel.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.cloudinary.Transformation;
import com.cloudinary.android.MediaManager;
 import com.github.gcacace.signaturepad.views.SignaturePad;
import com.vendor.vendorpannel.HomeActivity;
import com.vendor.vendorpannel.R;
import com.vendor.vendorpannel.SplashActivity;
import com.vendor.vendorpannel.VerificationActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class PanVerificationFragment extends Fragment {
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



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_pan_verification, container, false);

        Map config = new HashMap();
//        config.put("cloud_name", "myCloudName");

        config.put("cloud_name", "dov2s0ww5");


        MediaManager.init(getContext(), config);


        conti = view.findViewById(R.id.btnContinue);
        imgCamera = view.findViewById(R.id.imgCamera);

        etName = view.findViewById(R.id.etName);
        etFatherName = view.findViewById(R.id.etFatherName);
        etPanCardNumber = view.findViewById(R.id.etPanCardNumber);
        etDOB = view.findViewById(R.id.etDOB);
        imgPhoto = view.findViewById(R.id.imgPhoto);


        // signature
        signaturePad = view.findViewById(R.id.signaturePad);
        saveButton = view.findViewById(R.id.saveButton);
        clearButton = view.findViewById(R.id.clearButton);


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
                Toast.makeText(getContext(), "Signature Saved", Toast.LENGTH_SHORT).show();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signaturePad.clear();
            }
        });


        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,2);

            }
        });


        conti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Name = etName.getText().toString();
                String FatherName = etFatherName.getText().toString();
                String PanCardNumber = etPanCardNumber.getText().toString().trim();
                String DOB = etDOB.getText().toString();



                if (!TextUtils.isEmpty(Name)) {
                    if (!TextUtils.isEmpty(FatherName)) {
                        if (!TextUtils.isEmpty(PanCardNumber)) {
                            if (!TextUtils.isEmpty(DOB)) {

                                // Pan Number Validation
                                Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");

                                Matcher matcher = pattern.matcher(PanCardNumber);

                                if (matcher.matches()) {
                                    Toast.makeText(getContext(), PanCardNumber + " is Matching",
                                            Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getContext(), VerificationActivity.class));


                                } else {
                                    Toast.makeText(getContext(), PanCardNumber + " is Not Matching",
                                            Toast.LENGTH_LONG).show();
                                }




                                String requestId = MediaManager.get().upload("dog.mp4")
                                        .unsigned("preset1")
                                        .option("resource_type", "video")
                                        .option("folder", "my_folder/my_sub_folder/")
                                        .option("public_id", "my_dog")
                                        .option("overwrite", true)
                                        .option("notification_url", "https://mysite.example.com/notify_endpoint")
                                        .dispatch();




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


        return view;

    }



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home, menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_message) {
            return true;

        } else if (id == R.id.action_notification) {
            return true;

        }

        return super.onOptionsItemSelected(item);
    }






    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 1);

            } else {
                Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }


    }

}

package com.vendor.vendorpannel.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.net.ParseException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.vendor.vendorpannel.Activities.PanVerificationActivity;
import com.vendor.vendorpannel.Activities.VerificationActivity;
import com.vendor.vendorpannel.Databases.Database;
import com.vendor.vendorpannel.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PANFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PANFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PANFragment() {
        // Required empty public constructor
    }


    public static PANFragment newInstance(String param1, String param2) {
        PANFragment fragment = new PANFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


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

    private FloatingActionButton fab;










    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  =  inflater.inflate(R.layout.fragment_p_a_n, container, false);

        ///////////////////////////////////loading dialogue/////////////////////////////////////////////

        loadingDialogue = new Dialog(getContext());
        loadingDialogue.setContentView(R.layout.loadingprogressdialogue);
        loadingDialogue.setCancelable(false);
        loadingDialogue.getWindow().setBackgroundDrawable(getActivity().getDrawable(R.drawable.slider_background));
        loadingDialogue.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


/////////////////////////////////////loading dialogue/////////////////////////////////////////////


        conti = view.findViewById(R.id.btnContinue);
        etPanCardNumber =  view.findViewById(R.id.etPanCardNumber);
        etName =  view.findViewById(R.id.etName);
        etFatherName =  view.findViewById(R.id.etFatherName);
        etDOB =  view.findViewById(R.id.etDOB);
        imgPhoto =  view.findViewById(R.id.imgPhoto);

        db = new Database(getContext());
        fab = view.findViewById(R.id.floatingActionButton);



        //initializing awesomevalidation object
        /*
         * The library provides 3 types of validation
         * BASIC
         * COLORATION
         * UNDERLABEL
         * */
        final AwesomeValidation awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);


        awesomeValidation.addValidation(getActivity(), R.id.etName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);

        awesomeValidation.addValidation(getActivity(), R.id.etDOB, "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$", R.string.dateerror);


        // signature
        signaturePad =  view.findViewById(R.id.signaturePad);
        saveButton =  view.findViewById(R.id.saveButton);
        clearButton =  view.findViewById(R.id.clearButton);


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


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "camera", Toast.LENGTH_SHORT).show();


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
                                        Toast.makeText(getContext(), "Data Inserted", Toast.LENGTH_SHORT).show();


//                                    Toast.makeText(PANActivity.this, PanCardNumber + " is Matching",
//                                            Toast.LENGTH_LONG).show();


//                                        loadingDialogue.show();

                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {


                                                startActivity(new Intent(getContext(), VerificationActivity.class));
                                                getActivity().finish();
                                            }
                                        }, 5000);

                                    } else {
                                        Toast.makeText(getContext(), "Data Not Inserted", Toast.LENGTH_SHORT).show();


                                    }


                                } else {
                                    loadingDialogue.dismiss();

                                    Toast.makeText(getContext(), PanCardNumber + " is Not Matching",
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



        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
//            Uri ImageUri = data.getData();
//            CropImage.activity()
//                    .setGuidelines(CropImageView.Guidelines.ON)
//                    .setAspectRatio(1, 1)
//                    .start(getActivity());
//        }
//
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//                Uri resultUri = result.getUri();
//                Glide.with(this).load(resultUri).into(imgPhoto);
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Exception error = result.getError();
//
//                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }

        if (requestCode == 1) {
            if (resultCode == getActivity().RESULT_OK) {
                if (data != null) {
                    imageUri = data.getData();


                    Glide.with(getContext()).load(imageUri).into(imgPhoto);
//

                } else {
                    Toast.makeText(getContext(), "Image not found", Toast.LENGTH_SHORT).show();
                }

            }

        }
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
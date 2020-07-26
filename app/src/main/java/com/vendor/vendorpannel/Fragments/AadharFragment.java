package com.vendor.vendorpannel.Fragments;

import android.app.Dialog;
import android.content.Intent;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.vendor.vendorpannel.Activities.AadharActivity;
import com.vendor.vendorpannel.Activities.VerificationActivity;
import com.vendor.vendorpannel.Databases.Database;
import com.vendor.vendorpannel.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AadharFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AadharFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AadharFragment() {
        // Required empty public constructor
    }


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
    private FloatingActionButton fab;


    private Uri imageUri;


    // TODO: Rename and change types and number of parameters
    public static AadharFragment newInstance(String param1, String param2) {
        AadharFragment fragment = new AadharFragment();
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_aadhar, container, false);


        ///////////////////////////////////loading dialogue/////////////////////////////////////////////

        loadingDialogue = new Dialog(getContext());
        loadingDialogue.setContentView(R.layout.loadingprogressdialogue);
        loadingDialogue.setCancelable(false);
        loadingDialogue.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialogue.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


/////////////////////////////////////loading dialogue/////////////////////////////////////////////


        //initializing view objects

        conti = view.findViewById(R.id.btnContinue);
        fab = view.findViewById(R.id.floatingActionButton);
        etName = view.findViewById(R.id.etName);

        etVIDNumber = view.findViewById(R.id.etVidNumber);
        etAadharCardNumber = view.findViewById(R.id.etAadharCardNumber);
        etDOB = view.findViewById(R.id.etDOB);

        imgPhoto = view.findViewById(R.id.imgPhoto);

        //initializing awesomevalidation object
        /*
         * The library provides 3 types of validation
         * BASIC
         * COLORATION
         * UNDERLABEL
         * */
        final AwesomeValidation awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);


        awesomeValidation.addValidation(getActivity(), R.id.etName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);

        awesomeValidation.addValidation(getActivity(), R.id.etDOB, "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$", R.string.nameerror);


        radioSexGroup = (RadioGroup) view.findViewById(R.id.radioGroup);

        btnDisplay = (Button) view.findViewById(R.id.button);
        final int selectedId = radioSexGroup.getCheckedRadioButtonId();
        radioSexButton = (RadioButton) view.findViewById(selectedId);

       if ( radioSexButton == view.findViewById(selectedId)){
           btnDisplay.setEnabled(true);
           btnDisplay.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {



                   genderSelected = radioSexButton.getText().toString();
                   Toast.makeText(getContext(), radioSexButton.getText(), Toast.LENGTH_SHORT).show();

               }
           });
       }else{
           Toast.makeText(getContext(), "Select on please", Toast.LENGTH_SHORT).show();
       }




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

        // instance of database
        db = new Database(getContext());

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
                                if (!TextUtils.isEmpty(btnDisplay.getText().toString())) {
//                                    if (awesomeValidation.validate()) {


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
                                    } else {

                                        // Return if the string
                                        // matched the ReGex
                                        m.matches();
                                        boolean isInserted = db.insertData1(etName.getText().toString(), etVIDNumber.getText().toString(), etAadharCardNumber.getText().toString(), genderSelected);

                                        if (isInserted) {
                                            Toast.makeText(getContext(), "Data Inserted", Toast.LENGTH_SHORT).show();


//                                    Toast.makeText(AadharActivity.this, AadharCardNumber + " is Matching",
//                                            Toast.LENGTH_LONG).show();


                                            loadingDialogue.show();

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


                                    }

                                    // aadhar  Number Validation


//                                    } else {
//                                        Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
//                                    }

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


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            getActivity().finish();
            return true;


        }
        return super.onOptionsItemSelected(item);

    }


}
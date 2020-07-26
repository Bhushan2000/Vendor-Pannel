package com.vendor.vendorpannel.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vendor.vendorpannel.Databases.Myhelper;
import com.vendor.vendorpannel.R;
import com.vendor.vendorpannel.SharedPreferences.SharedPreference_Config;


public class LoginActivity extends AppCompatActivity {
    // database class
    Myhelper mh;

    EditText phoneNumberOrEmail,password;
    Button login;
    TextView register;

    Boolean EditTextEmptyHolder;

    private SharedPreference_Config sharedPreference_config;
    private Dialog loadingDialogue;
    private static int SPLASH_TIME_OUT = 3000;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreference_config = new SharedPreference_Config(getApplicationContext());
//        if(sharedPreference_config.readLoginStatus()){
//            startActivity(new Intent(this,HomeActivity.class));
//            finish();
//        }


        ///////////////////////////////////loading dialogue/////////////////////////////////////////////

        loadingDialogue = new Dialog(this);
        loadingDialogue.setContentView(R.layout.loadingprogressdialogue);
        loadingDialogue.setCancelable(false);
        loadingDialogue.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialogue.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


/////////////////////////////////////loading dialogue/////////////////////////////////////////////


        mh=new Myhelper(this);


        phoneNumberOrEmail = findViewById(R.id.etEmail);
        password=findViewById(R.id.etPassword);
        login=findViewById(R.id.btnLogin);


        register=findViewById(R.id.txtNewUserRegister);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Mobile = phoneNumberOrEmail.getText().toString();

                String Password = password.getText().toString();

                if (TextUtils.isEmpty(Mobile) || TextUtils.isEmpty(Password)) {

                    EditTextEmptyHolder = false;
                    Toast.makeText(getApplicationContext(),"Fields are empty",Toast.LENGTH_LONG).show();

                } else {

                    EditTextEmptyHolder = true;
                    String TempPassword=mh.getSinlgeEntry(Mobile);

                    if(TempPassword.equals(Password)) {
                        loadingDialogue.show();
                        sharedPreference_config.writeLoginStatus(true);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {


                            }
                        }, SPLASH_TIME_OUT);


                        Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();
                        Intent dashboard=new Intent(LoginActivity.this,HomeActivity.class);
                        startActivity(dashboard);
                        finish();


                        /*Intent i = new Intent(MainActivity.this, Profile.class);
                        Bundle b = new Bundle();
                        b.putString("key1", etUser.getText().toString());
                        i.putExtras(b);
                        startActivity(i);*/

// Close The Database
                        mh.close();
                    }
                    else{
                        loadingDialogue.dismiss();

                        Toast.makeText(LoginActivity.this, "Please Enter valid Password", Toast.LENGTH_LONG).show();
                    }
                }


            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register=new Intent(LoginActivity.this,Registration_Page.class);
                startActivity(register);
            }
        });





    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
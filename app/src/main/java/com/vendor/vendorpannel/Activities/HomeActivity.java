package com.vendor.vendorpannel.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.vendor.vendorpannel.Fragments.AadharFragment;
import com.vendor.vendorpannel.Fragments.HomeFragment;
import com.vendor.vendorpannel.Fragments.OrdersFragment;
import com.vendor.vendorpannel.Fragments.PANFragment;
import com.vendor.vendorpannel.Fragments.PaymentSettingFragment;
import com.vendor.vendorpannel.Fragments.ProductPostingFragment;
import com.vendor.vendorpannel.Fragments.ProductsFragment;
import com.vendor.vendorpannel.R;
 import com.vendor.vendorpannel.SharedPreferences.SharedPreference_Config;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    FrameLayout frameLayout;
    private Window window;
    public static Toolbar toolbar;




    private  MenuItem previousMenuItem;

    private int currentFragment = -1;
    private static final int HOME_FRAGMENT = 0;

    private SharedPreference_Config sharedPreference_config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sharedPreference_config = new SharedPreference_Config(getApplicationContext());
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        window = getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Home");

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar
                , R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        frameLayout = findViewById(R.id.fragment_container);





        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);




        if (savedInstanceState == null) {


            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);

        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // For Setting the title on Toolbar when changes of fragments

       // checking menu item checked or not If not make it in checked state
//        if (item.isChecked()) item.setChecked(false);
//        else
//            item.setChecked(true);


        if (previousMenuItem != null){
            item.setChecked(false);
        }
        item.isCheckable();
        item.isChecked();
        previousMenuItem = item;

        //closing drawer on item click
        drawer.closeDrawers();



        int  id = item.getItemId();
        switch (id) {
            case R.id.nav_home:
                toolbar.setTitle(getString(R.string.home));
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                break;

            case R.id.nav_products:
                toolbar.setTitle(getString(R.string.products));
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProductsFragment()).commit();
//                startActivity(new Intent(this,DatabaseActivityForPractise.class));


                break;

            case R.id.nav_orders:
                toolbar.setTitle(getString(R.string.orders));

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new OrdersFragment()).commit();
                break;

            case R.id.nav_payment_setting:
                toolbar.setTitle(getString(R.string.payment_setting));

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new PaymentSettingFragment()).commit();


                break;
            case R.id.nav_pan_verification:
                toolbar.setTitle(getString(R.string.pan_verification));
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new PANFragment()).commit();
//                startActivity(new Intent(this,PanVerificationActivity.class));


                break;
            case R.id.nav_aadhar_verification:
                toolbar.setTitle(getString(R.string.aadhar_verification));

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AadharFragment()).commit();
                //                finish();

                break;
            case R.id.nav_product_posting:

                toolbar.setTitle(getString(R.string.product_posting));

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProductPostingFragment()).commit();
                break;
            case R.id.nav_sign_out:

                sharedPreference_config.writeLoginStatus(false);
                startActivity(new Intent(this,LoginActivity.class));
                finish();

                 break;



        }


        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_message) {
            Toast.makeText(this, "Messages", Toast.LENGTH_SHORT).show();
            return true;

        } else if (id == R.id.action_notification) {
            Toast.makeText(this, "notification", Toast.LENGTH_SHORT).show();

            return true;

        }


        return super.onOptionsItemSelected(item);
    }



}


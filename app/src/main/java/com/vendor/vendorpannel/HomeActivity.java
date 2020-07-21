package com.vendor.vendorpannel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.vendor.vendorpannel.Fragments.AadharVerificationFragment;
import com.vendor.vendorpannel.Fragments.HomeFragment;
import com.vendor.vendorpannel.Fragments.OrdersFragment;
import com.vendor.vendorpannel.Fragments.PanVerificationFragment;
import com.vendor.vendorpannel.Fragments.ProductsFragment;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar
                , R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        frameLayout = findViewById(R.id.fragment_container);


        if (savedInstanceState == null) {


            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);

        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                break;

            case R.id.nav_products:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProductsFragment()).commit();
                break;

            case R.id.nav_orders:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new OrdersFragment()).commit();
                break;

            case R.id.nav_payment_setting:

                Toast.makeText(this, "Cart", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_pan_verification:

                 getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new PanVerificationFragment()).commit();
                break;
            case R.id.nav_product_posting:

                Toast.makeText(this, "account", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_sign_out:


                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AadharVerificationFragment()).commit();

                 break;



        }


        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
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
            return true;

        } else if (id == R.id.action_notification) {
            return true;

        }


        return super.onOptionsItemSelected(item);
    }
}
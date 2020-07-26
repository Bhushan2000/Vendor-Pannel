package com.vendor.vendorpannel.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.os.Handler;

import com.vendor.vendorpannel.Adapter.ViewProductsAdapter;
import com.vendor.vendorpannel.Model.ViewProductsModel;
import com.vendor.vendorpannel.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewProductsActivity extends AppCompatActivity {
private RecyclerView recyclerView;

//    List<> personNames = new ArrayList<>(Arrays.asList("Person 1", "Person 2", "Person 3", "Person 4", "Person 5", "Person 6" ));
//    List<>  personImages = new ArrayList<>(Arrays.asList(R.drawable.t_shirt_1, R.drawable.t_shirt_2, R.drawable.t_shirt_2, R.drawable.t_shirt_1, R.drawable.t_shirt_1, R.drawable.t_shirt_2));
//    List<>  price = new ArrayList<>(Arrays.asList("  1", "  2", "  3", "  4", "  5", "  6" ));
ArrayList<com.vendor.vendorpannel.Model.ViewProductsModel> list = new ArrayList<>();
com.vendor.vendorpannel.Model.ViewProductsModel viewProductsModel;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_products);

        // get the reference of RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // set a StaggeredGridLayoutManager with 3 number of columns and vertical orientation
//        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(staggeredGridLayoutManager); // set LayoutManager to RecyclerView
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        final ViewProductsAdapter customAdapter = new ViewProductsAdapter( list,this );
//        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView


//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager); // set LayoutManager to RecyclerView
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),staggeredGridLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(customAdapter);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadData();
                customAdapter.showShimmer = false;
                customAdapter.notifyDataSetChanged();
            }
        },3000);

    }

    private void loadData() {

        list.add(new ViewProductsModel("T-shirt","Rs 599 /-" ));

        list.add(new ViewProductsModel("T-shirt","Rs 599 /-" ));
        list.add(new ViewProductsModel("T-shirt","Rs 599 /-" ));
        list.add(new ViewProductsModel("T-shirt","Rs 599 /-" ));
        list.add(new ViewProductsModel("T-shirt","Rs 599 /-" ));
        list.add(new ViewProductsModel("T-shirt","Rs 599 /-" ));

    }
}
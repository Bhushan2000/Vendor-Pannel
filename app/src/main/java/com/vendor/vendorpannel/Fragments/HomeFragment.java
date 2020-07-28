package com.vendor.vendorpannel.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vendor.vendorpannel.Adapter.HomeBottomAdapter;
import com.vendor.vendorpannel.Adapter.HomeTopAdapter;
import com.vendor.vendorpannel.Model.HomeBottomModel;
import com.vendor.vendorpannel.Model.HomeTopModel;
import com.vendor.vendorpannel.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView_Top_part;
    private ArrayList<HomeTopModel> homeTopArrayList = new ArrayList<>();
    private RecyclerView recyclerView_bottom_Part;
    private ArrayList<HomeBottomModel> homeBottomArrayList = new ArrayList<>();

    private HomeBottomAdapter homeBottomAdapter;
    private HomeTopAdapter homeTopAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView_bottom_Part = view.findViewById(R.id.recyclerView_bottom_Part);
        recyclerView_Top_part = view.findViewById(R.id.recyclerView_Top_part);

         LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView_Top_part.setLayoutManager(layoutManager);

         LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
        layoutManager1.setOrientation(RecyclerView.VERTICAL);
        recyclerView_bottom_Part.setLayoutManager(layoutManager1);

        homeTopAdapter = new HomeTopAdapter(homeTopArrayList);
        homeBottomAdapter = new HomeBottomAdapter(homeBottomArrayList);

        recyclerView_bottom_Part.setAdapter(homeBottomAdapter);

        recyclerView_Top_part.setAdapter(homeTopAdapter);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView_bottom_Part.getContext(),layoutManager.getOrientation());

        recyclerView_Top_part.addItemDecoration(dividerItemDecoration);

        DividerItemDecoration dividerItemDecoration1 = new DividerItemDecoration(recyclerView_Top_part.getContext(),layoutManager1.getOrientation());


        recyclerView_bottom_Part.addItemDecoration(dividerItemDecoration1);

        loadData();


        return view;

    }

    public void loadData() {
        homeBottomArrayList.add(new HomeBottomModel("Products",R.drawable.products));
        homeBottomArrayList.add(new HomeBottomModel("Orders",R.drawable.orders));
        homeBottomArrayList.add(new HomeBottomModel("Payment Setting",R.drawable.pament_setting));
        homeBottomArrayList.add(new HomeBottomModel("PAN Verification",R.drawable.pan_verification));
        homeBottomArrayList.add(new HomeBottomModel("Aadhar Verification",R.drawable.aadhar));
        homeBottomArrayList.add(new HomeBottomModel("Product Posting",R.drawable.product_posting));


        homeTopArrayList.add(new HomeTopModel("Sales Today ","400 INR"));
        homeTopArrayList.add(new HomeTopModel("Unit today so far","14"));
        homeTopArrayList.add(new HomeTopModel("Current COD balance","3781 INR"));
        homeTopArrayList.add(new HomeTopModel("Next COD Payment","15 Oct, 2017"));
        homeTopArrayList.add(new HomeTopModel("Current ele. balance","4030 INR"));
        homeTopArrayList.add(new HomeTopModel("Next ele. Payment","16 Oct,2017"));
        homeTopArrayList.add(new HomeTopModel("Customer Feedback","4.5"));

    }


}

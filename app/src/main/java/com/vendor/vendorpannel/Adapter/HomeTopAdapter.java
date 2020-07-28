package com.vendor.vendorpannel.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vendor.vendorpannel.Model.HomeTopModel;
import com.vendor.vendorpannel.R;

import java.util.ArrayList;
import java.util.List;

public class HomeTopAdapter extends RecyclerView.Adapter<HomeTopAdapter.HomeTopViewHolder> {
private ArrayList<HomeTopModel> homeTopModelList;

    public HomeTopAdapter(ArrayList<HomeTopModel> homeTopModelList) {
        this.homeTopModelList = homeTopModelList;
    }

    @NonNull
    @Override
    public HomeTopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_layout_2,parent,false);

        return new HomeTopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeTopViewHolder holder, int position) {
        holder.title.setText(homeTopModelList.get(position).getTopTitle());
        holder.value.setText(homeTopModelList.get(position).getTopValue());
    }

    @Override
    public int getItemCount() {
        return homeTopModelList.size();
    }

    public class HomeTopViewHolder extends RecyclerView.ViewHolder{
        TextView title,value;


        public HomeTopViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            value = itemView.findViewById(R.id.value);
        }
    }

}

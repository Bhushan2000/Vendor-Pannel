package com.vendor.vendorpannel.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.vendor.vendorpannel.Model.ViewProductsModel;
import com.vendor.vendorpannel.R;

import java.util.ArrayList;
import java.util.List;

public class ViewProductsAdapter extends RecyclerView.Adapter<ViewProductsAdapter.MyViewHolder> {

    ArrayList<com.vendor.vendorpannel.Model.ViewProductsModel> list;
    public boolean showShimmer = true;
    Context context;

    public ViewProductsAdapter(ArrayList<ViewProductsModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_products_layout, parent, false);
        return new MyViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {


        holder.productTitle.setText(null);

//        holder.productImage.setImageResource();
        holder.price.setText(null);

        if(showShimmer){
            holder.shimmerFrameLayout.startShimmer();
        }else{
            holder.shimmerFrameLayout.stopShimmer();
            holder.shimmerFrameLayout.setShimmer(null);

            holder.productTitle.setBackground(null);
            holder.productTitle.setText(list.get(position).getProductTitle());

            holder.price.setBackground(null);
            holder.price.setText(list.get(position).getPrice());

            holder.productImage.setImageDrawable(context.getDrawable(R.drawable.t_shirt_1));
        }



    }

    @Override
    public int getItemCount() {
        // shimmer show item
        int SHIMMER_ITEM_NO = 10;

        return showShimmer ? SHIMMER_ITEM_NO:list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView productTitle, price;
        private ImageView productImage;
        ShimmerFrameLayout shimmerFrameLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            shimmerFrameLayout = itemView.findViewById(R.id.shimmerLayout);

            productTitle = (TextView) itemView.findViewById(R.id.txtProductHeading);
            price = itemView.findViewById(R.id.txtSubheading);
            productImage = itemView.findViewById(R.id.imgProduct);


        }
    }




}

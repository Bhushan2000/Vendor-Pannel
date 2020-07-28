package com.vendor.vendorpannel.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vendor.vendorpannel.Activities.NewActivity;
import com.vendor.vendorpannel.Fragments.ProductsFragment;
import com.vendor.vendorpannel.Model.HomeBottomModel;
import com.vendor.vendorpannel.R;

import java.util.ArrayList;
import java.util.List;

public class HomeBottomAdapter extends RecyclerView.Adapter<HomeBottomAdapter.HomeBottomViewHolder> {
private ArrayList<HomeBottomModel> homeBottomModelList;

    public HomeBottomAdapter(ArrayList<HomeBottomModel> homeBottomModelList) {
        this.homeBottomModelList = homeBottomModelList;
    }

    @NonNull
    @Override
    public HomeBottomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_layout_1,parent,false);

        return new HomeBottomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeBottomViewHolder holder, int position) {
        String head =  homeBottomModelList.get(position).getTitle();
        String id =  homeBottomModelList.get(position).getId();
        int iamge =  homeBottomModelList.get(position).getImage();

        holder.setData(id,head,iamge);

    }

    @Override
    public int getItemCount() {
        return homeBottomModelList.size();
    }

    public class HomeBottomViewHolder extends RecyclerView.ViewHolder{
       private TextView title;
       private ImageView bottomImage;



        public HomeBottomViewHolder(@NonNull final View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            bottomImage = itemView.findViewById(R.id.bottomImage);



        }

        private void setData(final String Id, final String heading,int image){

                title.setText(heading);
                bottomImage.setImageResource(image);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), NewActivity.class);

                    intent.putExtra("ID_products",Id);
                    intent.putExtra("toolbar_heading",heading);


                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}

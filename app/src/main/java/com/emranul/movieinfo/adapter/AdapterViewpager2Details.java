package com.emranul.movieinfo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.emranul.movieinfo.R;
import com.emranul.movieinfo.model.Images;

import java.util.List;

public class AdapterViewpager2Details extends RecyclerView.Adapter<AdapterViewpager2Details.ViewPager2DetailsViewHolder> {

    private List<Images> imagesList;

    public AdapterViewpager2Details(List<Images> imagesList) {
        this.imagesList = imagesList;
    }

    @NonNull
    @Override
    public ViewPager2DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_details_viewpager2_ss, parent, false);
        return new ViewPager2DetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPager2DetailsViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext())
                .load(imagesList.get(position).getFile_path())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    class ViewPager2DetailsViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public ViewPager2DetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.raw_details_image_ss);
        }
    }
}

package com.emranul.movieinfo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.emranul.movieinfo.R;
import com.emranul.movieinfo.model.Results;

import java.util.List;

public class AdapterPopular extends RecyclerView.Adapter<AdapterPopular.PopularVH> {

    private List<Results> resultsList;

    public AdapterPopular(List<Results> resultsList) {
        this.resultsList = resultsList;
    }

    @NonNull
    @Override
    public PopularVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PopularVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_popular_main,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PopularVH holder, int position) {
        holder.popularTitle.setText(resultsList.get(position).getTitle());
        holder.popularRatingText.setText(resultsList.get(position).getVote_average()+"");
        float x = (float) (resultsList.get(position).getVote_average()/2);
        holder.popularRating.setRating(x);
        Glide.with(holder.itemView.getContext())
                .load(resultsList.get(position).getPoster_path())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.popularImage);

    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }

    class PopularVH extends RecyclerView.ViewHolder {

        private ImageView popularImage;
        private TextView popularTitle,popularRatingText;
        private RatingBar popularRating;

        public PopularVH(@NonNull View itemView) {
            super(itemView);

            popularImage = itemView.findViewById(R.id.raw_popular_main_image);
            popularTitle = itemView.findViewById(R.id.raw_popular_main_title);
            popularRatingText = itemView.findViewById(R.id.raw_popular_main_rating_text);
            popularRating = itemView.findViewById(R.id.raw_popular_main_rating);
        }
    }
}

package com.emranul.movieinfo.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.emranul.movieinfo.DetailsActivity;
import com.emranul.movieinfo.R;
import com.emranul.movieinfo.model.Results;

import java.util.List;

public class AdapterPopular extends RecyclerView.Adapter<AdapterPopular.PopularViewHolder> {

    private final List<Results> resultsList;

    public AdapterPopular(List<Results> resultsList) {
        this.resultsList = resultsList;
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PopularViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_popular_main, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {
        holder.popularTitle.setText(resultsList.get(position).getTitle());
        holder.popularRatingText.setText(resultsList.get(position).getVote_average() + "");
        float x = (float) (resultsList.get(position).getVote_average() / 2);
        holder.popularRating.setRating(x);
        Glide.with(holder.itemView.getContext())
                .load(resultsList.get(position).getPoster_path())
                .into(holder.popularImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailsActivity.class);
                intent.putExtra("id", resultsList.get(position).getId());

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                        (Activity) holder.itemView.getContext(),
                        Pair.create(holder.popularImage, "ShareTransition")
                );

                holder.itemView.getContext().startActivity(intent, options.toBundle());
            }
        });

    }

    @Override
    public int getItemCount() {
        if (resultsList.size() > 10) {
            return 10;
        }
        return resultsList.size();
    }

    class PopularViewHolder extends RecyclerView.ViewHolder {

        private final ImageView popularImage;
        private final TextView popularTitle;
        private final TextView popularRatingText;
        private final RatingBar popularRating;

        public PopularViewHolder(@NonNull View itemView) {
            super(itemView);

            popularImage = itemView.findViewById(R.id.raw_popular_main_image);
            popularTitle = itemView.findViewById(R.id.raw_popular_main_title);
            popularRatingText = itemView.findViewById(R.id.raw_popular_main_rating_text);
            popularRating = itemView.findViewById(R.id.raw_popular_main_rating);
        }
    }
}

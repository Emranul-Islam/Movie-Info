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

public class AdapterRank extends RecyclerView.Adapter<AdapterRank.RankViewHolder> {
    private final List<Results> rankList;

    public AdapterRank(List<Results> rankList) {
        this.rankList = rankList;
    }

    @NonNull
    @Override
    public RankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RankViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_rank, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RankViewHolder holder, int position) {
        holder.rankTitle.setText(rankList.get(position).getTitle());
        holder.rankVote.setText(rankList.get(position).getVote_average() + "");
        holder.rankOverview.setText(rankList.get(position).getOverview());
        float x = (float) (rankList.get(position).getVote_average() / 2);
        holder.rankRating.setRating(x);
        Glide.with(holder.itemView.getContext())
                .load(rankList.get(position).getPoster_path())
                .into(holder.rankImage);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailsActivity.class);
                intent.putExtra("id", rankList.get(position).getId());


                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                        (Activity) holder.itemView.getContext(),
                        Pair.create(holder.rankImage, "ShareTransition")
                );

                holder.itemView.getContext().startActivity(intent, options.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return rankList.size();
    }

    class RankViewHolder extends RecyclerView.ViewHolder {
        private final ImageView rankImage;
        private final TextView rankTitle;
        private final TextView rankVote;
        private final TextView rankOverview;
        private final RatingBar rankRating;

        public RankViewHolder(@NonNull View itemView) {
            super(itemView);

            rankImage = itemView.findViewById(R.id.rank_image);
            rankTitle = itemView.findViewById(R.id.rank_title);
            rankVote = itemView.findViewById(R.id.rank_vote);
            rankRating = itemView.findViewById(R.id.rank_rating);
            rankOverview = itemView.findViewById(R.id.rank_overview);
        }
    }
}

package com.emranul.movieinfo.adapter;

import android.content.Intent;
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
    private List<Results> rankList;

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
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.rankImage);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailsActivity.class);
                intent.putExtra("id", rankList.get(position).getId());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rankList.size();
    }

    class RankViewHolder extends RecyclerView.ViewHolder {
        private ImageView rankImage;
        private TextView rankTitle, rankVote, rankOverview;
        private RatingBar rankRating;

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

package com.emranul.movieinfo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.emranul.movieinfo.R;
import com.emranul.movieinfo.model.Results;

import java.util.List;

public class AdapterSlider extends RecyclerView.Adapter<AdapterSlider.SliderVH> {
    private List<Results> slideList;
    private ViewPager2 viewPager2;

    public AdapterSlider(List<Results> slideList, ViewPager2 viewPager2) {
        this.slideList = slideList;
        this.viewPager2 = viewPager2;
    }


    @NonNull
    @Override
    public SliderVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_slider_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderVH holder, int position) {
        holder.title.setText(slideList.get(position).getTitle());
        holder.overview.setText(slideList.get(position).getOverview());
        holder.vote.setText(slideList.get(position).getVote_average() + "");
        float rating = (float) slideList.get(position).getVote_average() / 2;
        holder.ratingBar.setRating(rating);

        Glide.with(holder.itemView.getContext())
                .load(slideList.get(position).getPoster_path())
                .placeholder(R.color.colorBackground)
                .into(holder.image);

        Glide.with(holder.itemView.getContext())
                .load(slideList.get(position).getBackdrop_path())
                .placeholder(R.color.colorBackground)
                .into(holder.cover);


        if (position == slideList.size() - 2) {
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        if (slideList.size() > 6) {
            return 6;
        }
        return slideList.size();
    }


    class SliderVH extends RecyclerView.ViewHolder {
        private TextView title, vote, overview;
        private ImageView cover, image;
        private RatingBar ratingBar;

        public SliderVH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.slider_title);
            vote = itemView.findViewById(R.id.slider_vote);
            overview = itemView.findViewById(R.id.slider_overview);
            cover = itemView.findViewById(R.id.slider_cover);
            image = itemView.findViewById(R.id.slider_image);
            ratingBar = itemView.findViewById(R.id.slider_rating);
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            slideList.addAll(slideList);
            notifyDataSetChanged();
        }
    };
}

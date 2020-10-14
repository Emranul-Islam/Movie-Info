package com.emranul.movieinfo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.emranul.movieinfo.R;
import com.emranul.movieinfo.model.Cast;

import java.util.List;

public class AdapterCredits extends RecyclerView.Adapter<AdapterCredits.CreditsViewHolder> {

    private final List<Cast> creditsList;

    public AdapterCredits(List<Cast> creditsList) {
        this.creditsList = creditsList;
    }

    @NonNull
    @Override
    public CreditsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CreditsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_credits, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CreditsViewHolder holder, int position) {

        holder.name.setText(creditsList.get(position).getCharacter());
        holder.originalName.setText(creditsList.get(position).getName());
        Glide.with(holder.itemView.getContext())
                .load(creditsList.get(position).getProfile_path())
                .placeholder(R.drawable.ic_profile)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return creditsList.size();
    }

    class CreditsViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image;
        private final TextView name;
        private final TextView originalName;

        public CreditsViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.credits_image);
            name = itemView.findViewById(R.id.credits_name);
            originalName = itemView.findViewById(R.id.credits_original_name);
        }
    }
}

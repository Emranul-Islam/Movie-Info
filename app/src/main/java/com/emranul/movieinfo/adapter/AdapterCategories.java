package com.emranul.movieinfo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emranul.movieinfo.R;
import com.emranul.movieinfo.model.Genres;

import java.util.List;

public class AdapterCategories extends RecyclerView.Adapter<AdapterCategories.CategoriesVH> {
    private List<Genres> categoriesList;

    public AdapterCategories(List<Genres> categoriesList) {
        this.categoriesList = categoriesList;
    }

    @NonNull
    @Override
    public CategoriesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoriesVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_categories,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesVH holder, int position) {
        holder.categoriesText.setText(categoriesList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    class CategoriesVH extends RecyclerView.ViewHolder {
        private TextView categoriesText;
        public CategoriesVH(@NonNull View itemView) {
            super(itemView);
            categoriesText = itemView.findViewById(R.id.raw_categories_text);
        }
    }
}

package com.emranul.movieinfo.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.emranul.movieinfo.R;
import com.emranul.movieinfo.model.MainModel;
import com.emranul.movieinfo.model.Results;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.List;

import static com.emranul.movieinfo.Constant.POPULAR_TYPE;
import static com.emranul.movieinfo.Constant.SLIDER_TYPE;

public class AdapterMain extends RecyclerView.Adapter {

    private Context context;
    private List<MainModel> list;

    public AdapterMain(Context context, List<MainModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        switch (list.get(position).getType()) {
            case SLIDER_TYPE:
                return SLIDER_TYPE;
            case POPULAR_TYPE:
                return POPULAR_TYPE;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case SLIDER_TYPE:
                View sliderView = LayoutInflater.from(context).inflate(R.layout.layout_slider, parent, false);
                return new SliderVH(sliderView);
            case POPULAR_TYPE:
                View popularView = LayoutInflater.from(context).inflate(R.layout.layout_popular, parent, false);
                return new PopularVH(popularView);

            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (list.get(position).getType()) {
            case SLIDER_TYPE:
                List<Results> sliderList = list.get(position).getSlider();
                ((SliderVH) holder).setUpSlider(sliderList);
                break;
            case POPULAR_TYPE:
                List<Results> popularList = list.get(position).getPopular();
                ((PopularVH) holder).setUpPopular(popularList, list.get(position).getPopularTitle());
                break;
            default:
                return;
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    //slider added on main recycler start--------------->
    private class SliderVH extends RecyclerView.ViewHolder {

        private ViewPager2 viewPager2;
        private Handler sliderHandler = new Handler();
        private boolean reStart = true;
        private boolean reverse = false;

        public SliderVH(@NonNull View itemView) {
            super(itemView);
            viewPager2 = itemView.findViewById(R.id.viewpager2);

        }

        private void setUpSlider(List<Results> sliderList) {
            AdapterSlider adapterSlider = new AdapterSlider(sliderList, viewPager2);
            viewPager2.setAdapter(adapterSlider);
            DotsIndicator dotsIndicator = (DotsIndicator) itemView.findViewById(R.id.indicator);
            dotsIndicator.setViewPager2(viewPager2);

            viewPager2.setClipToPadding(false);
            viewPager2.setClipChildren(false);
            viewPager2.setOffscreenPageLimit(3);
            viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

            CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
            compositePageTransformer.addTransformer(new MarginPageTransformer(40));
            compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
                @Override
                public void transformPage(@NonNull View page, float position) {
                    float x = 1 - Math.abs(position);
                    page.setScaleY(0.8f + x * 0.15f);
                }
            });

            viewPager2.setPageTransformer(compositePageTransformer);

            viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    sliderHandler.removeCallbacks(sliderRunnable);
                    sliderHandler.postDelayed(sliderRunnable, 3000);
                }
            });

        }

        private Runnable sliderRunnable = new Runnable() {
            @Override
            public void run() {
                if (reStart) {
                    if (viewPager2.getCurrentItem() == 5) {
                        reverse = true;
                        reStart = false;
                    } else {
                        viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                    }
                }

                if (reverse) {
                    if (viewPager2.getCurrentItem() == 0) {
                        reverse = false;
                        reStart = true;
                        viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                    } else {
                        viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                    }
                }
            }
        };
    }
    //slider added on main recycler end;


    //popular added on main recycler start----------->
    private class PopularVH extends RecyclerView.ViewHolder {
        private RecyclerView popularRecycler;
        private TextView popularTv;

        public PopularVH(@NonNull View itemView) {
            super(itemView);
            popularRecycler = itemView.findViewById(R.id.popular_recycler);
            popularTv = itemView.findViewById(R.id.popular_text);
        }

        private void setUpPopular(List<Results> popularList, String title) {
            popularTv.setText(title);
            popularRecycler.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
            AdapterPopular adapterPopular = new AdapterPopular(popularList);
            popularRecycler.setAdapter(adapterPopular);
            adapterPopular.notifyDataSetChanged();
            Toast.makeText(context, title, Toast.LENGTH_SHORT).show();

        }
    }
    //popular added on main recycler end;


}

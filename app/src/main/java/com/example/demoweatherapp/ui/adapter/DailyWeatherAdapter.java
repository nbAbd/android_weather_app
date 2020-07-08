package com.example.demoweatherapp.ui.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoweatherapp.data.remote.api.model.Daily;
import com.example.demoweatherapp.databinding.WeatherDayItemBinding;
import com.example.demoweatherapp.utils.AppUtil;

import java.util.ArrayList;
import java.util.List;

public class DailyWeatherAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<Daily> items = new ArrayList<>();
    private OnDailyClickListener mListener;

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        WeatherDayItemBinding binding = WeatherDayItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new WeatherViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public void setListener(OnDailyClickListener listener) {
        this.mListener = listener;
    }

    public void setItems(List<Daily> dailyList) {
        // clean
        this.items.clear();
        if (dailyList != null) {
            items.addAll(dailyList);
        }
        this.items = dailyList;
        notifyDataSetChanged();
    }

    public class WeatherViewHolder extends BaseViewHolder {
        WeatherDayItemBinding mBinding;

        WeatherViewHolder(@NonNull WeatherDayItemBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }


        @Override
        public void onBind(int position) {
            int cardBackgroundColor = AppUtil.getRandomMaterialColor(mBinding.getRoot().getResources());
            mBinding.cardView.setCardBackgroundColor(cardBackgroundColor);

            int[] colors = {
                    Color.TRANSPARENT,
                    ColorUtils.setAlphaComponent(cardBackgroundColor, 100)
                    ,
                    Color.TRANSPARENT
            };

            Daily item = items.get(position);

            GradientDrawable shape = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colors);
            shape.setShape(GradientDrawable.RECTANGLE);
            mBinding.shadowView.setBackground(shape);

            mBinding.setDay(item);
            mBinding.executePendingBindings();


            mBinding.getRoot().setOnClickListener(v -> {
                if (mListener != null) {
                    mListener.onItemClick(item);
                }
            });

        }
    }

    public interface OnDailyClickListener {
        void onItemClick(Daily daily);
    }
}

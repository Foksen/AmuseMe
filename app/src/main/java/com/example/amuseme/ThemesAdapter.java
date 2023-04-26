package com.example.amuseme;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amuseme.databinding.ThemeItemBinding;

import java.util.ArrayList;

public class ThemesAdapter extends RecyclerView.Adapter<ThemesAdapter.ThemeViewHolder> {
    private final ArrayList<Theme> data;

    public ThemesAdapter(ArrayList<Theme> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ThemeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ThemeItemBinding binding = ThemeItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new ThemeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ThemeViewHolder holder, int position) {
        Theme item = data.get(position);
        holder.binding.themeTitle.setText(item.themeTitle);
        holder.binding.themeDesc.setText(item.themeDesc);

        // TODO: Try to run both themeImg.animate() and themeImgBW.animate()

        try {
            holder.binding.themeImg.setImageResource(item.themeImgID);
        } catch (Exception e) {
            Log.e("AMUSE_ME", e.getMessage());
            holder.binding.themeImg.setImageResource(Theme.defaultImgID);
        }

        try {
            holder.binding.themeImgBW.setImageResource(item.themeImgBWID);
        } catch (Exception e) {
            Log.e("AMUSE_ME", e.getMessage());
            holder.binding.themeImgBW.setImageResource(Theme.defaultImgBWID);
        }

        holder.binding.themeCheckbox.setChecked(false);
        holder.binding.themeImg.setAlpha(0.f);

        holder.binding.themeLayout.setOnClickListener((View v) -> {
            if (holder.binding.themeCheckbox.isChecked()) {
                holder.binding.themeImg.animate().alpha(0.f).setDuration(200)
                        .setInterpolator(new AccelerateInterpolator());
                holder.binding.themeCheckbox.setChecked(false);
            }
            else {
                holder.binding.themeImg.animate().alpha(1.f).setDuration(200)
                        .setInterpolator(new AccelerateInterpolator());
                holder.binding.themeCheckbox.setChecked(true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ThemeViewHolder extends RecyclerView.ViewHolder {
        ThemeItemBinding binding;
        public ThemeViewHolder(ThemeItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

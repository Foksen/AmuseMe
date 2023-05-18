package com.example.amuseme;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
    private final ArrayList<ThemeItemRecycler> data;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;

    public ThemesAdapter(ArrayList<ThemeItemRecycler> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ThemeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ThemeItemBinding binding = ThemeItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(parent.getContext());
        sharedPreferencesEditor = sharedPreferences.edit();
        return new ThemeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ThemeViewHolder holder, int position) {
        ThemeItemRecycler item = data.get(position);
        holder.binding.themeTitle.setText(item.themeTitle);
        holder.binding.themeDesc.setText(item.themeDesc);
        try {
            holder.binding.themeImg.setImageResource(item.themeImgID);
        } catch (Exception e) {
            Log.e("AMUSE_ME", e.getMessage());
            holder.binding.themeImg.setImageResource(ThemeItemRecycler.defaultImgID);
        }
        try {
            holder.binding.themeImgBW.setImageResource(item.themeImgBWID);
        } catch (Exception e) {
            Log.e("AMUSE_ME", e.getMessage());
            holder.binding.themeImgBW.setImageResource(ThemeItemRecycler.defaultImgBWID);
        }

        if (!sharedPreferences.contains(Integer.toString(item.themeId))) {
            sharedPreferencesEditor.putBoolean(Integer.toString(item.themeId), true);
            sharedPreferencesEditor.apply();
        }
        if (sharedPreferences.getBoolean(Integer.toString(item.themeId), true)) {
            holder.binding.themeImg.setAlpha(1.f);
            holder.binding.themeCheckbox.setChecked(true);
        }
        else {
            holder.binding.themeImg.setAlpha(0.f);
            holder.binding.themeCheckbox.setChecked(false);
        }

        holder.binding.themeLayout.setOnClickListener((View v) -> {
            if (holder.binding.themeCheckbox.isChecked()) {
                holder.binding.themeImg.animate().alpha(0.f).setDuration(200)
                        .setInterpolator(new AccelerateInterpolator());
                holder.binding.themeCheckbox.setChecked(false);
                sharedPreferencesEditor.putBoolean(Integer.toString(item.themeId), false);
                sharedPreferencesEditor.apply();
            }
            else {
                holder.binding.themeImg.animate().alpha(1.f).setDuration(200)
                        .setInterpolator(new AccelerateInterpolator());
                holder.binding.themeCheckbox.setChecked(true);
                sharedPreferencesEditor.putBoolean(Integer.toString(item.themeId), true);
                sharedPreferencesEditor.apply();
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

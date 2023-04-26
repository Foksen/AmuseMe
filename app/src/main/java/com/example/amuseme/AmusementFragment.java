package com.example.amuseme;

import static com.example.amuseme.R.color.tempDarkWhite;
import static com.example.amuseme.R.color.tempError;
import static com.example.amuseme.R.color.tempSuccess;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.amuseme.databinding.FragmentAmusementBinding;

import java.util.Objects;

public class AmusementFragment extends Fragment {
    FragmentAmusementBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAmusementBinding.inflate(inflater, container, false);

        binding.amusementDislikesCheckbox.setChecked(false);
        binding.amusementLikesCheckbox.setChecked(false);

        // TODO: Set image, title, desc, view, likes, dislikes

        binding.amusementDislikesIcon.setOnClickListener((View v) -> {
            if (binding.amusementLikesCheckbox.isChecked()) {
                binding.amusementLikesCheckbox.setChecked(false);
            }
            binding.amusementDislikesCheckbox.setChecked(!binding.amusementDislikesCheckbox.isChecked());
        });

        binding.amusementLikesIcon.setOnClickListener((View v) -> {
            if (binding.amusementDislikesCheckbox.isChecked()) {
                binding.amusementDislikesCheckbox.setChecked(false);
            }
            binding.amusementLikesCheckbox.setChecked(!binding.amusementLikesCheckbox.isChecked());
        });

        binding.amusementDislikesCheckbox.setOnCheckedChangeListener((view, isChecked) -> {
            if (isChecked) {
                binding.amusementDislikesIcon.setImageTintList(ColorStateList.valueOf(
                                ContextCompat.getColor(requireContext(), tempError)));
                // TODO: SQL query to change amusement stats, update value
            }
            else {
                binding.amusementDislikesIcon.setImageTintList(ColorStateList.valueOf(
                                ContextCompat.getColor(requireContext(), tempDarkWhite)));
                // TODO: SQL query to change amusement stats, update value
            }
        });

        binding.amusementLikesCheckbox.setOnCheckedChangeListener((view, isChecked) -> {
            if (isChecked) {
                binding.amusementLikesIcon.setImageTintList(ColorStateList.valueOf(
                        ContextCompat.getColor(requireContext(), tempSuccess)));
                // TODO: SQL query to change amusement stats, update value
            }
            else {
                binding.amusementLikesIcon.setImageTintList(ColorStateList.valueOf(
                        ContextCompat.getColor(requireContext(), tempDarkWhite)));
                // TODO: SQL query to change amusement stats, update value
            }
        });

        return binding.getRoot();
    }
}

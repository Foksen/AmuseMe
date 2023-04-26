package com.example.amuseme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.amuseme.databinding.FragmentAmusementBinding;
import com.example.amuseme.databinding.FragmentGeneratorBinding;

public class GeneratorFragment extends Fragment {
    FragmentGeneratorBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentGeneratorBinding.inflate(inflater, container, false);

        binding.controlBarMenu.setOnClickListener((View v) -> {
            // TODO: make animation during navigation
            moveToThemesFragment();
        });

        binding.generatorLoaderInactive.setOnClickListener((View v) -> {
            // TODO: make animation during navigation
            // TODO: animation with loader active
            moveToAmusementFragment();
        });

        return binding.getRoot();
    }

    private void moveToThemesFragment() {
        Navigation
                .findNavController(binding.getRoot())
                .navigate(R.id.action_generatorFragment_to_themesFragment);
    }

    private void moveToAmusementFragment() {        // TODO: Add bundle with amusement params
        Navigation
                .findNavController(binding.getRoot())
                .navigate(R.id.action_generatorFragment_to_amusementFragment);
    }
}

// TODO: Ask about destroying view in navigation
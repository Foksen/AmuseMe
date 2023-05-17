package com.example.amuseme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.amuseme.databinding.FragmentThemesBinding;

import java.util.ArrayList;

public class ThemesFragment extends Fragment {
    FragmentThemesBinding binding;
    ArrayList<ThemeItemRecycler> themes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentThemesBinding.inflate(inflater, container, false);
        initThemes();

        ThemesAdapter adapter = new ThemesAdapter(themes);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(adapter);

        binding.controlBarBack.setOnClickListener((View v) -> {
            // TODO: make animation during navigation
            Navigation
                    .findNavController(binding.getRoot())
                    .popBackStack();
        });

        return binding.getRoot();
    }

    private void initThemes() {
        themes = new ArrayList<>();

        themes.add(new ThemeItemRecycler(
                "Фильмы и сериалы",
                "В интернете так много фильмов и сериалов, но какой же выбрать? Мы подберём вам лучшее кино!",
                R.drawable.theme_img_1, R.drawable.theme_img_1_bw));
        themes.add(new ThemeItemRecycler(
                "Физическая активность",
                "Возможно, стоит немного размять тело? Физическая нагрузка полезна для человека!",
                R.drawable.theme_img_2, R.drawable.theme_img_2_bw));
    }
}

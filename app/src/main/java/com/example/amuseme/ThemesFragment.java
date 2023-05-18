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
import java.util.Arrays;

public class ThemesFragment extends Fragment {
    FragmentThemesBinding binding;
    public static final ArrayList<ThemeItemRecycler> themes = new ArrayList<>(Arrays.asList(
            new ThemeItemRecycler(1, "Фильмы и сериалы",
                    "В интернете так много фильмов и сериалов, но какой же выбрать? Мы подберём вам лучшее кино!",
                    R.drawable.theme_img_1, R.drawable.theme_img_1_bw),
            new ThemeItemRecycler(2, "Физическая активность",
                    "Возможно, стоит немного размять тело? Физическая нагрузка полезна для человека!",
                    R.drawable.theme_img_2, R.drawable.theme_img_2_bw)
    ));

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentThemesBinding.inflate(inflater, container, false);

        ThemesAdapter adapter = new ThemesAdapter(themes);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(adapter);

        binding.controlBarBack.setOnClickListener((View v) -> {
            Navigation.findNavController(binding.getRoot()).popBackStack();
        });

        return binding.getRoot();
    }
}

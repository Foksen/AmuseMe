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
                    "Возможно, стоит немного размять своё тело? Физическая нагрузка полезная для здоровья человека!",
                    R.drawable.theme_img_2, R.drawable.theme_img_2_bw),
            new ThemeItemRecycler(3, "Саморазвитие",
                    "Надоело сидеть без дела и хотите заняться чем-нибудь полезным? У нас есть несколько идей для вас!",
                    R.drawable.theme_img_3, R.drawable.theme_img_3_bw),
            new ThemeItemRecycler(4, "Развлечения для компании",
                    "Планируете отдохнуть со своими друзьями? Генератор предложит вам идею для совместного досуга!",
                    R.drawable.theme_img_4, R.drawable.theme_img_4_bw),
            new ThemeItemRecycler(5, "Компьютерные игры",
                    "Все игры пройдены и хочется чего-то нового? Мы расскажем вам о самых интересных тайтлах!",
                    R.drawable.theme_img_5, R.drawable.theme_img_5_bw),
            new ThemeItemRecycler(6, "Развлекающие видео",
                    "Как насчёт посмотреть видео на YouTube? Мы нашли много авторов, которые делают отличный контент!",
                    R.drawable.theme_img_6, R.drawable.theme_img_6_bw),
            new ThemeItemRecycler(7, "Музыка",
                    "Хотите послушать самые новые треки от известных исполнителей? У нас для вас есть целая подборка!",
                    R.drawable.theme_img_7, R.drawable.theme_img_7_bw)
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}

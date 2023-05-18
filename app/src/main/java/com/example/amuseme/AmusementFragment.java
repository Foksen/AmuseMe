package com.example.amuseme;

import static com.example.amuseme.R.color.gray_100;
import static com.example.amuseme.R.color.red_rose;
import static com.example.amuseme.R.color.green_spring;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.amuseme.databinding.FragmentAmusementBinding;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AmusementFragment extends Fragment {
    FragmentAmusementBinding binding;
    AmusementItemResponse amusementItem;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAmusementBinding.inflate(inflater, container, false);
        amusementItem = new AmusementItemResponse();

        initAmusementItem();
        initViews();

        binding.amusementDislikesIcon.setOnClickListener((View v) -> {
            if (binding.amusementLikesCheckbox.isChecked()) {
                binding.amusementLikesCheckbox.setChecked(false);
            }
            binding.amusementDislikesCheckbox.toggle();
        });

        binding.amusementLikesIcon.setOnClickListener((View v) -> {
            if (binding.amusementDislikesCheckbox.isChecked()) {
                binding.amusementDislikesCheckbox.setChecked(false);
            }
            binding.amusementLikesCheckbox.toggle();
        });

        binding.amusementDislikesCheckbox.setOnCheckedChangeListener((view, isChecked) -> {
            if (isChecked) {
                requestDislikes(1);
            }
            else {
                requestDislikes(-1);
            }
        });

        binding.amusementLikesCheckbox.setOnCheckedChangeListener((view, isChecked) -> {
            if (isChecked) {
                requestLikes(1);
            }
            else {
                requestLikes(-1);
            }
        });

        binding.controlBarBack.setOnClickListener((View v) -> {
            Navigation.findNavController(binding.getRoot()).popBackStack();
        });

        return binding.getRoot();
    }

    private void initAmusementItem() {
        if (getArguments() != null) {
            amusementItem.id = getArguments().getInt("id");
            amusementItem.title = getArguments().getString("title");
            amusementItem.imgUrl = getArguments().getString("imgUrl");
            amusementItem.desc = getArguments().getString("desc");
            amusementItem.views = getArguments().getInt("views");
            amusementItem.dislikes = getArguments().getInt("dislikes");
            amusementItem.likes = getArguments().getInt("likes");
        }
        else {
            amusementItem.id = 0;
            amusementItem.title = "Error";
            amusementItem.desc = "Item wasn't loaded";
            amusementItem.imgUrl = "";
            amusementItem.views = 0;
            amusementItem.dislikes = 0;
            amusementItem.likes = 0;
            Log.e("AMUSE_ME", "getArguments = null");
        }
    }

    @SuppressLint("SetTextI18n")
    private void initViews() {
        binding.amusementTitle.setText(amusementItem.title);
        binding.amusementDesc.setText(amusementItem.desc);
        binding.amusementViewsText.setText(Integer.toString(amusementItem.views));
        binding.amusementDislikesText.setText(Integer.toString(amusementItem.dislikes));
        binding.amusementLikesText.setText(Integer.toString(amusementItem.likes));

        binding.amusementDislikesCheckbox.setChecked(false);
        binding.amusementLikesCheckbox.setChecked(false);

        if (!Objects.equals(amusementItem.imgUrl, "")) {
            try {
                Picasso.get().load(amusementItem.imgUrl).into(binding.amusementImg);
            } catch (Exception e) {
                binding.amusementImg.setImageResource(R.drawable.amusement_img_default);
                Log.e("AMUSE_ME", e.toString());
            }
        }
        else {
            binding.amusementImg.setImageResource(R.drawable.amusement_img_default);
        }
    }

    @SuppressLint("CheckResult")
    private void requestDislikes(int delta) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AmuseMeServerAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        AmuseMeServerAPI amuseMeServerAPI = retrofit.create(AmuseMeServerAPI.class);
        amuseMeServerAPI.updateParam(amusementItem.id, "dislikes", delta)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    amusementItem.dislikes += delta;
                    updateDislikes();
                }, exception -> {
                    Log.e("AMUSE_ME", exception.toString());
                });
    }

    @SuppressLint("CheckResult")
    private void requestLikes(int delta) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AmuseMeServerAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        AmuseMeServerAPI amuseMeServerAPI = retrofit.create(AmuseMeServerAPI.class);
        amuseMeServerAPI.updateParam(amusementItem.id, "likes", delta)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    amusementItem.likes += delta;
                    updateLikes();
                }, exception -> {
                    Log.e("AMUSE_ME", exception.toString());
                });
    }

    @SuppressLint("SetTextI18n")
    private void updateDislikes() {
        if (binding.amusementDislikesCheckbox.isChecked()) {
            binding.amusementDislikesIcon.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), red_rose)));
        }
        else {
            binding.amusementDislikesIcon.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), gray_100)));
        }
        binding.amusementDislikesText.setText(Integer.toString(amusementItem.dislikes));
    }

    @SuppressLint("SetTextI18n")
    private void updateLikes() {
        if (binding.amusementLikesCheckbox.isChecked()) {
            binding.amusementLikesIcon.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), green_spring)));
        }
        else {
            binding.amusementLikesIcon.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), gray_100)));
        }
        binding.amusementLikesText.setText(Integer.toString(amusementItem.likes));
    }
}

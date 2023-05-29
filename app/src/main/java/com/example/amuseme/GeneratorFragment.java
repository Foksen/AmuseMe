package com.example.amuseme;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.amuseme.databinding.FragmentGeneratorBinding;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GeneratorFragment extends Fragment {
    FragmentGeneratorBinding binding;

    Disposable disposable;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;

    boolean isAnimLoaded;
    boolean isAmusementLoaded;
    AmusementItemResponse amusementItemResponse;

    Target picassoImageTarget;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentGeneratorBinding.inflate(inflater, container, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        sharedPreferencesEditor = sharedPreferences.edit();

        isAnimLoaded = false;
        isAmusementLoaded = false;

        binding.controlBarMenu.setOnClickListener((View v) -> {
            moveToThemesFragment();
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AmuseMeServerAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        AmuseMeServerAPI amuseMeServerAPI = retrofit.create(AmuseMeServerAPI.class);

        binding.generatorContentLayout.setOnTouchListener((View v, MotionEvent event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                String themesRequest = getThemesRequest();
                if (themesRequest.equals("")) {
                    moveToThemesFragment();
                    Toast.makeText(getContext(), "Выберите темы", Toast.LENGTH_SHORT).show();
                }
                else {
                    binding.generatorLoaderActive.animate().alpha(1.f).setDuration(1000).setInterpolator(new AccelerateInterpolator())
                            .withStartAction(() -> {
                                if (!isAmusementLoaded) {
                                    disposable = amuseMeServerAPI.getRandAmusement(getThemesRequest())
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(this::onSuccess, this::onError);
                                }
                            })
                            .withEndAction(() -> {
                                ((Vibrator)requireActivity().getSystemService(Context.VIBRATOR_SERVICE)).vibrate(20);
                                binding.generatorContentLayout.setOnTouchListener(null);
                                isAnimLoaded = true;
                                if (isAmusementLoaded) {
                                    moveToAmusementFragment(bundleFromAmusementItemResponse());
                                }
                            });
                }
            }
            else if (event.getAction() == MotionEvent.ACTION_UP) {
                binding.generatorLoaderActive.animate().alpha(0.f).setDuration(1000).setInterpolator(new AccelerateInterpolator())
                    .withEndAction(() -> {
                        isAnimLoaded = false;
                    });
            }
            return true;
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }

    private void onSuccess(AmusementItemResponse item) {
        amusementItemResponse = item;
        picassoImageTarget = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                AmusementFragment.imageBitmap = bitmap;
                isAmusementLoaded = true;
                if (isAnimLoaded) {
                    moveToAmusementFragment(bundleFromAmusementItemResponse());
                }
            }
            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                Log.e("AMUSE_ME", e.toString());
                AmusementFragment.imageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.amusement_img_default);
                isAmusementLoaded = true;
                if (isAnimLoaded) {
                    moveToAmusementFragment(bundleFromAmusementItemResponse());
                }
            }
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) { }
        };
        Picasso.get().load(item.imgUrl).into(picassoImageTarget);
    }

    private void onError(Throwable t) {
        binding.generatorLoaderActive.animate().cancel();
        isAnimLoaded = false;
        isAmusementLoaded = false;
        Bundle bundle = new Bundle();
        if (t instanceof HttpException) {
            bundle.putInt("errorCode", ErrorTypes.CODE_2_INTERNAL_ERROR);
        }
        else if (t instanceof IOException) {
            bundle.putInt("errorCode", ErrorTypes.CODE_1_CONNECTION_ERROR);
        }
        else {
            bundle.putInt("errorCode", ErrorTypes.CODE_0_UNKNOWN_ERROR);
        }
        moveToErrorFragment(bundle);
    }

    private Bundle bundleFromAmusementItemResponse() {
        Bundle bundle = new Bundle();
        bundle.putInt("id", amusementItemResponse.id);
        bundle.putString("title", amusementItemResponse.title);
        bundle.putString("desc", amusementItemResponse.desc);
        bundle.putString("imgUrl", amusementItemResponse.imgUrl);
        bundle.putInt("views", amusementItemResponse.views);
        bundle.putInt("dislikes", amusementItemResponse.dislikes);
        bundle.putInt("likes", amusementItemResponse.likes);
        return bundle;
    }

    private String getThemesRequest() {
        StringBuilder request = new StringBuilder();
        for (ThemeItemRecycler item : ThemesFragment.themes) {
            if (!sharedPreferences.contains(Integer.toString(item.themeId))) {
                sharedPreferencesEditor.putBoolean(Integer.toString(item.themeId), true);
                sharedPreferencesEditor.apply();
            }
            if (sharedPreferences.getBoolean(Integer.toString(item.themeId), true)) {
                if (request.length() > 0) {
                    request.append("_");
                }
                request.append(item.themeId);
            }
        }
        return request.toString();
    }

    private void moveToThemesFragment() {
        Navigation
                .findNavController(binding.getRoot())
                .navigate(R.id.action_generatorFragment_to_themesFragment);
    }

    private void moveToAmusementFragment(Bundle bundle) {
        Navigation
                .findNavController(binding.getRoot())
                .navigate(R.id.action_generatorFragment_to_amusementFragment, bundle);
    }

    private void moveToErrorFragment(Bundle bundle) {
        Navigation
                .findNavController(binding.getRoot())
                .navigate(R.id.action_generatorFragment_to_errorFragment, bundle);
    }
}
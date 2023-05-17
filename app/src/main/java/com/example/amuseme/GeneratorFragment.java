package com.example.amuseme;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.amuseme.databinding.FragmentAmusementBinding;
import com.example.amuseme.databinding.FragmentGeneratorBinding;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GeneratorFragment extends Fragment {
    FragmentGeneratorBinding binding;
    Disposable disposable;

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

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AmuseMeServerAPI.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            AmuseMeServerAPI amuseMeServerAPI = retrofit.create(AmuseMeServerAPI.class);
            disposable = amuseMeServerAPI.getRandAmusement("")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onSuccess, this::onError, this::onComplete, this::onSubscribe);
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }

    private void onSubscribe(Disposable d) { }

    private void onComplete() { }

    private void onSuccess(AmusementItemResponse item) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.id);
        bundle.putString("title", item.title);
        bundle.putString("desc", item.desc);
        bundle.putString("imgUrl", item.imgUrl);
        bundle.putInt("views", item.views);
        bundle.putInt("dislikes", item.dislikes);
        bundle.putInt("likes", item.likes);
        moveToAmusementFragment(bundle);
    }

    private void onError(Throwable t) {
        Log.e("AMUSE_ME", t.toString());
        Toast.makeText(getContext(), "Error during request [getRandAmusement]", Toast.LENGTH_SHORT).show();
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
}
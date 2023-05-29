package com.example.amuseme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.amuseme.databinding.FragmentErrorBinding;

public class ErrorFragment extends Fragment {
    FragmentErrorBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentErrorBinding.inflate(inflater, container, false);

        assert getArguments() != null;
        int errorCode = getArguments().getInt("errorCode");

        switch (errorCode) {
            case ErrorTypes.CODE_0_UNKNOWN_ERROR:
                binding.errorImg.setImageResource(R.drawable.error_unknown);
                binding.errorTitle.setText(R.string.error_0_title);
                binding.errorDesc.setText(R.string.error_0_desc);
                break;
            case ErrorTypes.CODE_1_CONNECTION_ERROR:
                binding.errorImg.setImageResource(R.drawable.error_connection);
                binding.errorTitle.setText(R.string.error_1_title);
                binding.errorDesc.setText(R.string.error_1_desc);
                break;
            case ErrorTypes.CODE_2_INTERNAL_ERROR:
                binding.errorImg.setImageResource(R.drawable.error_client);
                binding.errorTitle.setText(R.string.error_2_title);
                binding.errorDesc.setText(R.string.error_2_desc);
                break;
            default:
                binding.errorImg.setImageResource(R.drawable.error_unknown);
                binding.errorTitle.setText(R.string.error_title_holder);
                binding.errorDesc.setText(R.string.error_desc_holder);
                break;
        }

        binding.errorBtn.setOnClickListener((View v) -> {
            Navigation.findNavController(binding.getRoot()).popBackStack();
        });

        return binding.getRoot();
    }
}

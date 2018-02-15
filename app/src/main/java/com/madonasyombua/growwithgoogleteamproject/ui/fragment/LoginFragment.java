package com.madonasyombua.growwithgoogleteamproject.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.madonasyombua.growwithgoogleteamproject.R;
import com.madonasyombua.growwithgoogleteamproject.databinding.FragmentLoginBinding;

/**
 * Created by Ayo on 2/9/2018.
 */

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private static final String TAG = "LoginFragment";

    /*
    * This class is used to get data from the fragment like the username and stuff.
    * It also binds the views
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_login, container, false);
        binding.loginFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username_text = extractText(binding.editUsername);
                String password_text = extractText(binding.editPassword);

                Toast.makeText(getActivity(), "Logging in", Toast.LENGTH_SHORT).show();

            }
        });

        return binding.getRoot();
    }

    private String extractText(EditText text) {
        String newText = text.getText().toString().trim();
        return newText;
    }


}

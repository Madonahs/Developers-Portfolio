/*Copyright (c) 2018 Madona Syombua

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
 */
package com.madonasyombua.growwithgoogleteamproject.ui.fragment;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import com.madonasyombua.growwithgoogleteamproject.R;
import com.madonasyombua.growwithgoogleteamproject.databinding.FragmentSignupBinding;
import com.madonasyombua.growwithgoogleteamproject.ui.login.AppLoginManager;

import com.madonasyombua.growwithgoogleteamproject.data.models.User;

import com.madonasyombua.growwithgoogleteamproject.ui.activities.LoginActivity;


import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ayo on 2/9/2018.
 */

public class RegisterFragment extends Fragment {
    private static final String TAG =RegisterFragment.class.getName();

    private FragmentSignupBinding binding;
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    /**
     * This class is used to get data from the fragment like the username and stuff.
     * It also binds the views
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false);
        binding.registerFragmentButton.setOnClickListener(view -> {
            String username_txt = extractText(binding.editUsername);
            String email_txt = extractText(binding.editEmail);
            String password_txt = extractText(binding.editPassword);

            if (username_txt.isEmpty() || email_txt.isEmpty() || password_txt.isEmpty()) {
                // TODO add toast or change input color to show user that is required
                Toast.makeText(getContext(), "Username and password are required", Toast.LENGTH_SHORT).show();
                return;
            }

            // I added another parameter so that the username the user enter can be saved
            AppLoginManager.registerUser(getActivity(), new User(username_txt, email_txt, password_txt), username_txt);
            ((LoginActivity) Objects.requireNonNull(getActivity())).showHideProgressBar(true);
            Toast.makeText(getActivity(), "Registering", Toast.LENGTH_SHORT).show();
        });

        return binding.getRoot();
    }

    private String extractText(EditText text) {
        return text.getText().toString().trim();
    }

    public boolean validateEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}

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
package com.madonasyombua.growwithgoogleteamproject.fragment;

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
import com.madonasyombua.growwithgoogleteamproject.login.AppLoginManager;
import com.madonasyombua.growwithgoogleteamproject.databinding.FragmentLoginBinding;
import com.madonasyombua.growwithgoogleteamproject.models.User;
import com.madonasyombua.growwithgoogleteamproject.activities.LoginActivity;


/**
 * Created by Ayo on 2/9/2018.
 */

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private static final String TAG = LoginFragment.class.getName();
    /**
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
                String email_text = extractText(binding.editEmail);
                String password_text = extractText(binding.editPassword);

                if(email_text.isEmpty() || password_text.isEmpty()){
                    // TODO add toast or change input color to show user that is required
                    Toast.makeText(getContext(), "Username and password are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                AppLoginManager.signinUser(getActivity(), new User(email_text, password_text));
                ((LoginActivity) getActivity()).showHideProgressBar(true);
            }
        });

        return binding.getRoot();
    }

    private String extractText(EditText text) {
        String newText = text.getText().toString().trim();
        return newText;
    }
}




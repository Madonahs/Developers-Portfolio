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
import com.madonasyombua.growwithgoogleteamproject.database.AppLoginManager;
import com.madonasyombua.growwithgoogleteamproject.databinding.FragmentRegisterBinding;
import com.madonasyombua.growwithgoogleteamproject.models.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ayo on 2/9/2018.
 */

public class RegisterFragment extends Fragment {
    private static final String TAG = "RegisterFragment";

    private FragmentRegisterBinding binding;
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;

    /*
   * This class is used to get data from the fragment like the username and stuff.
   * It also binds the views
    */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_register, container, false);
        binding.registerFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username_txt = extractText(binding.editUsername);
                String email_txt = extractText(binding.editEmail);
                String password_txt = extractText(binding.editPassword);
                AppLoginManager.registerUser(getActivity(), new User(username_txt, email_txt, password_txt));
                Toast.makeText(getActivity(), "Registering", Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }

    private String extractText(EditText text){
        String newText = text.getText().toString().trim();
        return newText;
    }

    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}

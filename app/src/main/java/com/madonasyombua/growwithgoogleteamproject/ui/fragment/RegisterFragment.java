package com.madonasyombua.growwithgoogleteamproject.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.madonasyombua.growwithgoogleteamproject.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ayo on 2/9/2018.
 */

public class RegisterFragment extends Fragment {
    private static final String TAG = "RegisterFragment";

    private TextInputEditText username, email, password;
    private Button btn_register;
    private TextInputLayout til_username, til_email, til_password;

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
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        username = view.findViewById(R.id.edit_username);
        email = view.findViewById(R.id.edit_email);
        password = view.findViewById(R.id.edit_password);
        btn_register = view.findViewById(R.id.register_fragment_button);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username_txt = extractText(username);
                String email_txt = extractText(email);
                String password_txt = extractText(password);

                Toast.makeText(getActivity(), "Registering", Toast.LENGTH_SHORT).show();

            }
        });

        return view;
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

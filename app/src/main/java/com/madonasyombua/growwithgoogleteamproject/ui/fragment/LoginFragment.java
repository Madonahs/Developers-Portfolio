package com.madonasyombua.growwithgoogleteamproject.ui.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.madonasyombua.growwithgoogleteamproject.MainActivity;
import com.madonasyombua.growwithgoogleteamproject.R;
import com.madonasyombua.growwithgoogleteamproject.databinding.FragmentLoginBinding;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by Ayo on 2/9/2018.
 */

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private static final String TAG = "LoginFragment";
    private FirebaseAuth mAuth;
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
                String email_text = extractText(binding.editEmail);
                String password_text = extractText(binding.editPassword);
                mAuth = FirebaseAuth.getInstance();
                LoginUser(email_text,password_text);

                if(mAuth.getCurrentUser()!=null) {


                    Toast.makeText(getActivity(), "Logging in", LENGTH_SHORT).show();
                    //TODO: start MainActivity from Here
                    startActivity(new Intent(getActivity(), MainActivity.class));
                }
                else Toast.makeText(getActivity(), "Could not Login :( ... try registering", Toast.LENGTH_LONG).show();

            }
        });

        return binding.getRoot();
    }

    private String extractText(EditText text) {
        String newText = text.getText().toString().trim();
        return newText;
    }

    private void LoginUser(String email, final String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                        }
                        else
                            Log.e(TAG,"SigninWithEmail:Failure");



                        // ...
                    }
                });

        }
    }




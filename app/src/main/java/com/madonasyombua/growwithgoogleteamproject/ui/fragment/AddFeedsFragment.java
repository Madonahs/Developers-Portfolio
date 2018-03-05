package com.madonasyombua.growwithgoogleteamproject.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.madonasyombua.growwithgoogleteamproject.R;

/**
 * Created by Ayo on 3/3/2018.
 */

public class AddFeedsFragment extends Fragment {

    private static final String TAG = "AddFeedsFragment";
    TextInputEditText inputEditText;
    Button done;
    private onFragmentInteraction interaction;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            interaction = (onFragmentInteraction) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interaction = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_feeds, container, false);
        inputEditText = view.findViewById(R.id.edit_project_name);
        done = view.findViewById(R.id.button_add_feed);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = extractText(inputEditText);

                if (title != null){
                    interaction.receivedStringandImage(title);
                }else {
                    Toast.makeText(getActivity(), "Add a title", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onClick: " + title);
                }
            }
        });

        return view;
    }

    public interface onFragmentInteraction{
        void receivedStringandImage(String title);
    }
    private String extractText(TextInputEditText text) {
        return text.getText().toString().trim();
    }
}

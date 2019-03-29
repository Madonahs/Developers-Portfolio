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

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.text.Html;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.madonasyombua.growwithgoogleteamproject.R;
import com.madonasyombua.growwithgoogleteamproject.databinding.FragmentProfileBinding;
import com.madonasyombua.growwithgoogleteamproject.dialogs.ProfileFragmentDialog;
import com.madonasyombua.growwithgoogleteamproject.interfaces.OnFragmentInteractionListener;
import com.madonasyombua.growwithgoogleteamproject.data.models.User;
import com.madonasyombua.growwithgoogleteamproject.util.Constant;
import com.madonasyombua.growwithgoogleteamproject.data.actions.FirebaseAction;

import java.util.Objects;

import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 *
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment
        implements ProfileFragmentDialog.OnSubmitListener, View.OnTouchListener {

    private FragmentProfileBinding mBinding;
    private ProfileFragmentDialog dialog;
    private OnFragmentInteractionListener mListener;
    private GestureDetectorCompat gd;
    private User user;
    private int id;

    private static final String TAG = ProfileFragment.class.getName();



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        user = User.build(Objects.requireNonNull(getArguments().getBundle(Constant.USER)));

        GestureDetector.SimpleOnGestureListener listener = new GestureDetector.SimpleOnGestureListener() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Intent intent = null;
                switch (id) {
                    case R.id.phone_tv:
                        intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + mBinding.phoneTv.getText()));
                        break;
                    case R.id.email_tv:
                        intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setData(Uri.parse("mailto:"+mBinding.emailTv.getText()));
                        break;
                    case R.id.web_tv:
                        intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://" + mBinding.webTv.getText()/*assuming no one
                                adds https:// to their web link*/));
                        break;
                }
                assert intent != null;
                if (intent.resolveActivity(Objects.requireNonNull(getActivity()).getPackageManager()) != null)
                    startActivity(intent);
                return true;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }
        };
        gd = new GestureDetectorCompat(getActivity(), listener);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        

        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);

        mBinding.btnEditProfile.setOnClickListener(view -> {
            dialog = new ProfileFragmentDialog();
            dialog.show(getChildFragmentManager(), TAG);
        });


        //On touch listener to handle to user interactions
        mBinding.webTv.setOnTouchListener(this);
        mBinding.emailTv.setOnTouchListener(this);
        mBinding.phoneTv.setOnTouchListener(this);
        mBinding.homeTv.setOnTouchListener(this);

        //FIXED:  java.lang.NullPointerException:
        if(user.getPhone() != null)
            user.setPhone(PhoneNumberUtils.formatNumber(user.getPhone()));
        else
            mBinding.phoneTv.setVisibility(GONE);

        mBinding.setUser(user);
        setStatus(user.getStatus());
        mBinding.intro.setText(Html.fromHtml("<u>Intro</u>"));
        return mBinding.getRoot();
    }

// implement this on the main activity
   /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void submit(Bundle data) {
        User.build(user, data);
//        user.setPhone(PhoneNumberUtils.formatNumber(user.getPhone()));
        mBinding.setUser(user);
        FirebaseAction.write(user, (databaseError, databaseReference) -> {
            if (databaseError == null) {
                Toast.makeText(getContext(), "User created", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Error creating user:" +
                        databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onPause() {
        super.onPause();
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        id = v.getId();
        gd.onTouchEvent(event);
        return true;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user User object.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(@NonNull User user) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putBundle(Constant.USER,user.bundleUp());
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Sets the user status and updates indicator
     * according to online state
     *
     * @param status The status of the user
     */
    private void setStatus(User.Status status) {
        switch (status) {
            case ONLINE:
            {
                mBinding.status.setCompoundDrawablesWithIntrinsicBounds(getResources()
                        .getDrawable(R.drawable.ic_online), null, null, null);
                mBinding.status.setText(getString(R.string.online));
            }

            break;
            case OFFLINE:
            {
                mBinding.status.setCompoundDrawablesWithIntrinsicBounds(getResources()
                        .getDrawable(R.drawable.ic_offline), null, null, null);
                mBinding.status.setText(getString(R.string.offline));
            }
            break;
        }
    }
}

package com.madonasyombua.growwithgoogleteamproject.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.madonasyombua.growwithgoogleteamproject.R;
import com.madonasyombua.growwithgoogleteamproject.databinding.FragmentProfileBinding;
import com.madonasyombua.growwithgoogleteamproject.dialogs.ProfileFragmentDialog;
import com.madonasyombua.growwithgoogleteamproject.interfaces.OnFragmentInteractionListener;
import com.madonasyombua.growwithgoogleteamproject.models.User;
import com.madonasyombua.growwithgoogleteamproject.util.Constant;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * <p>
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment
        implements ProfileFragmentDialog.OnSubmitListener, View.OnTouchListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentProfileBinding mBinding;
    private ProfileFragmentDialog dialog;
    private OnFragmentInteractionListener mListener;
    private GestureDetectorCompat gd;
    private User user;
    private int id;

    private static final String TAG = "update-profile-fragment";

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            user = User.build(getArguments().getBundle(Constant.USER));
        }
        //TODO: remove
        user = new User();

        GestureDetector.SimpleOnGestureListener listener = new GestureDetector.SimpleOnGestureListener() {

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
                if (intent.resolveActivity(getActivity().getPackageManager()) != null)
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

        mBinding.btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new ProfileFragmentDialog();
                dialog.show(getChildFragmentManager(), TAG);
            }
        });


        //On touch listener to handle to user interactions
        mBinding.webTv.setOnTouchListener(this);
        mBinding.emailTv.setOnTouchListener(this);
        mBinding.phoneTv.setOnTouchListener(this);
        mBinding.homeTv.setOnTouchListener(this);


        mBinding.setUser(user);
        setStatus(false/*TODO: Replace with user.getStatus*/);
        mBinding.intro.setText(Html.fromHtml("<u>Intro</u>"));
        return mBinding.getRoot();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void submit(Bundle data) {
        //TODO: update DB with data
        user = User.build(user, data);
        mBinding.setUser(user);
    }

    @Override
    public void onPause() {
        super.onPause();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
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
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private void setStatus(boolean online) {
        if (online) {
            mBinding.status.setCompoundDrawablesWithIntrinsicBounds(getResources()
                    .getDrawable(R.drawable.ic_online), null, null, null);
            mBinding.status.setText(getString(R.string.online));
        } else {
            mBinding.status.setCompoundDrawablesWithIntrinsicBounds(getResources()
                    .getDrawable(R.drawable.ic_offline), null, null, null);
            mBinding.status.setText(getString(R.string.offline));
        }
    }
}

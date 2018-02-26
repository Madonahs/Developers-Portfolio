package com.madonasyombua.growwithgoogleteamproject.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.madonasyombua.growwithgoogleteamproject.R;
import com.madonasyombua.growwithgoogleteamproject.databinding.FragmentProfileBinding;
import com.madonasyombua.growwithgoogleteamproject.databinding.FragmentProfileDialogBinding;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.ProfileFragment;
import com.madonasyombua.growwithgoogleteamproject.util.Constant;

/**
 * Created by chuk on 2/23/18.
 */

/**
 * A simple {@link DialogFragment} subclass.
 * Activities/Fragments that contain this dialog must implement the
 * {@link ProfileFragmentDialog.OnSubmitListener} interface
 * to handle form submit.
 */
public class ProfileFragmentDialog extends DialogFragment {
    private FragmentProfileDialogBinding mBinding;
    private OnSubmitListener mListener;


    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Fragment fragment = getParentFragment();
        if(!(fragment instanceof OnSubmitListener))
            throw new RuntimeException(fragment.toString() + "must implement OnSubmitListener");

        mListener = (OnSubmitListener) fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.fragment_profile_dialog,container,false);

        mBinding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.submit(submit());
                getDialog().dismiss();
            }
        });

        return mBinding.getRoot();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private Bundle submit(){
        Bundle data = new Bundle(5);
        data.putString(Constant.INTRO,mBinding.introEdit.getText().toString());
        data.putString(Constant.PHONE,mBinding.phoneEdit.getText().toString());

        data.putString(Constant.LOCATION,mBinding.homeEdit.getText().toString());
        data.putString(Constant.WEB,mBinding.webEdit.getText().toString());
        data.putString(Constant.EMAIL,mBinding.emailEdit.getText().toString());

        return data;
    }

    public interface OnSubmitListener{
        void submit(Bundle data);
    }
}

package com.madonasyombua.growwithgoogleteamproject.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import com.madonasyombua.growwithgoogleteamproject.R;
import com.madonasyombua.growwithgoogleteamproject.databinding.FragmentProfileBinding;
import com.madonasyombua.growwithgoogleteamproject.databinding.FragmentProfileDialogBinding;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.ProfileFragment;
import com.madonasyombua.growwithgoogleteamproject.util.Constant;

/**
 * Created by chuk on 2/23/18.
 */

public class ProfileFragmentDialog extends DialogFragment {
    private FragmentProfileDialogBinding mBinding;
    private OnSubmitListener mListener;


    @Override
    public void setTargetFragment(@Nullable Fragment fragment, int requestCode) {
        super.setTargetFragment(fragment, requestCode);

        if(!(fragment instanceof OnSubmitListener))
            throw new RuntimeException(fragment.toString() + "must implement OnSubmitListener");

        mListener = (OnSubmitListener) fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.fragment_profile_dialog,null,false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(mBinding.getRoot())
                .setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.submit(submit());
                    }
                });

        return builder.create();
    }

    public static ProfileFragmentDialog getInstance(ProfileFragment profileFragment) {
        ProfileFragmentDialog dialog = new ProfileFragmentDialog();
        dialog.setTargetFragment(profileFragment,0);
        return dialog;
    }

    private Bundle submit(){
        Bundle data = new Bundle(5);
        data.putString(Constant.INTRO,mBinding.introEdit.getText().toString());
        data.putString(Constant.PHONE,mBinding.phoneEdit.getText().toString());
        data.putString(Constant.LOCATION,mBinding.locationEdit.getText().toString());
        data.putString(Constant.WEB,mBinding.webEdit.getText().toString());
        data.putString(Constant.EMAIL,mBinding.emailEdit.getText().toString());


        return data;
    }


    public interface OnSubmitListener{
        void submit(Bundle data);
    }
}

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
package com.madonasyombua.growwithgoogleteamproject.dialogs

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.madonasyombua.growwithgoogleteamproject.databinding.FragmentProfileDialogBinding
import com.madonasyombua.growwithgoogleteamproject.util.Constant
import kotlinx.android.synthetic.main.fragment_profile_dialog.*

class ProfileFragmentDialog : DialogFragment() {
    private var mBinding: FragmentProfileDialogBinding? = null
    private var mListener: OnSubmitListener? = null

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val fragment = parentFragment
        if (fragment !is OnSubmitListener) throw RuntimeException(fragment.toString() + "must implement OnSubmitListener")
        mListener = fragment
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentProfileDialogBinding.bind(view)
        mBinding?.submitBtn?.setOnClickListener {
            mListener?.submit(submit())
            dialog?.dismiss()
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    private fun submit(): Bundle {
        val data = Bundle(5)
        data.putString(Constant.INTRO, getString(intro_edit))
        data.putString(Constant.PHONE, getString(phone_edit))
        data.putString(Constant.LOCATION, getString(home_edit))
        data.putString(Constant.WEB, getString(web_edit))
        data.putString(Constant.EMAIL, getString(email_edit))
        return data
    }


    interface OnSubmitListener {
        fun submit(data: Bundle?)
    }

    companion object {
        private fun getString(view: View): String {
            if (view is EditText) {
                return view.text.toString()
            } else if (view is TextView) return view.text.toString()
            return view.toString()
        }
    }
}
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
package com.madonasyombua.growwithgoogleteamproject.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.text.Html
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.DatabaseReference.CompletionListener
import com.madonasyombua.growwithgoogleteamproject.R
import com.madonasyombua.growwithgoogleteamproject.data.actions.FirebaseAction.write
import com.madonasyombua.growwithgoogleteamproject.data.models.User
import com.madonasyombua.growwithgoogleteamproject.databinding.FragmentProfileBinding
import com.madonasyombua.growwithgoogleteamproject.dialogs.ProfileFragmentDialog
import com.madonasyombua.growwithgoogleteamproject.dialogs.ProfileFragmentDialog.OnSubmitListener
import com.madonasyombua.growwithgoogleteamproject.interfaces.OnFragmentInteractionListener
import com.madonasyombua.growwithgoogleteamproject.util.Constant
import java.util.*

class ProfileFragment : Fragment(R.layout.fragment_profile), OnSubmitListener, OnTouchListener {
    private var mBinding: FragmentProfileBinding? = null
    private var dialog: ProfileFragmentDialog? = null
    private var mListener: OnFragmentInteractionListener? = null
    private var gd: GestureDetectorCompat? = null
    private var user: User? = null
    private var fragmentProfileDialogBinding : FragmentProfileBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        user = arguments?.getBundle(Constant.USER)?.let { User.build(it) }
        val listener: SimpleOnGestureListener = object : SimpleOnGestureListener() {

            override fun onDoubleTap(e: MotionEvent): Boolean {
                var intent: Intent? = null
                when (id) {
                    R.id.phone_tv -> {
                        intent = Intent(Intent.ACTION_DIAL)
                        intent.data = Uri.parse("tel:" + mBinding?.phoneTv?.text)
                    }
                    R.id.email_tv -> {
                        intent = Intent(Intent.ACTION_SENDTO)
                        intent.data = Uri.parse("mailto:" + mBinding?.emailTv?.text)
                    }
                    R.id.web_tv -> intent = Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://" + mBinding?.webTv?.text
                            ))
                }

                if (activity?.packageManager?.let { intent?.resolveActivity(it) } != null) startActivity(intent)
                return true
            }

            override fun onDown(e: MotionEvent): Boolean {
                return true
            }
        }
        gd = GestureDetectorCompat(activity, listener)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       val  mBinding = FragmentProfileBinding.bind(view)
        fragmentProfileDialogBinding = mBinding

        mBinding.btnEditProfile.setOnClickListener {
            dialog = ProfileFragmentDialog()
            dialog?.show(childFragmentManager, TAG)
        }

        mBinding.webTv.setOnTouchListener(this)
        mBinding.emailTv.setOnTouchListener(this)
        mBinding.phoneTv.setOnTouchListener(this)
        mBinding.homeTv.setOnTouchListener(this)

        if (user?.phone != null) user?.phone = PhoneNumberUtils.formatNumber(user?.phone, Locale.getDefault().country) else mBinding.phoneTv.visibility = View.GONE
        user?.status?.let { setStatus(it) }
        mBinding.intro.text = Html.fromHtml("<u>Intro</u>")

    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun submit(data: Bundle?) {
        user?.let {
            if (data != null) {
                User.build(it, data)
            }
        }

        user?.let {
            write(it, CompletionListener { databaseError: DatabaseError?, _: DatabaseReference? ->
            if (databaseError == null) {
                Toast.makeText(context, "User created", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Error creating user:" +
                        databaseError.toString(), Toast.LENGTH_SHORT).show()
            }
        })
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    override fun onPause() {
        super.onPause()
        Objects.requireNonNull((activity) as AppCompatActivity).supportActionBar?.show()
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        var id = v.id
        gd?.onTouchEvent(event)
        return true
    }

    private fun setStatus(status: User.Status) {
        when (status) {
            User.Status.ONLINE -> {
                mBinding?.status?.setCompoundDrawablesWithIntrinsicBounds(ResourcesCompat.getDrawable(resources,R.drawable.ic_online,null), null, null, null)
                mBinding?.status?.text = getString(R.string.online)
            }
            User.Status.OFFLINE -> {
                mBinding?.status?.setCompoundDrawablesWithIntrinsicBounds(ResourcesCompat.getDrawable(resources,R.drawable.ic_offline, null)
                       , null, null, null)
                mBinding?.status?.text = getString(R.string.offline)
            }
        }
    }

    companion object {
        private val TAG = ProfileFragment::class.java.name
        @JvmStatic
        fun newInstance(user: User): ProfileFragment {
            val fragment = ProfileFragment()
            val args = Bundle()
            args.putBundle(Constant.USER, user.bundleUp())
            fragment.arguments = args
            return fragment
        }


    }
}
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
package com.madonasyombua.growwithgoogleteamproject.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.facebook.CallbackManager
import com.facebook.internal.CallbackManagerImpl
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.madonasyombua.growwithgoogleteamproject.R
import com.madonasyombua.growwithgoogleteamproject.data.models.User
import com.madonasyombua.growwithgoogleteamproject.databinding.ActivityLoginBinding
import com.madonasyombua.growwithgoogleteamproject.ui.activities.LoginActivity
import com.madonasyombua.growwithgoogleteamproject.ui.adapter.FragmentsAdapter
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.LoginFragment
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.RegisterFragment
import com.madonasyombua.growwithgoogleteamproject.ui.intro.OnBoardingActivity
import com.madonasyombua.growwithgoogleteamproject.ui.login.AppLoginManager.LoginInterface
import com.madonasyombua.growwithgoogleteamproject.ui.login.LoginStatusManager.getLoginStatus
import com.madonasyombua.growwithgoogleteamproject.ui.login.LoginStatusManager.storeLoginStatus
import com.madonasyombua.growwithgoogleteamproject.util.Constant
import jonathanfinerty.once.Once
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginInterface, View.OnClickListener {
    private var mCallbackManager: CallbackManager? = null

    private var mGoogleSignInClient: GoogleSignInClient? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (getLoginStatus(this) &&
            FirebaseAuth.getInstance().currentUser != null
        ) {
            startActivity(Intent(this, MainActivity::class.java))
            return
        }

        mCallbackManager = CallbackManager.Factory.create()

        binding.btnLogin.setOnClickListener(this)
        binding.btnRegister.setOnClickListener(this)
        binding.btnFacebookLogin.setOnClickListener(this)
        binding.btnGoogleLogin.setOnClickListener(this)

        binding.btnLogin.setBackgroundResource(R.drawable.button_rounded_focused)
        binding.btnRegister.setBackgroundResource(R.drawable.button_rounded_normal)

        setViewPager(binding.container)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        Once.initialise(this)
        if (!Once.beenDone(Once.THIS_APP_INSTALL, "showTutorial")) {
            Log.i(TAG, "onCreate: First time")
            startActivityForResult(Intent(this, OnBoardingActivity::class.java), SHOW_INTRO)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SHOW_INTRO) {
            if (resultCode == RESULT_OK) {
                Once.markDone("showTutorial")
            }
        } else if (requestCode == CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode()) {
            mCallbackManager?.onActivityResult(requestCode, resultCode, data)
        } else if (requestCode == GOOGLE_SIGN_IN_REQUEST_CODE) {
            val gTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleGoogleSignInResult(gTask)
        }
    }

    private fun setViewPager(viewPager: ViewPager) {
        val adapter = FragmentsAdapter(supportFragmentManager)
        adapter.addFragment(LoginFragment())
        viewPager.adapter = adapter
    }

    private fun registerFragment() {
        val adapter = FragmentsAdapter(supportFragmentManager)
        adapter.addFragment(RegisterFragment())
        container.adapter = adapter
    }


    fun showHideProgressBar(show: Boolean) {
        if (show) {
            login_loader.visibility = View.VISIBLE
        } else {
            login_loader.visibility = View.INVISIBLE
        }
    }

    override fun onSigninSuccess(user: User?) {
        val intent = Intent(this, MainActivity::class.java)
            .putExtra(Constant.USER, user!!.bundleUp())
        startActivity(intent)
        showHideProgressBar(false)
        storeLoginStatus(this, true)
        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onRegistrationSuccess() {
        showHideProgressBar(false)
        Toast.makeText(this, "Registration Success", Toast.LENGTH_SHORT).show()
    }

    override fun onSigninFailed() {
        showHideProgressBar(false)
    }

    override fun onRegistrationFailed() {
        showHideProgressBar(false)
    }

    override fun onResetPasswordSuccess() {
        Toast.makeText(this, "onResetPasswordSuccess", Toast.LENGTH_SHORT).show()
    }

    override fun onResetPasswordFailed() {
        Toast.makeText(this, "onResetPasswordFailed", Toast.LENGTH_SHORT).show()
    }

    /***
     * Handle login process when the login button is pressed
     */
    private fun handleLoginProcess() {
        // Toggle buttons
        btn_login.setBackgroundResource(R.drawable.button_rounded_focused)
        btn_register.setBackgroundResource(R.drawable.button_rounded_normal)
        setViewPager(container)
    }

    /*
     * Handle registration process when the register button is clicked
     */
    private fun handleRegistrationProcess() {
        // Toggle buttons
        btn_login.setBackgroundResource(R.drawable.button_rounded_normal)
        btn_register.setBackgroundResource(R.drawable.button_rounded_focused)
        registerFragment()
    }

    private fun signInWithFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(
            this@LoginActivity,
            listOf(PUBLIC_PROFILE_PERMISSION, EMAIL_PERMISSION)
        )
    }

    private fun signInWithGoogle() {

        val googleSignInIntent = mGoogleSignInClient?.signInIntent
        startActivityForResult(googleSignInIntent, GOOGLE_SIGN_IN_REQUEST_CODE)
    }


    private fun handleGoogleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            if (account != null) {
                val personName = account.displayName
                val personEmail = account.email

                Log.d(TAG, "Google info $personName - $personEmail")
                val mainActivityIntent = Intent(baseContext, MainActivity::class.java)

                startActivity(mainActivityIntent)
            }
        } catch (e: ApiException) {

            e.printStackTrace()
        }
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_register -> handleRegistrationProcess()
            R.id.btn_login -> handleLoginProcess()
            R.id.btn_facebook_login -> signInWithFacebook()
            R.id.btn_google_login -> signInWithGoogle()
        }
    }

    companion object {

        private const val PUBLIC_PROFILE_PERMISSION = "public_profile"
        private const val EMAIL_PERMISSION = "email"
        private val TAG = LoginActivity::class.java.name
        private const val SHOW_INTRO = 1
        private const val GOOGLE_SIGN_IN_REQUEST_CODE = 500
    }
}
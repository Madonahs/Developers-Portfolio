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

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.madonasyombua.growwithgoogleteamproject.R
import com.madonasyombua.growwithgoogleteamproject.data.models.User
import com.madonasyombua.growwithgoogleteamproject.databinding.FragmentSigninBinding
import com.madonasyombua.growwithgoogleteamproject.ui.activities.LoginActivity
import com.madonasyombua.growwithgoogleteamproject.ui.login.AppLoginManager.signinUser

class LoginFragment : Fragment( R.layout.fragment_signin) {

    private var fragmentLoginFragmentBinding : FragmentSigninBinding ? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSigninBinding.bind(view)
        fragmentLoginFragmentBinding = binding

        binding.loginFragmentButton.setOnClickListener {
            val emailText = extractText(binding.editEmail)
            val passwordText = extractText(binding.editPassword)
            if (emailText.isEmpty() || passwordText.isEmpty()) {
                Toast.makeText(context, "Username and password are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            activity?.let { it1 -> signinUser(it1, User(emailText, passwordText)) }
            (activity as LoginActivity).showHideProgressBar(true)
        }
    }

    private fun extractText(text: EditText): String {
        return text.text.toString().trim { it <= ' ' }
    }

    companion object;

    override fun onDestroyView() {
        fragmentLoginFragmentBinding = null
        super.onDestroyView()
    }

}
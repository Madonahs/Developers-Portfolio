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
import com.madonasyombua.growwithgoogleteamproject.databinding.FragmentSignupBinding
import com.madonasyombua.growwithgoogleteamproject.ui.activities.LoginActivity
import com.madonasyombua.growwithgoogleteamproject.ui.login.AppLoginManager.registerUser
import java.util.regex.Pattern

class RegisterFragment : Fragment(R.layout.fragment_signup) {
    private var fragmentSignupBinding: FragmentSignupBinding? = null
    private val pattern = Pattern.compile(EMAIL_PATTERN)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSignupBinding.bind(view)
        fragmentSignupBinding = binding
        binding.registerFragmentButton.setOnClickListener {
            val usernameTxt = extractText(binding.editUsername)
            val emailTxt = extractText(binding.editEmail)
            val passwordTxt = extractText(binding.editPassword)
            if (usernameTxt.isEmpty() || emailTxt.isEmpty() || passwordTxt.isEmpty()) {

                Toast.makeText(context, "Username and password are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            activity?.let { it1 -> registerUser(it1, User(usernameTxt, emailTxt, passwordTxt), usernameTxt) }
            (activity as LoginActivity).showHideProgressBar(true)
            Toast.makeText(activity, "Registering", Toast.LENGTH_SHORT).show()
        }

    }

    private fun extractText(text: EditText): String {
        return text.text.toString().trim { it <= ' ' }
    }

    companion object {
        private const val EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$"
    }

    override fun onDestroyView() {
        fragmentSignupBinding = null
        super.onDestroyView()
    }
}
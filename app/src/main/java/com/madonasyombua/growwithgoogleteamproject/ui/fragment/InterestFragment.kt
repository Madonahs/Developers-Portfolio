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
import androidx.fragment.app.Fragment
import com.madonasyombua.growwithgoogleteamproject.R
import com.madonasyombua.growwithgoogleteamproject.databinding.FragmentInterestBinding
import com.madonasyombua.growwithgoogleteamproject.interfaces.OnFragmentInteractionListener

class InterestFragment : Fragment(R.layout.fragment_interest) {
    private var mListener: OnFragmentInteractionListener? = null
    private var fragmentInterestBinding: FragmentInterestBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentInterestBinding.bind(view)
        fragmentInterestBinding = binding
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    companion object {
    }

    override fun onDestroyView() {
        fragmentInterestBinding = null
        super.onDestroyView()
    }

}
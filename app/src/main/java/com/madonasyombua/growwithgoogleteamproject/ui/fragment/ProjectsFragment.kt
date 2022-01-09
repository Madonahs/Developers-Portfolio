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
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.FacebookSdk
import com.madonasyombua.growwithgoogleteamproject.R
import com.madonasyombua.growwithgoogleteamproject.data.models.Portfolio
import com.madonasyombua.growwithgoogleteamproject.databinding.FragmentProjectsBinding
import com.madonasyombua.growwithgoogleteamproject.interfaces.OnFragmentInteractionListener
import com.madonasyombua.growwithgoogleteamproject.ui.adapter.PortfolioAdapter
import java.util.*

class ProjectsFragment : Fragment(R.layout.fragment_projects) {
    private val portfolioList: MutableList<Portfolio> = ArrayList()
    private var mAdapter: PortfolioAdapter? = null
    private var mListener: OnFragmentInteractionListener? = null
    private var fragmentProjectsBinding: FragmentProjectsBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            val mParam1 = arguments?.getString(ARG_PARAM1)
            val mParam2 = arguments?.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentProjectsBinding.bind(view)
        fragmentProjectsBinding = binding

        mAdapter = PortfolioAdapter(portfolioList)
        val mLayoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(FacebookSdk.getApplicationContext())
        binding.portfolioRecyclerView.layoutManager = mLayoutManager
        binding.portfolioRecyclerView.itemAnimator = DefaultItemAnimator()
        binding.portfolioRecyclerView.adapter = mAdapter
        testPortfolioData()

    }

    private fun testPortfolioData() {
        var portfolio = Portfolio(
            "Simple Maths",
            "Android Application",
            "Simple Maths is a" +
                    "mobile app project that I did for kids. It's a learning app for Arithmetic",
            R.drawable.ic_facebook
        )
        portfolioList.add(portfolio)
        portfolio = Portfolio(
            "Essay Tutors", "Web Development", "This is an online tutoring network" +
                    "for teachers and students.", R.drawable.logo
        )
        portfolioList.add(portfolio)
        portfolio = Portfolio(
            "Budgeting Buddy", "Android Development", "This is a budgeting planner" +
                    " and expense tracking mobile device for Android devices.", R.drawable.ic_google
        )
        portfolioList.add(portfolio)
        mAdapter?.notifyDataSetChanged()
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    companion object {

        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

    }

    override fun onDestroyView() {
        fragmentProjectsBinding = null
        super.onDestroyView()
    }

}
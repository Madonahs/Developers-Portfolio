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

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.FacebookSdk
import com.madonasyombua.growwithgoogleteamproject.R
import com.madonasyombua.growwithgoogleteamproject.data.models.Portfolio
import com.madonasyombua.growwithgoogleteamproject.interfaces.OnFragmentInteractionListener
import com.madonasyombua.growwithgoogleteamproject.ui.adapter.PortfolioAdapter
import java.util.*

/**
 * @author madona 12/23/19
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 *
 * to handle interaction events.
 * Use the [ProjectsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProjectsFragment : Fragment() {
    private val portfolioList: MutableList<Portfolio> = ArrayList()
    private var mAdapter: PortfolioAdapter? = null
    private var mListener: OnFragmentInteractionListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) { // TODO: Rename and change types of parameters
            val mParam1 = arguments!!.getString(ARG_PARAM1)
            val mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? { // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_projects, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.portfolio_recycler_view)
        mAdapter = PortfolioAdapter(portfolioList)
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(FacebookSdk.getApplicationContext())
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = mAdapter
        testPortfolioData()
        return view
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri?) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    private fun testPortfolioData() {
        var portfolio = Portfolio("Simple Maths", "Android Application", "Simple Maths is a" +
                "mobile app project that I did for kids. It's a learning app for Arithmetic", R.drawable.ic_facebook)
        portfolioList.add(portfolio)
        portfolio = Portfolio("Essay Tutors", "Web Development", "This is an online tutoring network" +
                "for teachers and students.", R.drawable.logo)
        portfolioList.add(portfolio)
        portfolio = Portfolio("Budgeting Buddy", "Android Development", "This is a budgeting planner" +
                " and expense tracking mobile device for Android devices.", R.drawable.ic_google)
        portfolioList.add(portfolio)
        mAdapter!!.notifyDataSetChanged()
    }


    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProjectsFragment.
         */
// TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): ProjectsFragment {
            val fragment = ProjectsFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}
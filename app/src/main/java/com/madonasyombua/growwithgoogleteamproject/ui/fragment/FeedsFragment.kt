package com.madonasyombua.growwithgoogleteamproject.ui.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.madonasyombua.growwithgoogleteamproject.R
import com.madonasyombua.growwithgoogleteamproject.data.models.Paths
import com.madonasyombua.growwithgoogleteamproject.data.models.Post
import com.madonasyombua.growwithgoogleteamproject.databinding.FragmentFeedsBinding
import com.madonasyombua.growwithgoogleteamproject.ui.adapter.FeedsAdapter

class FeedsFragment : Fragment(R.layout.fragment_feeds) {
    private var mListener: OnFragmentInteractionListener? = null
    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: RecyclerView.Adapter<*>? = null
    private var mPosts: ArrayList<Post> = ArrayList()
    private var currentUSer: FirebaseUser? = null
    private var fragmentFeedsBinding: FragmentFeedsBinding? = null

    private val feedsListener: ChildEventListener = object : ChildEventListener {
        override fun onChildAdded(dataSnapshot: DataSnapshot?, s: String?) {
            val p = dataSnapshot?.getValue(Post::class.java)
            if (p != null) {
                p.key = dataSnapshot.key
                if (!mPosts.contains(p)) {
                    mPosts.add(p)
                    mPosts.size.minus(1).let { mAdapter?.notifyItemInserted(it) }
                }
            }
        }

        override fun onChildChanged(dataSnapshot: DataSnapshot, s: String) {
            val p = dataSnapshot.getValue(Post::class.java)
            if (p != null) {
                p.key = dataSnapshot.key
                if (mPosts.contains(p)) {
                    val idx = mPosts.indexOf(p)
                    if (idx > -1) {
                        mPosts[idx] = p
                        mAdapter?.notifyItemChanged(idx)
                    }
                }
            }
        }

        override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
        override fun onChildMoved(dataSnapshot: DataSnapshot, s: String) {}
        override fun onCancelled(databaseError: DatabaseError) {}
    }
    private var mListening = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPosts = ArrayList()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentFeedsBinding.bind(view)
        fragmentFeedsBinding = binding

        currentUSer = FirebaseAuth.getInstance().currentUser

        val fab: FloatingActionButton = view.findViewById(R.id.add_feeds)
        fab.setOnClickListener { showPostDialog() }
        binding.displayEmpty.visibility = View.GONE
        binding.recyclerView.setHasFixedSize(true)

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        mRecyclerView?.layoutManager = mLayoutManager
        mAdapter = FeedsAdapter(activity, mPosts, { }, true)
        mRecyclerView?.adapter = mAdapter
        startFeedListener()
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    fun showPostDialog() {
        val fm = activity?.supportFragmentManager
        println("fm: $fm")
        val prefs = activity?.getSharedPreferences(
            "com.madonasyombua.growwithgoogleteamproject.ui.fragment",
            Context.MODE_PRIVATE
        )
        val postDialog = PostFeedFragment.newInstance(
            getString(R.string.new_post), getString(R.string.postingas),
            currentUSer?.displayName, prefs?.getString("name", "")
        )
        if (fm != null) {
            postDialog.show(fm, "fragment_post_dialog")
        }
    }

    override fun onResume() {
        super.onResume()
        if (mAdapter != null && mRecyclerView != null && !mListening) {
            startFeedListener()
        } else {
            Log.i("FeedsFragment", "Did not start feed listener, already listening: $mListening")
        }
    }

    override fun onPause() {
        super.onPause()
        FirebaseDatabase.getInstance().getReference(Paths.FEED).removeEventListener(feedsListener)
        mListening = false
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = if (context is OnFragmentInteractionListener) {
            context
        } else {
            throw RuntimeException(
                context.toString()
                        + " must implement OnFragmentInteractionListener"
            )
        }

    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnFragmentInteractionListener {

    }

    private fun startFeedListener() {

        FirebaseDatabase.getInstance().getReference("feeds").addChildEventListener(feedsListener)
        mListening = true
    }

    companion object {

        fun newInstance(): FeedsFragment {
            return FeedsFragment()
        }
    }

    override fun onDestroyView() {
        fragmentFeedsBinding = null
        super.onDestroyView()
    }
}
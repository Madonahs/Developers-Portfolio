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
package com.madonasyombua.growwithgoogleteamproject.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.madonasyombua.growwithgoogleteamproject.R;
import com.madonasyombua.growwithgoogleteamproject.adapter.FeedsAdapter;
import com.madonasyombua.growwithgoogleteamproject.models.Paths;
import com.madonasyombua.growwithgoogleteamproject.models.Post;
import java.util.ArrayList;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link FeedsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedsFragment extends Fragment{
    private OnFragmentInteractionListener mListener;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    SwipeRefreshLayout mSwipeRefreshLayout;
    CoordinatorLayout coordinatorLayout;
    private ArrayList<Post> mPosts;

    @BindView(R.id.displayEmpty)TextView displayEmpty;
    @BindString(R.string.start) String stringStart;
    @BindString(R.string.new_post)String stringNewPost;
    @BindString(R.string.postingas)String stringPostingAs;

    private ChildEventListener feedsListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            // This is triggered when we receive a post
            Post p = dataSnapshot.getValue(Post.class);

            if(p != null) {
                p.setKey(dataSnapshot.getKey());
                if(!mPosts.contains(p)) {
                    mPosts.add(p);
                    mAdapter.notifyItemInserted(mPosts.size() - 1);
                }
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            // This will be triggered when a post on the feed changes, e.g. it gets a new up/down vote
            Post p = dataSnapshot.getValue(Post.class);
            if(p != null) {
                p.setKey(dataSnapshot.getKey());
                if (mPosts.contains(p)) {
                    int idx = mPosts.indexOf(p);
                    if(idx > -1) {
                        mPosts.set(idx, p);
                        mAdapter.notifyItemChanged(idx);
                    }
                }
            }
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) { }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

        @Override
        public void onCancelled(DatabaseError databaseError) { }
    };
    private boolean mListening = false;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FeedFragment.
     */
    public static FeedsFragment newInstance() {
        FeedsFragment fragment = new FeedsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPosts = new ArrayList<>();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feeds, container, false);
        ButterKnife.bind(this, view);
        coordinatorLayout = view.findViewById(R.id.base);
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        mRecyclerView =  view.findViewById(R.id.recyclerView);

        FloatingActionButton fab =  view.findViewById(R.id.add_feeds);
        fab.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        showPostDialog();
                    }
                }
        );

        displayEmpty.setVisibility(View.GONE);
        mRecyclerView.setHasFixedSize(true);


        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new FeedsAdapter(getActivity(), mPosts, new FeedsAdapter.OnItemClickListener() {
            @Override
            public void onClick(View caller) {

            }
        }, true);
        mRecyclerView.setAdapter(mAdapter);
        startFeedListener();
        return view;
    }

    /**
     * Shows a dialog for writing a new post.
     */
    public void showPostDialog() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        System.out.println("fm: " + fm);
        SharedPreferences prefs = getActivity().getSharedPreferences("com.madonasyombua.growwithgoogleteamproject.ui.fragment", Context.MODE_PRIVATE);
        PostFeedFragment postDialog = PostFeedFragment.newInstance(stringNewPost, stringPostingAs,
                prefs.getString("username", ""), prefs.getString("name", ""));
        postDialog.show(fm, "fragment_post_dialog");
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mAdapter != null && mRecyclerView != null && !mListening) {
            startFeedListener();
        } else {
            Log.i("FeedsFragment", "Did not start feed listener, already listening: " + mListening);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        FirebaseDatabase.getInstance().getReference(Paths.FEED).removeEventListener(feedsListener);
        mListening = false;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

        //((AppCompatActivity)(context)).getSupportActionBar().setTitle(getString(R.string.feeds));

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void startFeedListener() {
        // Start listening for feed updates, feedsListener will look for changes.
        FirebaseDatabase.getInstance().getReference("feeds").addChildEventListener(feedsListener);
        mListening = true;
    }


}

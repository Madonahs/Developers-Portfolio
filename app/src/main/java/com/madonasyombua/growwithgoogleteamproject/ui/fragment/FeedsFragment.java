package com.madonasyombua.growwithgoogleteamproject.ui.fragment;
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
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.madonasyombua.growwithgoogleteamproject.R;
import com.madonasyombua.growwithgoogleteamproject.adapter.FeedsAdapter;
import com.madonasyombua.growwithgoogleteamproject.models.Post;

import java.util.ArrayList;

import butterknife.BindString;
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
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private CoordinatorLayout coordinatorLayout;
    private TextView displayEmpty;
    private ArrayList<Post> mPosts;
    @BindString(R.string.start) String stringStart;
    @BindString(R.string.new_post)String stringNewPost;
    @BindString(R.string.postingas)String stringPostingAs;

    public FeedsFragment() {
        // Required empty public constructor
    }

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

       // stringStart = getResources().getString(R.string.start);
       // stringNewPost = getResources().getString(R.string.new_post);
        //stringPostingAs = getResources().getString(R.string.posting_as);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feeds, container, false);
        ButterKnife.bind(this, view);
        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.base);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.add_feeds);
        fab.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        showPostDialog();
                    }
                }
        );

        displayEmpty = (TextView) view.findViewById(R.id.displayEmpty);
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
        updateFeed();
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

    /**
     * Updates the user's feed and populates the cards.
     */


    public void updateFeed() {
      //Update feeds

    }

    /**
     * Displays a snackbar.
     * @param text Text to display on the snackbar
     */


}

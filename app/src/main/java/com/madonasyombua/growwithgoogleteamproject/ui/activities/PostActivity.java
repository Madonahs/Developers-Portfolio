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

package com.madonasyombua.growwithgoogleteamproject.ui.activities;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import com.madonasyombua.growwithgoogleteamproject.R;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.PostFeedFragment;
import com.madonasyombua.growwithgoogleteamproject.data.models.Post;
import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindString;
import butterknife.ButterKnife;

public class PostActivity extends AppCompatActivity
        implements PostFeedFragment.OnFragmentInteractionListener {

    private static final String TAG = PostActivity.class.getName();
    @BindString(R.string.post) String stringPost;
    @BindString(R.string.deleted_post) String stringDeletedPost;
    @BindString(R.string.comment) String stringComment;
    @BindString(R.string.commenting_as) String  stringCommentingAs;
    @BindString(R.string.delete_post) String stringDeletePost;
    @BindString(R.string.yes) String stringYes;
    @BindString(R.string.cancel)String stringCancel;
    @BindString(R.string.deleted_comment)String stringDeletedComment;
    @BindString(R.string.delete_comment)String stringDeleteComment;
    @BindString(R.string.sent_comment)String stringSentComment;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feeds);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(stringPost);

        ArrayList<Post> mPost = new ArrayList<>();
        Post receivedPost = (Post) getIntent().getSerializableExtra("post");
        mPost.add(receivedPost);

        SwipeRefreshLayout mSwipeRefreshLayout = findViewById(R.id.swipeRefresh);
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent));
        mSwipeRefreshLayout.setOnRefreshListener(
                this::updatePost
        );

        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        /* use this setting to improve performance if you know that changes
        in content do not change the layout size of the RecyclerView*/
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    /**
     * Displays the comment dialog for submitting a comment.
     */
    public void showCommentDialog(View v) {

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     * update post
     */
    public void updatePost() {

        //update post

    }

    /**
     *
     * @param uri uri
     */
    @Override
    public void onFragmentInteraction(Uri uri) {
        //uri
        }

    @Override
    public void onDialogSubmit() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onDialogSubmit(PostFeedFragment dialog, String text, String fileName) {

    }
}


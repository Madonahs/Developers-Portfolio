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

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindString
import butterknife.ButterKnife
import com.madonasyombua.growwithgoogleteamproject.R
import com.madonasyombua.growwithgoogleteamproject.data.models.Post
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.PostFeedFragment
import java.util.*

class PostActivity : AppCompatActivity(), PostFeedFragment.OnFragmentInteractionListener {
    @JvmField
    @BindString(R.string.post)
    var stringPost: String? = null
    @JvmField
    @BindString(R.string.deleted_post)
    var stringDeletedPost: String? = null
    @JvmField
    @BindString(R.string.comment)
    var stringComment: String? = null
    @JvmField
    @BindString(R.string.commenting_as)
    var stringCommentingAs: String? = null
    @JvmField
    @BindString(R.string.delete_post)
    var stringDeletePost: String? = null
    @JvmField
    @BindString(R.string.yes)
    var stringYes: String? = null
    @JvmField
    @BindString(R.string.cancel)
    var stringCancel: String? = null
    @JvmField
    @BindString(R.string.deleted_comment)
    var stringDeletedComment: String? = null
    @JvmField
    @BindString(R.string.delete_comment)
    var stringDeleteComment: String? = null
    @JvmField
    @BindString(R.string.sent_comment)
    var stringSentComment: String? = null

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_feeds)
        ButterKnife.bind(this)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = stringPost
        val mPost = ArrayList<Post>()
        val receivedPost = intent.getSerializableExtra("post") as Post
        mPost.add(receivedPost)
        val mSwipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent))
        mSwipeRefreshLayout.setOnRefreshListener { updatePost() }
        val mRecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        /* use this setting to improve performance if you know that changes
        in content do not change the layout size of the RecyclerView*/mRecyclerView.setHasFixedSize(true)
        // use a linear layout manager
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        mRecyclerView.layoutManager = mLayoutManager
    }

    /**
     * Displays the comment dialog for submitting a comment.
     */
    fun showCommentDialog(v: View?) {}

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    /**
     * update post
     */
    fun updatePost() { //update post
    }

    /**
     *
     * @param uri uri
     */
    override fun onFragmentInteraction(uri: Uri) { //uri
    }

    override fun onDialogSubmit() {}
    override fun onItemClick(parent: AdapterView<*>?, view: View, position: Int, id: Long) {}
    override fun onDialogSubmit(dialog: PostFeedFragment, text: String, fileName: String) {}

    companion object {
        private val TAG = PostActivity::class.java.name
    }
}
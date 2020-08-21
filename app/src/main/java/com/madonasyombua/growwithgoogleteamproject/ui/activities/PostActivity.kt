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
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.madonasyombua.growwithgoogleteamproject.R
import com.madonasyombua.growwithgoogleteamproject.data.models.Post
import com.madonasyombua.growwithgoogleteamproject.databinding.ActivityAddFeedsBinding
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.PostFeedFragment
import java.util.*

class PostActivity : AppCompatActivity(), PostFeedFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAddFeedsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = R.string.post.toString()
        val mPost = ArrayList<Post>()
        val receivedPost = intent.getSerializableExtra("post") as Post
        mPost.add(receivedPost)
        binding.swipeRefresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent))
        binding.swipeRefresh.setOnRefreshListener { updatePost() }

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = mLayoutManager
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun updatePost() {
        //update post
    }

    override fun onFragmentInteraction(uri: Uri?) { //uri
    }

    override fun onDialogSubmit() {}
    override fun onDialogSubmit(dialog: PostFeedFragment?, text: String?, fileName: String?) {

    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {}

    companion object
}
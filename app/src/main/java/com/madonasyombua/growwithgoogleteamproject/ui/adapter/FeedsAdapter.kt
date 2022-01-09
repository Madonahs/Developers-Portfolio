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
 **/
package com.madonasyombua.growwithgoogleteamproject.ui.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.madonasyombua.growwithgoogleteamproject.R
import com.madonasyombua.growwithgoogleteamproject.data.models.Post
import com.madonasyombua.growwithgoogleteamproject.ui.activities.MainActivity.Companion.dpToPixels
import com.madonasyombua.growwithgoogleteamproject.ui.activities.PostActivity
import java.util.*

class FeedsAdapter(
    private var mActivity: Activity,
    private val mPosts: ArrayList<Post>,
    private val mListener: OnItemClickListener,
    private val mFromMainActivity: Boolean
) : RecyclerView.Adapter<FeedsAdapter.ViewHolder>() {
    /**
     * Create new views (invoked by the layout manager)
     *
     * @param parent   parent
     * @param viewType view type
     * @return adapter
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.feeds_list_item, parent, false)
        return ViewHolder(
            view,
            mPosts,
            object : OnItemClickListener {
                override fun onClick(caller: View?) {
                    mListener.onClick(caller)
                }
            },
            mFromMainActivity
        )
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     *
     * @param holder   holder
     * @param position position
     * Gets an object at the given position in the posts array and populates
     * text and image views.
     */
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = mPosts[position]
        holder.mName?.text = post.username
        holder.mPostProfilePicture?.setImageBitmap(null)
        holder.mPostProfilePicture?.setImageResource(R.drawable.avater)
        holder.mPosted?.text = post.posted
        holder.mText?.text = post.text
        holder.mNoComments?.text = "" + post.numberOfComments
        holder.mUpvotes?.text = "" + post.upvotes
        holder.mDownvotes?.text = "" + post.downvotes
        holder.mCardView?.tag = "closed"
        holder.mCardView?.cardElevation = dpToPixels(1, holder.mCardView!!)
        holder.mPostImageBorder?.visibility = View.GONE
        if (post.image != null) {
            val imageAsBytes = Base64.decode(post.image!!.toByteArray(), Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.size)
            holder.mPostImage?.setImageBitmap(bitmap)
            holder.mPostImageBorder!!.visibility = View.VISIBLE
            if (!mFromMainActivity) {
                holder.mPostImage?.setOnClickListener { v: View? ->
                    val fm = (Companion as PostActivity).supportFragmentManager
                }
            } else {
                val cardView = holder.mCardView
                holder.mPostImage?.setOnClickListener { v: View? -> cardView?.performClick() }
            }
        } else {
            holder.mPostImage?.setImageBitmap(null)
        }
        if (mFromMainActivity) holder.mCloseButton?.visibility = View.INVISIBLE
    }

    /**
     * Provide a reference to the views for each data item
     * Complex data items may need more than one view per item, and
     * we can provide access to all the views for a data item in a view holder
     */
    class ViewHolder internal constructor(
        itemView: View?,
        posts: ArrayList<Post>?,
        listener: FeedsAdapter.OnItemClickListener,
        fromMainActivity: Boolean
    ) : RecyclerView.ViewHolder(
        itemView!!
    ), View.OnClickListener {
        private val cardListener: View.OnClickListener? = null
        private val closeListener: View.OnClickListener? = null
        private val voteListener: View.OnClickListener? = null

        @BindView(R.id.cardView)
        var mCardView: CardView? = null

        @BindView(R.id.commentsSection)
        var mCommentsSection: LinearLayout? = null

        @BindView(R.id.postInfo)
        var mPostInfo: LinearLayout? = null

        @BindView(R.id.name)
        var mName: TextView? = null

        @BindView(R.id.time)
        var mPosted: TextView? = null

        @BindView(R.id.text)
        var mText: TextView? = null

        @BindView(R.id.comments)
        var mNoComments: TextView? = null

        @BindView(R.id.upvotes)
        var mUpvotes: TextView? = null

        @BindView(R.id.downvotes)
        var mDownvotes: TextView? = null

        @BindView(R.id.closeButton)
        var mCloseButton: ImageView? = null

        @BindView(R.id.deleteCommentButton)
        var mDeletePostButton: ImageView? = null

        @BindView(R.id.postProfilePicture)
        var mPostProfilePicture: ImageView? = null

        @BindView(R.id.upvote)
        var mUpvote: ImageView? = null

        @BindView(R.id.downvote)
        var mDownvote: ImageView? = null

        @BindView(R.id.postImage)
        var mPostImage: ImageView? = null

        @BindView(R.id.postImageBorder)
        var mPostImageBorder: LinearLayout? = null
        override fun onClick(v: View) {

        }

        /**
         * Onclick interface for custom behavior.
         */
        internal interface OnItemClickListener {
            fun onClick(caller: View?)
        }

        companion object {
            /**
             * Sets up listeners for the feeds_list_item, the close button and the vote buttons.
             */
            fun setUpListeners() {}
        }

        init {
            ButterKnife.bind(this, itemView!!)
            mPostImageBorder?.visibility = View.GONE
            setUpListeners()
            mUpvote?.setOnClickListener(voteListener)
            mUpvotes?.setOnClickListener(voteListener)
            mDownvote?.setOnClickListener(voteListener)
            mDownvotes?.setOnClickListener(voteListener)
            mPostImage?.setOnClickListener(this)
            if (fromMainActivity) {
                mCloseButton?.setOnClickListener(closeListener)
                mCardView?.setOnClickListener(cardListener)
            } else {
                mDeletePostButton?.setOnClickListener(this)
                mPostInfo?.setOnClickListener(this)
            }
        }
    }

    /**
     * @return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount(): Int {
        return mPosts.size
    }

    /**
     * Onclick interface for custom behavior.
     */
    interface OnItemClickListener {
        fun onClick(caller: View?)
    }

    companion object;
    init {
        val stringComment = mActivity.resources.getString(R.string.comment)
    }
}
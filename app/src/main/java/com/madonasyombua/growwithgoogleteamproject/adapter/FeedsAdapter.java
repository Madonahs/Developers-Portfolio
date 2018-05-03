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
package com.madonasyombua.growwithgoogleteamproject.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.madonasyombua.growwithgoogleteamproject.R;
import com.madonasyombua.growwithgoogleteamproject.activities.PostActivity;
import com.madonasyombua.growwithgoogleteamproject.activities.MainActivity;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.ImageDialog;
import com.madonasyombua.growwithgoogleteamproject.util.BitmapHandler;
import com.madonasyombua.growwithgoogleteamproject.models.Post;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by madon on 3/20/2018.
 */

public class FeedsAdapter extends RecyclerView.Adapter<FeedsAdapter.ViewHolder> {
    private ArrayList<Post> mPosts;
    private static Activity mActivity;
    private boolean mFromMainActivity;
    private OnItemClickListener mListener;
    private String stringComment;

    BitmapHandler bitmapHandler;

    public FeedsAdapter(Activity activity, ArrayList<Post> posts, OnItemClickListener listener, boolean fromMainActivity) {
        mActivity = activity;
        mPosts = posts;
        mListener = listener;
        mFromMainActivity = fromMainActivity;
        stringComment = activity.getResources().getString(R.string.comment);
    }

    /**
     * Create new views (invoked by the layout manager)
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public FeedsAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feeds_list_item, parent, false);
        ViewHolder vh = new ViewHolder(view, mPosts, new FeedsAdapter.ViewHolder.OnItemClickListener() {
            public void onClick(View caller) { mListener.onClick(caller); }
        }, mFromMainActivity);

        return vh;
    }

    /**
     *  Replace the contents of a view (invoked by the layout manager)
     * @param holder
     * @param position
     *  Gets an object at the given position in the posts array and populates
     *  text and image views.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Post post = mPosts.get(position);
        holder.mName.setText(post.getUsername());
        holder.mPostProfilePicture.setImageBitmap(null);
        holder.mPostProfilePicture.setImageResource(R.drawable.default_pic);
        holder.mPosted.setText(post.getPosted());
        holder.mText.setText(post.getText());
        holder.mNoComments.setText("" + post.getNumberOfComments());
        holder.mUpvotes.setText("" + post.getUpvotes());
        holder.mDownvotes.setText("" + post.getDownvotes());
        holder.mCardView.setTag("closed");
        holder.mCardView.setCardElevation(MainActivity.dpToPixels(1, holder.mCardView));

        holder.mPostImageBorder.setVisibility(View.GONE);
        if (post.getImage() != null) {
            byte[] imageAsBytes = Base64.decode(post.getImage().getBytes(), Base64.DEFAULT);
            final Bitmap bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
            holder.mPostImage.setImageBitmap(bitmap);
            holder.mPostImageBorder.setVisibility(View.VISIBLE);
            if (!mFromMainActivity) {
                holder.mPostImage.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FragmentManager fm = ((PostActivity) mActivity).getSupportFragmentManager();
                                ImageDialog imageDialog = ImageDialog.newInstance(bitmapHandler.getLarger(bitmap));
                                imageDialog.show(fm, "fragment_image_dialog");
                            }
                        }
                );
            } else {
                final CardView cardView = holder.mCardView;
                holder.mPostImage.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cardView.performClick();
                            }
                        }
                );
            }
        } else {
            holder.mPostImage.setImageBitmap(null);
        }

        if (mFromMainActivity)
            holder.mCloseButton.setVisibility(View.INVISIBLE);

    }
    /**
     * Provide a reference to the views for each data item
     * Complex data items may need more than one view per item, and
     * we can provide access to all the views for a data item in a view holder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private OnItemClickListener mListener;
        private View.OnClickListener cardListener;
        private View.OnClickListener closeListener;
        private View.OnClickListener voteListener;
        private ArrayList<Post> mPosts;
        private boolean mFromMainActivity;

        @BindView(R.id.cardView) CardView mCardView;
        @BindView(R.id.commentsSection)LinearLayout mCommentsSection;
        @BindView(R.id.postInfo) LinearLayout mPostInfo;
        @BindView(R.id.name)TextView mName;
        @BindView(R.id.time)TextView mPosted;
        @BindView(R.id.text)TextView mText;
        @BindView(R.id.comments)TextView mNoComments;
        @BindView(R.id.upvotes)TextView mUpvotes;
        @BindView(R.id.downvotes)TextView mDownvotes;
        @BindView(R.id.closeButton)ImageView mCloseButton;
        @BindView(R.id.deleteCommentButton)ImageView mDeletePostButton;
        @BindView(R.id.postProfilePicture)ImageView mPostProfilePicture;
        @BindView(R.id.upvote) ImageView mUpvote;
        @BindView(R.id.downvote)ImageView mDownvote;
        @BindView(R.id.postImage)ImageView mPostImage;
        @BindView(R.id.postImageBorder)LinearLayout  mPostImageBorder;


        public ViewHolder(View itemView, ArrayList<Post> posts, OnItemClickListener listener, boolean fromMainActivity) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mPosts = posts;
            mListener = listener;
            mFromMainActivity = fromMainActivity;
            mPostImageBorder.setVisibility(View.GONE);
            setUpListeners();

            mUpvote.setOnClickListener(voteListener);
            mUpvotes.setOnClickListener(voteListener);
            mDownvote.setOnClickListener(voteListener);
            mDownvotes.setOnClickListener(voteListener);
            mPostImage.setOnClickListener(this);

            if(mFromMainActivity){
                mCloseButton.setOnClickListener(closeListener);
                mCardView.setOnClickListener(cardListener);
            }else{
                mDeletePostButton.setOnClickListener(this);
                mPostInfo.setOnClickListener(this);
            }

        }

        /**
         * Sets up listeners for the feeds_list_item, the close button and the vote buttons.
         */
        public static void setUpListeners() {


        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v);
        }

        /**
         * Onclick interface for custom behavior.
         */
        public interface OnItemClickListener {
            void onClick(View caller);
        }
    }
    /**
     *
     * @return
     * the size of your dataset (invoked by the layout manager)
     */
    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    /**
     * Onclick interface for custom behavior.
     */
    public interface OnItemClickListener {
        void onClick(View caller);
    }

}
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
import com.madonasyombua.growwithgoogleteamproject.actvities.AddFeeds;
import com.madonasyombua.growwithgoogleteamproject.actvities.MainActivity;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.ImageDialog;
import com.madonasyombua.growwithgoogleteamproject.util.BitmapHandler;
import com.madonasyombua.growwithgoogleteamproject.models.Post;
import java.util.ArrayList;


/**
 * Created by madon on 3/20/2018.
 */

public class FeedsAdapter extends RecyclerView.Adapter<FeedsAdapter.ViewHolder> {
    private ArrayList<Post> mPosts;
    private static Activity mActivity;
    private boolean mFromMainActivity;
    private OnItemClickListener mListener;

    private String stringComment;

    private BitmapHandler bitmapHandler;

    public FeedsAdapter(Activity activity, ArrayList<Post> posts, OnItemClickListener listener, boolean fromMainActivity) {
        mActivity = activity;
        mPosts = posts;
        mListener = listener;
        mFromMainActivity = fromMainActivity;
       // bitmapHandler = new BitmapHandler();

        stringComment = activity.getResources().getString(R.string.comment);
    }
    // Create new views (invoked by the layout manager)
    @Override
    public FeedsAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feeds_list_item, parent, false);
        ViewHolder vh = new ViewHolder(view, mPosts, new FeedsAdapter.ViewHolder.OnItemClickListener() {
            public void onClick(View caller) { mListener.onClick(caller); }
        }, mFromMainActivity);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        // Gets an object at the given position in the posts array and populates
        // text and image views.

        Post post = mPosts.get(position);

        //holder.mUsername.setText(post.getUser().getUsername());
        holder.mName.setText(post.getUser().getName());
        if (post.getUser().getImage() != null) {
            byte[] imageAsBytes = Base64.decode(post.getUser().getImage().getBytes(), Base64.DEFAULT);
            Bitmap thumbnail = bitmapHandler.getThumbnail(
                    BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length)
            );
            holder.mPostProfilePicture.setImageBitmap(thumbnail);
        } else {
            holder.mPostProfilePicture.setImageBitmap(null);
            holder.mPostProfilePicture.setImageResource(R.drawable.default_pic);
        }
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
                                FragmentManager fm = ((AddFeeds) mActivity).getSupportFragmentManager();
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


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // we can provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CardView mCardView;
        public TextView mUsername, mName, mPosted, mText, mNoComments, mUpvotes, mDownvotes;
        public ImageView mCloseButton, mDeletePostButton, mDeleteCommentButton;
        public ImageView mPostProfilePicture, mUpvote, mDownvote, mPostImage;
        public LinearLayout mCommentsSection, mPostInfo, mPostImageBorder;

        private OnItemClickListener mListener;

        private View.OnClickListener cardListener;
        private View.OnClickListener closeListener;
        private View.OnClickListener voteListener;
       // private RequestQueue requestQueue;

        private ArrayList<Post> mPosts;
        private boolean mFromMainActivity;

        public ViewHolder(View view, ArrayList<Post> posts, OnItemClickListener listener, boolean fromMainActivity) {
            super(view);

            mPosts = posts;
            mListener = listener;
            mFromMainActivity = fromMainActivity;

            mCardView = (CardView) view;
           // mUsername = (TextView) view.findViewById(R.id.username);
            mName = (TextView) view.findViewById(R.id.name);
            mPostProfilePicture = (ImageView) view.findViewById(R.id.postProfilePicture);
            mPosted = (TextView) view.findViewById(R.id.time);
            mText = (TextView) view.findViewById(R.id.text);
            mPostImage = (ImageView) view.findViewById(R.id.postImage);
            mPostImageBorder = (LinearLayout) view.findViewById(R.id.postImageBorder);
            mNoComments = (TextView) view.findViewById(R.id.comments);
            mUpvote = (ImageView) view.findViewById(R.id.upvote);
            mUpvotes = (TextView) view.findViewById(R.id.upvotes);
            mDownvote = (ImageView) view.findViewById(R.id.downvote);
            mDownvotes = (TextView) view.findViewById(R.id.downvotes);
            mCommentsSection = (LinearLayout) view.findViewById(R.id.commentsSection);

            mPostImageBorder.setVisibility(View.GONE);


            setUpListeners();

            mUpvote.setOnClickListener(voteListener);
            mUpvotes.setOnClickListener(voteListener);
            mDownvote.setOnClickListener(voteListener);
            mDownvotes.setOnClickListener(voteListener);

            mPostImage.setOnClickListener(this);

            if (mFromMainActivity) {
                mCloseButton = (ImageView) view.findViewById(R.id.closeButton);
                mCloseButton.setOnClickListener(closeListener);

                mCardView.setOnClickListener(cardListener);
            }
            else {
                mDeletePostButton = (ImageView) view.findViewById(R.id.deleteCommentButton);
                mDeletePostButton.setOnClickListener(this);

                mPostInfo = (LinearLayout) view.findViewById(R.id.postInfo);
                mPostInfo.setOnClickListener(this);
            }
        }

        /**
         * Sets up listeners for the feeds_list_item, the close button and the vote buttons.
         */
        public void setUpListeners() {


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

    // Return the size of your dataset (invoked by the layout manager)
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

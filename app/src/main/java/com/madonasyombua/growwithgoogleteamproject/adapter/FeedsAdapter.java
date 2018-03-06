package com.madonasyombua.growwithgoogleteamproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.madonasyombua.growwithgoogleteamproject.R;
import com.madonasyombua.growwithgoogleteamproject.models.Feeds;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ayo on 3/4/2018.
 */

public class FeedsAdapter extends RecyclerView.Adapter<FeedsAdapter.ViewHolder> {

    List<Feeds> feedsList;

    public FeedsAdapter(List<Feeds> feedsList){
        this.feedsList = feedsList;
    }

    @Override
    public FeedsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feeds_list_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FeedsAdapter.ViewHolder holder, int position) {
        holder.feed_title.setText(feedsList.get(position).getFeed_name());
        holder.img_feeds.setImageResource(feedsList.get(position).getFeed_images());
    }

    @Override
    public int getItemCount() {
        return feedsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.img_feeds) ImageView img_feeds;

        @BindView(R.id.tv_feed_title)  TextView feed_title;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setFeedsList(List<Feeds> feeds){ this.feedsList = feeds; }
}

package com.madonasyombua.growwithgoogleteamproject.adapter;

/**
 * Created by jantz on 2/18/2018.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.madonasyombua.growwithgoogleteamproject.R;
import com.madonasyombua.growwithgoogleteamproject.models.Portfolio;

import java.util.List;

public class PortfolioAdapter extends RecyclerView.Adapter<PortfolioAdapter.MyViewHolder> {

    private List<Portfolio> portfolioList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, longDescription, shortDescription;
        public ImageView portfolioImage;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            shortDescription = (TextView) view.findViewById(R.id.shortDescription);
            longDescription = (TextView) view.findViewById(R.id.longDescription);
            portfolioImage = (ImageView) view.findViewById(R.id.portfolio_image);

        }
    }


    public PortfolioAdapter(List<Portfolio> portfolioList) {
        this.portfolioList = portfolioList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.portfolio_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Portfolio portfolio = portfolioList.get(position);
        holder.title.setText(portfolio.getTitle());
        holder.shortDescription.setText(portfolio.getShortDescription());
        holder.longDescription.setText(portfolio.getLongDescription());
        holder.portfolioImage.setImageResource(portfolio.getPortfolioImage());
    }

    @Override
    public int getItemCount() {
        return portfolioList.size();
    }
}

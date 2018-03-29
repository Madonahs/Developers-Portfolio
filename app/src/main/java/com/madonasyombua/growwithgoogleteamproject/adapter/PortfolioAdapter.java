package com.madonasyombua.growwithgoogleteamproject.adapter;
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

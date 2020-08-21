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
package com.madonasyombua.growwithgoogleteamproject.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.madonasyombua.growwithgoogleteamproject.R
import com.madonasyombua.growwithgoogleteamproject.data.models.Portfolio
import com.madonasyombua.growwithgoogleteamproject.databinding.PortfolioListRowBinding
import com.madonasyombua.growwithgoogleteamproject.ui.adapter.PortfolioAdapter.MyViewHolder

class PortfolioAdapter(private val portfolioList: List<Portfolio>) : RecyclerView.Adapter<MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = PortfolioListRowBinding.bind(view)
        fun bindTo(portfolio: Portfolio) {
            binding.title.text = portfolio.title
            binding.shortDescription.text = portfolio.shortDescription
            binding.longDescription.text = portfolio.longDescription
            binding.portfolioImage.setImageResource(portfolio.portfolioImage)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.portfolio_list_row, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val portfolio = portfolioList[position]
        holder.bindTo(portfolio)
    }

    override fun getItemCount(): Int {
        return portfolioList.size
    }
}
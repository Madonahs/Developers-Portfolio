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

package com.madonasyombua.growwithgoogleteamproject.data.models;


/**
 * Created by jantz on 2/18/2018.
 */

public class Portfolio {
    private String title, shortDescription, longDescription;
    private int portfolioImage;

    public Portfolio() {
    }

    public Portfolio(String title, String shortDescription, String longDescription, int portfolioImage) {
        this.title = title;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.portfolioImage = portfolioImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public int getPortfolioImage() {
        return portfolioImage;
    }

    public void setPortfolioImage(int portfolioImage) {
        this.portfolioImage = portfolioImage;
    }

}
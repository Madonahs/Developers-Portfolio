package com.madonasyombua.growwithgoogleteamproject.models;

/**
 * Created by Ayo on 3/4/2018.
 */

public class Feeds {
    // TODO: 3/4/2018 Make a short Description editText
    private String feed_name, feed_description;
    private int feed_images;

    public Feeds(String feed_name, String feed_description, int feed_images) {
        this.feed_name = feed_name;
        this.feed_description = feed_description;
        this.feed_images = feed_images;
    }

    public Feeds(String feed_name, int feed_images) {
        this.feed_name = feed_name;
        this.feed_images = feed_images;
    }

    public String getFeed_name() {
        return feed_name;
    }

    public void setFeed_name(String feed_name) {
        this.feed_name = feed_name;
    }

    public String getFeed_description() {
        return feed_description;
    }

    public void setFeed_description(String feed_description) {
        this.feed_description = feed_description;
    }

    public int getFeed_images() {
        return feed_images;
    }

    public void setFeed_images(int feed_images) {
        this.feed_images = feed_images;
    }
}

package com.madonasyombua.growwithgoogleteamproject.util;

import com.madonasyombua.growwithgoogleteamproject.models.User;
import java.io.Serializable;

/**
 * Created by madon on 3/20/2018.
 */

public class Comment implements Serializable {

    int cid;
    private User user;
    private String text, commented, image;

    public Comment(int cid, User user, String text, String commented, String image) {
        this.cid = cid;
        this.user = user;
        this.text = text;
        this.commented = commented;
        this.image = image;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCommented(String posted) {
        this.commented = commented;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCid() {
        return cid;
    }

    public User getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public String getCommented() {
        return commented;
    }

    public String getImage() {
        return image;
    }
}

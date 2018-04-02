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
package com.madonasyombua.growwithgoogleteamproject.models;

import com.google.firebase.database.Exclude;
import com.madonasyombua.growwithgoogleteamproject.util.Comment;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by madon on 3/20/2018.
 */

public class Post  extends FirebaseObject implements Serializable {

    int pid, numberOfComments, upvotes, downvotes, voted;
    private String text, posted, image, username;
   private ArrayList<Comment> comments;

   public Post() {}

    public Post(int pid, String text, String username, String posted, int numberOfComments,
                ArrayList<Comment> comments, int upvotes, int downvotes, int voted, String image) {
        this.pid = pid;
        this.text = text;
        this.posted = posted;
        this.numberOfComments = numberOfComments;
        this.comments = comments;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.voted = voted;
        this.image = image;
        this.username = username;
    }
    public Post(String post, String name, String image) {
        this.text = post;
        this.username = name;
        this.image = image;
    }
    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPosted(String posted) {
        this.posted = posted;
    }

    public void setNumberOfComments(int numberOfComments) {
        this.numberOfComments = numberOfComments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }

    public void setVoted(int voted) {
        this.voted = voted;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPid() {
        return pid;
    }

    public String getText() {
        return text;
    }

    public String getPosted() {
        return posted;
    }

    public int getNumberOfComments() {
        return numberOfComments;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public int getVoted() {
        return voted;
    }

    public String getImage() {
        return image;
    }


    @Exclude
    @Override
    public String getPath() {
        return getBase() + getKey();
    }

    @Exclude
    @Override
    public String getBase() {
        return Paths.FEED + Paths.SL;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}


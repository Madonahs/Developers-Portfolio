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
package com.madonasyombua.growwithgoogleteamproject.util;

import com.madonasyombua.growwithgoogleteamproject.models.User;

import java.io.Serializable;

/**
 * Created by madon on 3/10/2018.
 */
/**
 *A  user submitted comment
 *
 * */
public class UserComment implements Serializable {
    int cos;
    private User user;
    private String text, commented, image;

    public UserComment(int cos, User user, String text, String commented, String image) {
        this.cos = cos;
        this.user = user;
        this.text = text;
        this.commented = commented;
        this.image = image;
    }

    public void setCos(int cid) {
        this.cos = cid;
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

    public int getcos() {
        return cos;
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


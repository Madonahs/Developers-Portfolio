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

import android.os.Bundle;
import android.support.annotation.NonNull;
import com.google.firebase.database.Exclude;
import com.madonasyombua.growwithgoogleteamproject.data.db.FirebaseObject;
import com.madonasyombua.growwithgoogleteamproject.util.Constant;
import java.io.Serializable;


/*
  Created by chuk on 2/15/18.
 */

/**
 * {@link User}
 * A blueprint for user
 */

public class User extends FirebaseObject implements Serializable {
    private String name, password, followers, following, projects,
            email, location, phone, website, intro, image;


    public enum Status {
        ONLINE,
        OFFLINE
    }

    private Status status;

    // Empty constructor
    private User() {
    }


    // Must override and exclude or it will be written to firebase!
    @Exclude
    @Override
    public String getPath() {
        return getBase() + getKey();
    }

    // Must override and exclude or it will be written to firebase!
    @Exclude
    @Override
    public String getBase() {
        return Paths.USER + Paths.SL;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void setPortfolio(Portfolio portfolio) {
    }

    public static User build(@NonNull Bundle data) {
        User user = new User();
        user.setLocation(data.getString(Constant.LOCATION));
        user.setPhone(data.getString(Constant.PHONE));
        user.setEmail(data.getString(Constant.EMAIL));
        user.setWebsite(data.getString(Constant.WEB));
        user.setIntro(data.getString(Constant.INTRO));
        user.setStatus(data.getBoolean(Constant.STATUS));
        return user;
    }


    public static User build(@NonNull User user, @NonNull Bundle data) {
        user.setLocation(data.getString(Constant.LOCATION));
        user.setPhone(data.getString(Constant.PHONE));
        user.setEmail(data.getString(Constant.EMAIL));
        user.setWebsite(data.getString(Constant.WEB));
        user.setIntro(data.getString(Constant.INTRO));
        user.setStatus(data.getBoolean(Constant.STATUS));
        return user;
    }
    public Bundle bundleUp() {
        Bundle bundle = new Bundle(11/*Eleven fields*/);
        bundle.putString(Constant.INTRO, intro);
        bundle.putString(Constant.WEB, website);
        bundle.putString(Constant.EMAIL, email);
        bundle.putString(Constant.PHONE, phone);
        bundle.putString(Constant.LOCATION, location);
        bundle.putString(Constant.NAME, name);
        bundle.putBoolean(Constant.STATUS,status == Status.ONLINE);
        return bundle;
    }
    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public String getFollowers() {
        return followers;
    }

    public String getFollowing() {
        return following;
    }

    public String getProjects() {
        return projects;
    }

    public String getEmail() {
        return email;
    }

    public String getLocation() {
        return location;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    public String getIntro() {
        return intro;
    }

    public Status getStatus() {
        return status;
    }

    private void setLocation(String location) {
        if (location != null && !location.isEmpty()) this.location = location;
    }

    private void setEmail(String email) {
        if (email != null && !email.isEmpty()) this.email = email;
    }

    public void setPhone(String phone) {
        if (phone != null && !phone.isEmpty()) this.phone = phone;
    }

    private void setWebsite(String website) {
        if (website != null && !website.isEmpty()) this.website = website;
    }

    private void setIntro(String intro) {
        if (intro != null && !intro.isEmpty()) this.intro = intro;
    }

    public void setStatus(boolean status) {
        this.status = status ? Status.ONLINE:Status.OFFLINE;
    }

    public String getImage() {
        return image;
    }
}
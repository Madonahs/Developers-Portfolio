package com.madonasyombua.growwithgoogleteamproject.models;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.firebase.database.Exclude;
import com.madonasyombua.growwithgoogleteamproject.util.Constant;

import java.io.Serializable;


/**
 * Created by chuk on 2/15/18.
 */

/**
 * {@link User}
 * A blueprint for user
 */

public class User extends FirebaseObject implements Serializable {
    private String name, password, followers, following, projects,
            email, location, phone, website, intro,image;
    private Portfolio portfolio;
    private boolean status;

    // Empty constructor
    public User() {}


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

    public void setPortfolio(Portfolio portfolio){
        this.portfolio = portfolio;
    }

    public static User build(@NonNull Bundle data) {
        User user = new User();
        user.setLocation(data.getString(Constant.LOCATION));
        user.setPhone(data.getString(Constant.PHONE));
        user.setEmail(data.getString(Constant.EMAIL));

        user.setWebsite(data.getString(Constant.WEB));
        user.setIntro(data.getString(Constant.INTRO));
        return user;
    }


    public static User build(@NonNull User user,@NonNull Bundle data) {
        user.setLocation(data.getString(Constant.LOCATION));
        user.setPhone(data.getString(Constant.PHONE));
        user.setEmail(data.getString(Constant.EMAIL));

        user.setWebsite(data.getString(Constant.WEB));
        user.setIntro(data.getString(Constant.INTRO));
        return user;
    }

    public String getName() {
        return this.name;
    }
    public String getPassword() { return this.password;}

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

    public boolean getStatus() {
        return status;
    }

    public void setLocation(String location) {
        if (!location.isEmpty()) this.location = location;
    }

    public void setEmail(String email) {
        if (!email.isEmpty())this.email = email;
    }

    public void setPhone(String phone) {
        if (!phone.isEmpty()) this.phone = phone;
    }

    public void setWebsite(String website) {
        if (!website.isEmpty()) this.website = website;
    }

    public void setIntro(String intro) {
        if (!intro.isEmpty()) this.intro = intro;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }
}

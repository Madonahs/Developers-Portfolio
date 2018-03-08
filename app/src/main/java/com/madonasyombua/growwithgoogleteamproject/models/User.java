package com.madonasyombua.growwithgoogleteamproject.models;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.madonasyombua.growwithgoogleteamproject.util.Constant;

/**
 * Created by chuk on 2/15/18.
 */

/**
 * {@link User}
 * A blueprint for user
 */
public class User {
    private String name, password, followers, following, projects,
            email, location, phone, website, intro;
    private Portfolio portfolio;
    private boolean status;


    //TODO: Remove default values and
    public User() {
        this.name = "Cleverchuk";

        this.followers = "27K";
        this.intro = "My intro, everything special";
        this.location = "Death Valley";
        this.phone = "000-000-0000";
        this.website = "cleverchuk.github.io";
        this.email = "email@email.com";
        this.following = "425";
        this.projects = "434";
        this.status = true;

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
        if (!phone.isEmpty()) this.phone = "("+phone.substring(0,3)+")-"+phone.substring(3,6)+
                "-"+phone.substring(6,10);
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
}

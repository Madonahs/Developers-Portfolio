package com.madonasyombua.growwithgoogleteamproject.models;

/**
 * Created by chuk on 2/15/18.
 */

/*
    Just a dummy model test the fragment
 */
public class User {
    private String name, followers, following, projects;
    private String email, location, phone, website, intro;
    private String status;


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
        this.status = "Online";

    }

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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

    public String getStatus() {
        return status;
    }
}

package com.example.instacalcid1;

public class createnew_user {

    String name,name_caps,email,password,phone,profileurl,description,followers_count,following_count,totalpost_count,notification_count;

    public createnew_user(String name, String email, String password, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone ;
        this.description = "" ;
        this.profileurl = "" ;
        this.followers_count = "0";
        this.following_count =  "0";
        this.totalpost_count = "0";
        this.notification_count = "0";
        this.name_caps = name.toUpperCase();

    }

    public createnew_user() {
    }

    public createnew_user(String name, String name_caps, String email, String password, String phone, String profileurl, String description, String followers_count, String following_count, String totalpost_count, String notification_count) {
        this.name = name;
        this.name_caps = name_caps ;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.profileurl = profileurl;
        this.description = description;
        this.followers_count = followers_count;
        this.following_count = following_count;
        this.totalpost_count = totalpost_count;
        this.notification_count = notification_count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfileurl() {
        return profileurl;
    }

    public void setProfileurl(String profileurl) {
        this.profileurl = profileurl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFollowers_count() {
        return followers_count;
    }

    public void setFollowers_count(String followers_count) {
        this.followers_count = followers_count;
    }

    public String getFollowing_count() {
        return following_count;
    }

    public void setFollowing_count(String following_count) {
        this.following_count = following_count;
    }

    public String getName_caps() {
        return name_caps;
    }

    public void setName_caps(String NAME) {
        this.name_caps = name_caps;
    }

    public String getTotalpost_count() {
        return totalpost_count;
    }

    public void setTotalpost_count(String totalpost_count) {
        this.totalpost_count = totalpost_count;
    }
    public String getNotification_count() {
        return notification_count;
    }

    public void setNotification_count(String notification_count) {
        this.notification_count = notification_count;
    }
}



package com.example.instacalcid1;

import java.text.DateFormat;
import java.util.Calendar;

public class createnew_post {

    String userId;
    String likes_count;
    String comments_count;
    String post_count;
    String timestamp;
    String user_name;
    String imageurl;
    String caption ;


    public createnew_post(String caption, String POST_COUNT) {
        this.userId = loggined_user_details.getPhone();
        this.post_count = POST_COUNT;
        this.caption = caption ;
        this.imageurl = "";
        this.timestamp = DateFormat.getDateInstance().format(Calendar.getInstance().getTime());
        this.comments_count = "0";
        this.likes_count = "0";
        this.user_name = loggined_user_details.getName();

    }

    public createnew_post() {
    }

    public createnew_post(String userId, String likes_count, String comments_count, String post_count, String timestamp, String imageurl, String user_name, String caption) {
        this.userId = userId;
        this.likes_count = likes_count;
        this.comments_count = comments_count;
        this.post_count = post_count;
        this.timestamp = timestamp;
        this.imageurl = imageurl;
        this.user_name = user_name;
        this.caption = caption;
    }


    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }


    public void setTime_stamp(String time_stamp) {
        this.timestamp = time_stamp;
    }



    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }



    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }




    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(String likes_count) {
        this.likes_count = likes_count;
    }

    public String getComments_count() {
        return comments_count;
    }

    public void setComments_count(String comments_count) {
        this.comments_count = comments_count;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

//    public void setImage_url(String image_url) {
//
//        public String getImage_url() {
//        return image_url;
//    }
//    this.image_url = image_url;
//    }

    public String getPost_count() {
        return post_count;
    }

    public void setPost_count(String post_count) {
        this.post_count = post_count;
    }
}

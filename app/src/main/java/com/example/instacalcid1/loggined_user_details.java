package com.example.instacalcid1;

import android.app.Application;



import java.util.ArrayList;
import java.util.List;

public class loggined_user_details extends Application {


    private static int totalpost_count = 0;
    private static String following_count = "";
    private static String followers_count = "";
    private static  String notification_count = "0" ;
    private static int islogined = 0;
    private static String name = "" ;
    private static String email = "" ;
    private static String description = "" ;
    private static String phone = "" ;

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        loggined_user_details.name = name;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        loggined_user_details.email = email;
    }

    public static String getDescription() {
        return description;
    }

    public static void setDescription(String description) {
        loggined_user_details.description = description;
    }

    public static String getPhone() {
        return phone;
    }

    public static void setPhone(String phone) {
        loggined_user_details.phone = phone;
    }

    public loggined_user_details() {
        //fill_post_search_result();
      //  fill_user_data();
    }



    public static int getTotalpost_count() {
        return totalpost_count;
    }

    public static void setTotalpost_count(String totalpost_count) {
        loggined_user_details.totalpost_count = Integer.valueOf( totalpost_count);
    }

    public static String getFollowing_count() {
        return following_count;
    }

    public static void setFollowing_count(String following_count) {
        loggined_user_details.following_count = following_count;
    }

    public static String getFollowers_count() {
        return followers_count;
    }

    public static void setFollowers_count(String followers_count) {
        loggined_user_details.followers_count = followers_count;
    }

    public static String getNotification_count() {
        return notification_count;
    }

    public static void setNotification_count(String notification_count) {
        loggined_user_details.notification_count = notification_count;
    }

    public static int getIslogined() {
        return islogined;
    }


  //  public static List<post> getPost_list() {
    //    return post_list;
  //  }

//    public static void setPost_list(List<post> post_list) {
//        loggined_user_details.post_list = post_list;
//    }
//
//    public static List<user> getUserList() {
//        return userList;
//    }
//
//    public static void setUserList(List<user> userList) {
//        loggined_user_details.userList = userList;
//    }

    public static int getTotal_post() {
        return totalpost_count;
    }

    public static void setTotal_post(int total_post) {
        loggined_user_details.totalpost_count = total_post;
    }

    public static String getNextPostID() {
        return String.valueOf(totalpost_count + 1);
    }

    public static void setTotal_postID(int nextPostID) {
        loggined_user_details.totalpost_count = nextPostID;
    }
    public static int isIslogined() {
        return islogined;
    }

    public static void setIslogined(int islogined) {
        loggined_user_details.islogined = islogined;
    }
}

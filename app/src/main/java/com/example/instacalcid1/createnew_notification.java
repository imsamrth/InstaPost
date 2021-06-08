package com.example.instacalcid1;

public class createnew_notification {

    String post_id ;
    String user ;
    String notification ;

    public createnew_notification() {
    }

    public createnew_notification(String post_id, String user, String notification) {

        this.post_id = post_id;
        this.user = user;
        this.notification = notification;
    }

    createnew_notification(int action_code , String post_count, String user_count){


        if(action_code== 1){
            this.notification = loggined_user_details.getName() + " liked your photo" ;
            this.post_id = user_count+"p"+post_count ;
            this.user = loggined_user_details.getPhone();}


            else  if(action_code==2){
            this.notification = user + " commented on your photo";
            this.post_id = post_count ;
                this.user = loggined_user_details.getPhone();}


           else{
                this.notification = user +" started following you" ;
            this.post_id = user_count+"p"+post_count ;
                this.user = loggined_user_details.getPhone();}

    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }



}

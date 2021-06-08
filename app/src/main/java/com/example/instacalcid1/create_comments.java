package com.example.instacalcid1;

public class create_comments {
    String comments,userId ;
    create_comments(String comments){
        this.comments = loggined_user_details.getName()+" : " + comments ;
        this.userId = loggined_user_details.getPhone();
    }

    public create_comments() {
    }

    public create_comments(String comments, String userId) {
        this.comments = comments;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
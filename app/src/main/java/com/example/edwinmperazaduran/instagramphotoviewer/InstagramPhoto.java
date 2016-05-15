package com.example.edwinmperazaduran.instagramphotoviewer;

import java.util.ArrayList;
public class InstagramPhoto {
    public String username;
    public String caption;
    public String imageUrl;
    public int imageHeight;
    public long createdTime;
    public int likesCount;
    public int commentCount;
    public ArrayList<InstagramComment> comments;

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public ArrayList<InstagramComment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<InstagramComment> comments) {
        this.comments = comments;
    }

    public InstagramPhoto (){
        comments = new ArrayList<>();
    }

    public String getProfileUrl() { return profileUrl; }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String profileUrl;

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }
}

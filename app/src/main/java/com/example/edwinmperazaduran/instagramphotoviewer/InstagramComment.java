package com.example.edwinmperazaduran.instagramphotoviewer;

/**
 * Created by edwinmperazaduran on 19/9/15.
 */
public class InstagramComment {

    public String text;
    public String commentUserName;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCommentUserName() {
        return commentUserName;
    }

    public void setCommentUserName(String commentUserName) {
        this.commentUserName = commentUserName;
    }
}

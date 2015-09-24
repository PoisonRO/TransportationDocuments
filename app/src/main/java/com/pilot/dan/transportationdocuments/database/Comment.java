package com.pilot.dan.transportationdocuments.database;

/**
 * Created by dan on 9/22/15.
 */
public class Comment {

    private long comment_id;
    private String comment;

    public long getComment_id() {
        return comment_id;
    }

    public void setComment_id(long comment_id) {
        this.comment_id = comment_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

package com.pilot.dan.transportationdocuments.database;

import java.util.Date;

/**
 * Created by dan on 9/21/15.
 */

public class LoadHeader {

    public final static String STATUS_CURRENT           = "C";
    public final static String STATUS_UPLOADED          = "DU";
    public final static String STATUS_NOT_UPLOADED      = "DN";

    private long    _id;
    private String  desc;
    private String  file;
    private Date    date;
    private String status;


    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}

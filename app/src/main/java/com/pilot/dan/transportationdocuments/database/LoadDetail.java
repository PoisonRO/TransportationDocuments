package com.pilot.dan.transportationdocuments.database;

/**
 * Created by dan on 9/21/15.
 */

public class LoadDetail {

    public final static String STATUS_WAITING             ="W";
    public final static String STATUS_DELIVERY_OK         ="D";
    public final static String STATUS_CURERNT_DELIVERY    ="C";
    public final static String STATUS_NOT_DELIVERED       ="N";
    public final static String STATUS_DELIVERED_NO_DOCS   ="D1";


    private long        _id;
    private long        _line;
    private String      status;
    private String      destination;
    private String      customer;
    private String      item;
    private long _comment_id;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public long get_line() {
        return _line;
    }

    public void set_line(long _line) {
        this._line = _line;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public long get_comment_id() {
        return _comment_id;
    }

    public void set_comment_id(long _comment_id) {
        this._comment_id = _comment_id;
    }
}

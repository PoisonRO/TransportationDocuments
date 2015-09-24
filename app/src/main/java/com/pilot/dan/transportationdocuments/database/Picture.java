package com.pilot.dan.transportationdocuments.database;

/**
 * Created by dan on 9/23/15.
 */
public class Picture {

    private long        _load_id;
    private long        _load_line_id;
    private long        _pic_id;
    private String      pic_location;

    public long get_load_id() {
        return _load_id;
    }

    public void set_load_id(long _load_id) {
        this._load_id = _load_id;
    }

    public long get_pic_id() {
        return _pic_id;
    }

    public void set_pic_id(long _pic_id) {
        this._pic_id = _pic_id;
    }

    public String getPic_location() {
        return pic_location;
    }

    public void setPic_location(String pic_location) {
        this.pic_location = pic_location;
    }

    public long get_load_line_id() {
        return _load_line_id;
    }

    public void set_load_line_id(long _load_line_id) {
        this._load_line_id = _load_line_id;
    }
}

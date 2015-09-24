package com.pilot.dan.transportationdocuments.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.pilot.dan.transportationdocuments.database.MySQLiteHelper;
import com.pilot.dan.transportationdocuments.database.Picture;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dan on 9/23/15.
 */

public class PictureDAO extends TemplateDAO{

    private String[] allCollumns = {
            MySQLiteHelper.COLLUMN_PICTURE_LOAD_ID,
            MySQLiteHelper.COLLUMN_PICTURE_LOAD_LINE,
            MySQLiteHelper.COLLUMN_PICTURE_ID,
            MySQLiteHelper.COLLUMN_PICTURE
    };

    Context context;

    public PictureDAO(Context context) {
        super(context);
        this.context = context;
    }

    public List<Picture> getDeliveryPictures(long _load_id,long _load_line) {

        ArrayList<Picture> list = new ArrayList<Picture>();

        String whereClause =    MySQLiteHelper.COLLUMN_PICTURE_LOAD_ID+" = '"+_load_id+"' and "+
                                MySQLiteHelper.COLLUMN_PICTURE_LOAD_LINE+" = '"+_load_line+"'";

        Cursor cursor = database.query(MySQLiteHelper.TABLE_PICTURES,allCollumns,whereClause,null,null,null,null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            list.add(cursorToComment(cursor));
            cursor.moveToNext();
        }

        cursor.close();

        return list;
    }

    public Picture addNew(long _load_id,long _load_line,String location) throws SQLException {

        Picture newPic = new Picture();

        NextNumberDAO nn = new NextNumberDAO(context);

        long _line_id = nn.getNextNumber(NextNumberDAO.NEXT_NUMER_PIC_ID);

        newPic.set_load_id(_load_id);
        newPic.set_load_line_id(_load_line);
        newPic.set_pic_id(_line_id);
        newPic.setPic_location(location+"pic_"+_load_id+"_"+_load_line+"_"+_line_id+".png");

        ContentValues values = new ContentValues();

        values.put(MySQLiteHelper.COLLUMN_PICTURE_LOAD_ID, newPic.get_load_id());
        values.put(MySQLiteHelper.COLLUMN_PICTURE_LOAD_LINE, newPic.get_load_line_id());
        values.put(MySQLiteHelper.COLLUMN_PICTURE_ID, newPic.get_pic_id());
        values.put(MySQLiteHelper.COLLUMN_PICTURE, newPic.getPic_location());

        database.insertOrThrow(MySQLiteHelper.TABLE_PICTURES, null, values);

        return newPic;

    }

    private Picture cursorToComment(Cursor cursor) {

        Picture ret = new Picture();

        ret.set_load_id(cursor.getInt(0));
        ret.set_load_line_id(cursor.getInt(1));
        ret.set_pic_id(cursor.getInt(2));
        ret.setPic_location(cursor.getString(3));

        return ret;
    }

}

package com.pilot.dan.transportationdocuments.database.dao;

/*
                  o
                  |
                ,'~'.
               /     \
              |   ____|_
              |  '___,,_'         .----------------.
              |  ||(o |o)|       ( KILL ALL HUMANS! )
              |   -------         ,----------------'
              |  _____|         -'
              \  '####,
               -------
             /________\
           (  )        |)
           '_ ' ,------|\         _
          /_ /  |      |_\        ||
         /_ /|  |     o| _\      _||
        /_ / |  |      |\ _\____//' |
       (  (  |  |      | (_,_,_,____/
        \ _\ |   ------|
         \ _\|_________|
          \ _\ \__\\__\
          |__| |__||__|
       ||/__/  |__||__|
               |__||__|
               |__||__|
               /__)/__)
              /__//__/
             /__//__/
            /__//__/.
          .'    '.   '.
         (_kOs____)____)

*/

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.pilot.dan.transportationdocuments.database.LoadDetail;
import com.pilot.dan.transportationdocuments.database.MySQLiteHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dan on 9/21/15.
 */

public class LoadDetailDAO extends TemplateDAO {

    private String[] allCollumns = {MySQLiteHelper.COLLUMN_LOAD_DETAIL_ID,
                                    MySQLiteHelper.COLLUMN_LOAD_DETAIL_LINE,
                                    MySQLiteHelper.COLLUMN_LOAD_DETAIL_STATUS,
                                    MySQLiteHelper.COLLUMN_LOAD_DETAIL_DEST,
                                    MySQLiteHelper.COLLUMN_LOAD_DETAIL_CUST,
                                    MySQLiteHelper.COLLUMN_LOAD_DETAIL_ITEM,
                                    MySQLiteHelper.COLLUMN_LOAD_DETAIL_COMMENT_ID};

    public LoadDetailDAO(Context context) {
        super(context);
    }

    public void UpdateLoadLine(LoadDetail data) {

        ContentValues values = new ContentValues();

        values.put(MySQLiteHelper.COLLUMN_LOAD_DETAIL_ID,data.get_id());
        values.put(MySQLiteHelper.COLLUMN_LOAD_DETAIL_LINE, data.get_line());
        values.put(MySQLiteHelper.COLLUMN_LOAD_DETAIL_STATUS, data.getStatus());
        values.put(MySQLiteHelper.COLLUMN_LOAD_DETAIL_DEST, data.getDestination());
        values.put(MySQLiteHelper.COLLUMN_LOAD_DETAIL_CUST, data.getCustomer());
        values.put(MySQLiteHelper.COLLUMN_LOAD_DETAIL_ITEM, data.getItem());
        values.put(MySQLiteHelper.COLLUMN_LOAD_DETAIL_COMMENT_ID, data.get_comment_id());

        String whereClause = MySQLiteHelper.COLLUMN_LOAD_DETAIL_ID+" = '"+data.get_id()+"' and "+
                             MySQLiteHelper.COLLUMN_LOAD_DETAIL_LINE+" = '"+data.get_line()+"'";

        database.update(MySQLiteHelper.TABLE_LOAD_DETAIL, values, whereClause, null);

    }

    public LoadDetail createLoadDetailLine(long LoadID,long LineID,String Status,String szDestination,String szCustomer,String szItem) {
        ContentValues values = new ContentValues();

        values.put(MySQLiteHelper.COLLUMN_LOAD_DETAIL_ID,LoadID);
        values.put(MySQLiteHelper.COLLUMN_LOAD_DETAIL_LINE,LineID);
        values.put(MySQLiteHelper.COLLUMN_LOAD_DETAIL_STATUS,Status);
        values.put(MySQLiteHelper.COLLUMN_LOAD_DETAIL_DEST,szDestination);
        values.put(MySQLiteHelper.COLLUMN_LOAD_DETAIL_CUST,szCustomer);
        values.put(MySQLiteHelper.COLLUMN_LOAD_DETAIL_ITEM,szItem);
        values.put(MySQLiteHelper.COLLUMN_LOAD_DETAIL_COMMENT_ID,0);

        database.insertOrThrow(MySQLiteHelper.TABLE_LOAD_DETAIL, null, values);

        LoadDetail ret = new LoadDetail();
        ret.set_id(LoadID);
        ret.set_line(LineID);
        ret.setStatus(Status);
        ret.setCustomer(szCustomer);
        ret.setItem(szItem);
        ret.setDestination(szDestination);

        return ret;
    }

    public List<LoadDetail> getLoadLines(long LoadID) {

        List<LoadDetail> ret = new ArrayList<LoadDetail>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_LOAD_DETAIL,allCollumns,MySQLiteHelper.COLLUMN_LOAD_DETAIL_ID+" = '"+Long.toString(LoadID)+"'",null,null,null,null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            ret.add(cursorToLoadDetail(cursor));
            cursor.moveToNext();
        }

        cursor.close();

        return  ret;

    }

    public LoadDetail getLoadDetail(long _id,long _line) {

        String whereClause = MySQLiteHelper.COLLUMN_LOAD_DETAIL_ID+" = '"+_id+"' and "+
                             MySQLiteHelper.COLLUMN_LOAD_DETAIL_LINE+" = '"+_line+"'";

        Cursor cursor = database.query(MySQLiteHelper.TABLE_LOAD_DETAIL,allCollumns,whereClause,null,null,null,null);
        cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            return  cursorToLoadDetail(cursor);
        } else
            return null;
    }

    private LoadDetail cursorToLoadDetail(Cursor cursor) {
        LoadDetail detail = new LoadDetail();
        detail.set_id(cursor.getInt(0));
        detail.set_line(cursor.getInt(1));
        detail.setStatus(cursor.getString(2));
        detail.setDestination(cursor.getString(3));
        detail.setCustomer(cursor.getString(4));
        detail.setItem(cursor.getString(5));
        detail.set_comment_id(cursor.getInt(6));
        return detail;
    }

    public void updateStatusAndContinue(LoadDetail data) {
        UpdateLoadLine(data);

        // get next line
        String whereClause = MySQLiteHelper.COLLUMN_LOAD_DETAIL_ID+" = '"+data.get_id()+"' and "+
                MySQLiteHelper.COLLUMN_LOAD_DETAIL_STATUS+" = '"+LoadDetail.STATUS_WAITING+"'";

        Cursor cursor = database.query(MySQLiteHelper.TABLE_LOAD_DETAIL,allCollumns,whereClause,null,null,null,null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            LoadDetail next = cursorToLoadDetail(cursor);
            next.setStatus(LoadDetail.STATUS_CURERNT_DELIVERY);
            UpdateLoadLine(next);
            cursor.close();
        }
    }

    public void setDeliveryAsActive(LoadDetail data) {

        // get next line
        String whereClause = MySQLiteHelper.COLLUMN_LOAD_DETAIL_ID+" = '"+data.get_id()+"' and "+
                MySQLiteHelper.COLLUMN_LOAD_DETAIL_STATUS+" = '"+LoadDetail.STATUS_CURERNT_DELIVERY+"'";

        LoadDetail currentActive = null;

        Cursor cursor = database.query(MySQLiteHelper.TABLE_LOAD_DETAIL,allCollumns,whereClause,null,null,null,null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            // set active line to waiting
            currentActive = cursorToLoadDetail(cursor);
            cursor.close();
            currentActive.setStatus(LoadDetail.STATUS_WAITING);
            UpdateLoadLine(currentActive);
        }

        data.setStatus(LoadDetail.STATUS_CURERNT_DELIVERY);
        UpdateLoadLine(data);
    }
}

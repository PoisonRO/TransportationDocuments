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

import com.pilot.dan.transportationdocuments.database.LoadHeader;
import com.pilot.dan.transportationdocuments.database.MySQLiteHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dan on 9/21/15.
 */

public class LoadHeaderDAO extends TemplateDAO {

    private Context context;
    private String[] allCollumns = {MySQLiteHelper.COLLUMN_LOAD_HEADER_ID,
                                    MySQLiteHelper.COLLUMN_LOAD_HEADER_DESC,
                                    MySQLiteHelper.COLLUMN_LOAD_HEADER_FILE,
                                    MySQLiteHelper.COLLUMN_LOAD_HEADER_DATE,
                                    MySQLiteHelper.COLLUMN_LOAD_HEADER_STATUS
                                    };

    public LoadHeaderDAO(Context context) {
        super(context);
        this.context = context;
    }

    public LoadHeader getActivHeader() {
        Cursor cursor = database.query(MySQLiteHelper.TABLE_LOAD_HEADER,allCollumns,MySQLiteHelper.COLLUMN_LOAD_HEADER_STATUS+" = '"+LoadHeader.STATUS_CURRENT+"'",null,null,null,null);
        cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            return cursorToLoadHeader(cursor);
        }

        return  null;
    }

    /*
    create a new header
     */

    public LoadHeader crateLoadHeader(String description,String fileName,Date date,String status) throws SQLException{

        // get next number
        NextNumberDAO nnDAO = new NextNumberDAO(context);

        ContentValues values = new ContentValues();

        long newID = nnDAO.getNextNumber(NextNumberDAO.NEXT_NUMBER_LOAD_HEADER);

        values.put(MySQLiteHelper.COLLUMN_NN_ID,newID);
        values.put(MySQLiteHelper.COLLUMN_LOAD_HEADER_DESC,description);
        values.put(MySQLiteHelper.COLLUMN_LOAD_HEADER_FILE,fileName);
        values.put(MySQLiteHelper.COLLUMN_LOAD_HEADER_DATE, convertDateToString(date));
        values.put(MySQLiteHelper.COLLUMN_LOAD_HEADER_STATUS,status);

        database.insertOrThrow(MySQLiteHelper.TABLE_LOAD_HEADER, null, values);

        LoadHeader hdr = new LoadHeader();
        hdr.set_id(newID);
        hdr.setDate(date);
        hdr.setDesc(description);
        hdr.setFile(fileName);
        hdr.setStatus(status);
        return hdr;
    }

    /*
    * get all headers
    **/
    public List<LoadHeader> getLoadHeaders() {

        List<LoadHeader> loadHeaders = new ArrayList<LoadHeader>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_LOAD_HEADER,allCollumns,null,null,null,null,null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            loadHeaders.add(cursorToLoadHeader(cursor));
            cursor.moveToNext();
        }

        cursor.close();

        return loadHeaders;
    }

    private LoadHeader cursorToLoadHeader(Cursor cursor) {
        LoadHeader header = new LoadHeader();
        header.set_id(cursor.getInt(0));
        header.setDesc(cursor.getString(1));
        header.setFile(cursor.getString(2));
        header.setDate(convertStringToDate(cursor.getString(3)));
        header.setStatus(cursor.getString(4));
        return header;
    }
}


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

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.pilot.dan.transportationdocuments.database.MySQLiteHelper;
import com.pilot.dan.transportationdocuments.dialog.DialogError;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by dan on 9/21/15.
 */
public class TemplateDAO {

    protected SQLiteDatabase      database;
    protected MySQLiteHelper dbHelper;

    protected TemplateDAO() {};

    public TemplateDAO(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public  void close() {
        if (database!=null)
            database.close();
    }

    static protected String convertDateToString(Date date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }

    static protected Date convertStringToDate(String date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date ret;
        try {
            ret = dateFormat.parse(date);
        } catch (ParseException e) {
            ret = null;
        }

        return ret;
    }
}

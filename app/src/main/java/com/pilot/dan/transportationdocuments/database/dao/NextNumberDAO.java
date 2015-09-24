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

import com.pilot.dan.transportationdocuments.database.MySQLiteHelper;

import java.sql.SQLException;

/**
 * Created by dan on 9/21/15.
 */
public class NextNumberDAO extends TemplateDAO {

    public static final String NEXT_NUMBER_LOAD_HEADER      = "nnldh";
    public static final String NEXT_NUMER_COMMENT           = "cmntid";
    public static final String NEXT_NUMER_PIC_ID            = "picid";

    private String[] allCollumns = {MySQLiteHelper.COLLUMN_NN_ID,
                                    MySQLiteHelper.COLLUN_NN_VALUE};

    public NextNumberDAO(Context context) {
        super(context);
    }

    public long getNextNumber(String key) throws SQLException{

        long retValue=1;

        open();

        ContentValues values = new ContentValues();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_NEXT_NUMBERS,allCollumns,MySQLiteHelper.COLLUMN_NN_ID+ " = '"+key+"'",null,null,null,null);
        cursor.moveToFirst();
        if (cursor.isAfterLast()) {
            // add new next number

            values.put(MySQLiteHelper.COLLUMN_NN_ID,key);
            values.put(MySQLiteHelper.COLLUN_NN_VALUE,retValue+1);
            database.insert(MySQLiteHelper.TABLE_NEXT_NUMBERS, null, values);

        } else {

            // increment next number
            retValue = cursor.getInt(1);

            values.put(MySQLiteHelper.COLLUN_NN_VALUE,retValue+1);
            String whereClause = MySQLiteHelper.COLLUMN_NN_ID+" = '"+key+"'";
            database.update(MySQLiteHelper.TABLE_NEXT_NUMBERS,values,whereClause,null);
        }

        close();

        return retValue;
    }
}
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


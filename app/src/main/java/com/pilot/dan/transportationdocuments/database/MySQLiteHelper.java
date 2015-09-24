package com.pilot.dan.transportationdocuments.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dan on 9/19/15.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    // database parameters
    private static final String DATABASE_NAME = "transportation.db";
    private static final int DATABASE_VERSION = 1;

    /*first table settings*/
    public static final String TABLE_SETTINGS                       = "settings";
    public static final String COLLUMN_SETTING_ID                   = "_id";
    public static final String COLLUMN_SETTING_VALUE                = "value";
    private static final String CREATE_SETTINGS_TABLE =
            "create table "+TABLE_SETTINGS+"("+
                    COLLUMN_SETTING_ID+" text primary key, "+
                    COLLUMN_SETTING_VALUE+ " text);";

    /*second table load header*/
    public static final String TABLE_LOAD_HEADER                    = "loadhdr";
    public static final String COLLUMN_LOAD_HEADER_ID               = "_id";
    public static final String COLLUMN_LOAD_HEADER_DESC             = "desc";
    public static final String COLLUMN_LOAD_HEADER_FILE             = "file";
    public static final String COLLUMN_LOAD_HEADER_DATE             = "date";
    public static final String COLLUMN_LOAD_HEADER_STATUS           = "status";

    private static final String CREATE_LOAD_HEADER_TABLE=
            "create table "+TABLE_LOAD_HEADER+"("+
            COLLUMN_LOAD_HEADER_ID+" integer primary key,"+
            COLLUMN_LOAD_HEADER_DESC+" text,"+
            COLLUMN_LOAD_HEADER_FILE+" text,"+
            COLLUMN_LOAD_HEADER_DATE+" datetime,"+
            COLLUMN_LOAD_HEADER_STATUS +" text"+

            ");";

    /*third table load detail*/
    public static final String TABLE_LOAD_DETAIL                    = "loaddtl";
    public static final String COLLUMN_LOAD_DETAIL_ID               = "_id";
    public static final String COLLUMN_LOAD_DETAIL_LINE             = "_line";
    public static final String COLLUMN_LOAD_DETAIL_STATUS           = "status";
    public static final String COLLUMN_LOAD_DETAIL_DEST             = "dest";
    public static final String COLLUMN_LOAD_DETAIL_CUST             = "cust";
    public static final String COLLUMN_LOAD_DETAIL_ITEM             = "item";
    public static final String COLLUMN_LOAD_DETAIL_COMMENT_ID       = "_id_comment";

    private static final String CREATE_LOAD_DETAIL_TABLE=
            "create table "+TABLE_LOAD_DETAIL+"("+
            COLLUMN_LOAD_DETAIL_ID+" integer,"+
            COLLUMN_LOAD_DETAIL_LINE+" integer,"+
            COLLUMN_LOAD_DETAIL_STATUS+" text,"+
            COLLUMN_LOAD_DETAIL_DEST+" text,"+
            COLLUMN_LOAD_DETAIL_CUST+" text,"+
            COLLUMN_LOAD_DETAIL_ITEM+" text,"+
            COLLUMN_LOAD_DETAIL_COMMENT_ID+" integer,"+
            "primary key ("+COLLUMN_LOAD_DETAIL_ID+","+COLLUMN_LOAD_DETAIL_LINE+"));";


    public static final String TABLE_NEXT_NUMBERS                   = "nextnumbers";
    public static final String COLLUMN_NN_ID                        = "_id";
    public static final String COLLUN_NN_VALUE                      = "value";

    private static final String CREATE_NEXT_NUMBER_TABLE =
            "create table "+TABLE_NEXT_NUMBERS+"("+
                    COLLUMN_NN_ID+" text primary key,"+
                    COLLUN_NN_VALUE+" integer);";

    public static final String TABLE_COMMENTS                       = "comments";
    public static final String COLLUMN_COMMENTS_ID                  = "_id";
    public static final String COLLUMN_COMMENTS_TEXT                = "comment";

    private static final String CREATE_COMMENTS_TABLE =
            "create table "+TABLE_COMMENTS+"("+
            COLLUMN_COMMENTS_ID+" integer primary key,"+
            COLLUMN_COMMENTS_TEXT+" text"+
            ");";

    public static final String TABLE_PICTURES                       = "pictures";
    public static final String COLLUMN_PICTURE_LOAD_ID              = "_id";
    public static final String COLLUMN_PICTURE_LOAD_LINE            = "_line";
    public static final String COLLUMN_PICTURE_ID                   = "_pic_id";
    public static final String COLLUMN_PICTURE                      = "pic";

    private static final String CREATE_PICTURES_TABLE =
            "create table "+TABLE_PICTURES+"("+
            COLLUMN_PICTURE_LOAD_ID+" integer,"+
            COLLUMN_PICTURE_LOAD_LINE+" integer,"+
            COLLUMN_PICTURE_ID+" integer,"+
            COLLUMN_PICTURE+" text,"+
            "primary key ("+COLLUMN_PICTURE_LOAD_ID+","+COLLUMN_PICTURE_LOAD_LINE+","+COLLUMN_PICTURE_ID+"));";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        database.execSQL(CREATE_SETTINGS_TABLE);
        database.execSQL(CREATE_LOAD_HEADER_TABLE);
        database.execSQL(CREATE_LOAD_DETAIL_TABLE);
        database.execSQL(CREATE_NEXT_NUMBER_TABLE);
        database.execSQL(CREATE_COMMENTS_TABLE);
        database.execSQL(CREATE_PICTURES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO add upgrade scripts
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


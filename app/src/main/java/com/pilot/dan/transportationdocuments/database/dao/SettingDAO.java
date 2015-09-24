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
import com.pilot.dan.transportationdocuments.database.Setting;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dan on 9/19/15.
 *
 * This is the Data Access Object for the settings table
 */

public class SettingDAO extends TemplateDAO {

    private String[] allCollumns = {MySQLiteHelper.COLLUMN_SETTING_ID,
                                    MySQLiteHelper.COLLUMN_SETTING_VALUE};

    public SettingDAO(Context context) {
        super(context);
    }

    /**
     *
     * Function used to add a new record to the database
     *
     * @param id - this is the setting name (ID)
     * @param value - this is the setting value
     * @return Setting Object
     */


    public Setting createSetting(String id,String value) {

        // set the record information
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLLUMN_SETTING_ID, id);
        values.put(MySQLiteHelper.COLLUMN_SETTING_VALUE, value);

        // execute SQL to insert the record
        //database.insertOrThrow(MySQLiteHelper.TABLE_SETTINGS,null, values);
        database.insert(MySQLiteHelper.TABLE_SETTINGS,null, values);

        // create return value
        Setting retSetting = new Setting();

        retSetting.setId(id);
        retSetting.setValue(value);

        return retSetting;

    }

    public String getSetting(String key) {

        String ret=null;

        Cursor cursor = database.query(MySQLiteHelper.TABLE_SETTINGS,allCollumns,MySQLiteHelper.COLLUMN_SETTING_ID+" = '"+key+"'",null,null,null,null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast())
            ret=cursor.getString(1);

        cursor.close();

        return  ret;

    }

    /**
     * Function used to delete a record from the database
     * NOT USED YET
     */

    public void deleteSetting(Setting setting) {
        database.delete(MySQLiteHelper.TABLE_SETTINGS, MySQLiteHelper.COLLUMN_SETTING_ID + " = " + setting.getId(), null);
    }

    /**
     * Function used to update a record in the database
     * @param setting
     */

    public void updateSetting(Setting setting) {

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLLUMN_SETTING_VALUE, setting.getValue());
        String where_clause = MySQLiteHelper.COLLUMN_SETTING_ID+" = '"+setting.getId()+"'";
        database.update(MySQLiteHelper.TABLE_SETTINGS,values,where_clause,null);
    }

    /**
     *
     * Function used to query all settings from the database
     *
     * @return a list of settings
     */
    public List<Setting> getSettings() {

        List<Setting> settings = new ArrayList<Setting>();

        // execute query and open cursor
        Cursor cursor = database.query(MySQLiteHelper.TABLE_SETTINGS,allCollumns,null,null,null,null,null);

        // iterate the cursor and add the values to the return list
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Setting setting = cursorToComment(cursor);
            settings.add(setting);
            cursor.moveToNext();
        }

        // close cursor and return list
        cursor.close();
        return settings;
    }

    public List<String> getSettingsDefinition() {
        List<String> settings = new ArrayList<String>();

        // execute query and open cursor
        Cursor cursor = database.query(MySQLiteHelper.TABLE_SETTINGS,allCollumns,null,null,null,null,null);

        // iterate the cursor and add the values to the return list
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            settings.add(cursor.getString(0));
            cursor.moveToNext();
        }

        // close cursor and return list
        cursor.close();

        return settings;

    }

    // this converts the cursor tot a setting object
    private Setting cursorToComment(Cursor cursor) {
        Setting setting = new Setting();
        setting.setId(cursor.getString(0));
        setting.setValue(cursor.getString(1));
        return setting;
    }
}

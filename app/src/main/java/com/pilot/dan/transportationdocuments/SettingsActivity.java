package com.pilot.dan.transportationdocuments;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.pilot.dan.transportationdocuments.database.dao.SettingDAO;
import com.pilot.dan.transportationdocuments.dialog.EditSetting;

import java.sql.SQLException;

public class SettingsActivity extends AppCompatActivity {

    ListView settingslist;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final ViewGroup vg = (ViewGroup)findViewById(R.id.settingsActivity);
        settingslist = (ListView) findViewById(R.id.listViewSettings);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        final AppCompatActivity activity = this;

        // set on click option

        refreshListView();

        settingslist.setOnItemClickListener(
            new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    FragmentManager manager = getFragmentManager();

                    EditSetting editDialog = new EditSetting();
                    editDialog.setActivity(activity);

                    // set dialog title
                    Bundle arguments = new Bundle();
                    arguments.putInt("setting_id", position);

                    editDialog.setArguments(arguments);


                    editDialog.show(manager,"edit_setting_fragment");

                    }
                }
            );

        settingslist.setAdapter(adapter);

    }

    private void refreshListView() {
        SettingDAO settingsDAO = new SettingDAO(this);
        try {
            settingsDAO.open();
            adapter.clear();
            adapter.addAll(settingsDAO.getSettingsDefinition());
            settingsDAO.close();
        } catch (SQLException e) {
            // TODO handle exception
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_new) {
            // add new setting
            FragmentManager manager = getFragmentManager();

            EditSetting editDialog = new EditSetting();
            editDialog.setActivity(this);

            editDialog.setAddNewCallback(new Runnable() {
                @Override
                public void run() {
                    refreshListView();
                }
            });

            editDialog.show(manager, "edit_setting_fragment");
            return true;
        }

        if (id==R.id.about) {
            Intent settings_intent = new Intent(this,AboutActivity.class);
            startActivity(settings_intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
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

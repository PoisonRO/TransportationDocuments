package com.pilot.dan.transportationdocuments;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pilot.dan.transportationdocuments.adapters.LoadHistoryAdapter;
import com.pilot.dan.transportationdocuments.database.LoadHeader;
import com.pilot.dan.transportationdocuments.database.dao.LoadHeaderDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class LoadHistoryActivity extends AppCompatActivity {

    ListView headerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_history);

        final Activity activity = this;

        // set header list
        headerList = (ListView)findViewById(R.id.listViewLdHdr);



        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);

        // debug
        LoadHeaderDAO hdrDAO = new LoadHeaderDAO(this);


        try {
            hdrDAO.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        final LoadHistoryAdapter adapter = new LoadHistoryAdapter(this,(ArrayList)hdrDAO.getLoadHeaders());
        hdrDAO.close();

        // set callback for lists
        headerList.setOnItemClickListener(

                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(activity, LoadDetailActivity.class);
                        LoadHeader selected = (LoadHeader)adapter.getItem(position);
                        intent.putExtra("LOAD",""+selected.get_id() );
                        startActivity(intent);
                    }
                }
        );

        headerList.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_load_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

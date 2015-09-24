package com.pilot.dan.transportationdocuments;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.pilot.dan.transportationdocuments.adapters.LoadDetailAdapter;
import com.pilot.dan.transportationdocuments.database.dao.LoadDetailDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class LoadDetailActivity extends AppCompatActivity {

    ListView detailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_detail);

        detailList = (ListView)findViewById(R.id.listViewLoadDetail);

        final LoadDetailDAO dtlDAO = new LoadDetailDAO(this);

        try {
            dtlDAO.open();
        } catch (SQLException e) {

        }

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            //String value = extras.getString("new_variable_name");
            return;
        }

        String value = extras.getString("LOAD");
        if (value == null) return;
        int position = Integer.parseInt(value);

        final LoadDetailAdapter adapter1 = new LoadDetailAdapter(this,(ArrayList) dtlDAO.getLoadLines(position));
        detailList.setAdapter(adapter1);

        dtlDAO.close();

        registerForContextMenu(detailList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_load_detail, menu);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu,View view,ContextMenu.ContextMenuInfo menuInfo) {
        //super.onCreate(menu,view,menuInfo);

        //https://www.youtube.com/watch?v=Pq9YQl0nfEk
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_history_load_context,menu);
    }
}

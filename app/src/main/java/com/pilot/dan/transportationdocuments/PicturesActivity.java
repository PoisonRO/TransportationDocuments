package com.pilot.dan.transportationdocuments;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.pilot.dan.transportationdocuments.adapters.PicturesAdapter;
import com.pilot.dan.transportationdocuments.database.dao.PictureDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class PicturesActivity extends AppCompatActivity {

    ListView            picturesList;
    PicturesAdapter     adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures);

        picturesList = (ListView)findViewById(R.id.listViewPictures);


        PictureDAO pictureDAO = new PictureDAO(this);
        adapter = new PicturesAdapter(this,null);

        // get values from parent

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }

        String value = extras.getString("LOAD");
        if (value == null) return;
        long _id = Integer.parseInt(value);

        value = extras.getString("LOAD_LINE");
        if (value == null) return;
        long _line = Integer.parseInt(value);

        try {

            pictureDAO.open();
            adapter.updateData((ArrayList) pictureDAO.getDeliveryPictures(_id, _line));
            pictureDAO.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        picturesList.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pictures, menu);
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

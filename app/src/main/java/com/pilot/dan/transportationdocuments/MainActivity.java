package com.pilot.dan.transportationdocuments;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.pilot.dan.transportationdocuments.adapters.LoadDetailAdapter;
import com.pilot.dan.transportationdocuments.database.Comment;
import com.pilot.dan.transportationdocuments.database.Picture;
import com.pilot.dan.transportationdocuments.database.dao.CommentDAO;
import com.pilot.dan.transportationdocuments.database.LoadDetail;
import com.pilot.dan.transportationdocuments.database.dao.LoadDetailDAO;
import com.pilot.dan.transportationdocuments.database.LoadHeader;
import com.pilot.dan.transportationdocuments.database.dao.LoadHeaderDAO;
import com.pilot.dan.transportationdocuments.database.dao.PictureDAO;
import com.pilot.dan.transportationdocuments.database.dao.SettingDAO;
import com.pilot.dan.transportationdocuments.dialog.DeliveryNotOK;
import com.pilot.dan.transportationdocuments.dialog.inputValues.InputDeliveryNotOK;
import com.pilot.dan.transportationdocuments.dialog.returnValues.ReturnDeliveryNotOK;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    ListView                        detailList;
    Switch                          aSwitch;

    LoadDetailAdapter               adapter;
    ReturnDeliveryNotOK             retNotOkValues;
    LoadDetail                      itemSelected;
    LoadHeader                      activeHeader;
    Picture                         pic;

    //DAO
    LoadDetailDAO                   activeLoadLines;
    LoadHeaderDAO                   activeLoad;
    CommentDAO                      commentDAO;
    PictureDAO                      pictureDAO;

    // fragment manager for dialogs
    FragmentManager                 manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = getFragmentManager();

        //create dao
        activeLoadLines     = new LoadDetailDAO(this);
        commentDAO          = new CommentDAO(this);
        activeLoad          = new LoadHeaderDAO(this);
        pictureDAO          = new PictureDAO(this);

        // Delivery not ok values
        retNotOkValues = new ReturnDeliveryNotOK();

        // get controls
        detailList = (ListView)findViewById(R.id.listViewMainActivity);
        aSwitch = (Switch)findViewById(R.id.view_finalized);

        adapter = new LoadDetailAdapter(this,null);
        adapter.setHideCompleted(true);

        detailList.setAdapter(adapter);

        aSwitch.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            adapter.setHideCompleted(false);
                            adapter.notifyDataSetChanged();
                        } else {
                            adapter.setHideCompleted(true);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
        );

        RefreshGrid();
        registerForContextMenu(detailList);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        SettingDAO settingDAO = new SettingDAO(this);
        getMenuInflater().inflate(R.menu.menu_main_with_demo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // goto to settings activity
        if (id == R.id.action_settings) {
            Intent settings_intent = new Intent(this,SettingsActivity.class);
            startActivity(settings_intent);
            return true;
        }

        if (id == R.id.about) {
            Intent settings_intent = new Intent(this,AboutActivity.class);
            startActivity(settings_intent);
            return true;
        }

        if (id == R.id.history) {
            Intent settings_intent = new Intent(this,LoadHistoryActivity.class);
            startActivity(settings_intent);
            return true;
        }

        if (id == R.id.demo) {
            // load demo data
            LoadDemoData();
            RefreshGrid();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void LoadDemoData() {
        LoadHeaderDAO hdr = new LoadHeaderDAO(this);
        LoadDetailDAO dtl = new LoadDetailDAO(this);

        try {
            hdr.open();
            dtl.open();

            for (int i =0;i<11;i++) {

                String status_h;

                if (i==10) status_h = LoadHeader.STATUS_CURRENT;
                else {
                    if (i%2 == 0) status_h = LoadHeader.STATUS_NOT_UPLOADED;
                    else status_h = LoadHeader.STATUS_UPLOADED;
                }

                LoadHeader header = hdr.crateLoadHeader("Load "+i,
                                                        "file_"+i+".csv",
                                                        new Date(),
                                                        status_h);
                for (int j =0;j<10;j++) {
                    //long LoadID,long LineID,String Status,String szDestination,String szCustomer,String szItem

                    String status;
                    if (j==0)
                        status = LoadDetail.STATUS_CURERNT_DELIVERY;
                    else
                        status = LoadDetail.STATUS_WAITING;

                    dtl.createLoadDetailLine(   header.get_id(),
                                                j,
                                                status,
                                                "Destinatie" + i+j,
                                                "Client "+i+j,
                                                "Produs "+i+j);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        hdr.close();
        dtl.close();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu,View view,ContextMenu.ContextMenuInfo menuInfo) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

        itemSelected = (LoadDetail)adapter.getItem(info.position);

        String item_status = itemSelected.getStatus();
        MenuInflater inflater = getMenuInflater();

        switch (item_status) {
            case LoadDetail.STATUS_CURERNT_DELIVERY:
                inflater.inflate(R.menu.menu_active_load_context_current, menu);
                break;
            case LoadDetail.STATUS_WAITING:
                inflater.inflate(R.menu.menu_active_load_context_waiting, menu);
                break;
            case LoadDetail.STATUS_NOT_DELIVERED:
                inflater.inflate(R.menu.menu_active_load_context_error,menu);
                break;
            case LoadDetail.STATUS_DELIVERED_NO_DOCS:
                inflater.inflate(R.menu.menu_active_load_context_no_docs,menu);
                break;
            case LoadDetail.STATUS_DELIVERY_OK:
                inflater.inflate(R.menu.menu_active_load_context_delivered,menu);
                break;
        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int id = item.getItemId();
        DeliveryNotOK dialogNotOK;

        switch (id) {
            case R.id.add_new_pic:
                takeNewPicture();
                return true;

            case R.id.main_delivery_not_ok:

                dialogNotOK = new DeliveryNotOK();
                dialogNotOK.setRetValues(retNotOkValues);

                dialogNotOK.setAddNewCallback(new Runnable() {

                    @Override
                    public void run() {
                        setDeliveryNotOK();
                    }

                });

                dialogNotOK.show(manager,"delivery_not_ok_fragment");
                return true;

            case R.id.main_delivery_ok:
                takeNewPicture();
                return true;

            case R.id.main_delivery_ok_no_docs:
                return true;

            case R.id.main_delivery_set_active:

                try {
                    activeLoadLines.open();
                    activeLoadLines.setDeliveryAsActive(itemSelected);
                    RefreshGrid();
                }catch (SQLException e) {
                    ShowDialogError();
                }

                return true;
            case R.id.main_view_error:

                dialogNotOK = new DeliveryNotOK();
                InputDeliveryNotOK inputDeliveryNotOK = new InputDeliveryNotOK();

                inputDeliveryNotOK.set_id(itemSelected.get_id());
                inputDeliveryNotOK.set_line(itemSelected.get_line());

                dialogNotOK.setInputValues(inputDeliveryNotOK);

                dialogNotOK.show(manager,"delivery_not_ok_fragment");

                return true;

            case R.id.view_pics:

                Intent picturesActivity = new Intent(this,PicturesActivity.class);

                picturesActivity.putExtra("LOAD",""+itemSelected.get_id());
                picturesActivity.putExtra("LOAD_LINE",""+itemSelected.get_line());

                startActivity(picturesActivity);

                return true;
        }

        return false;
    }

    private void setDeliveryNotOK() {
        try {

            Comment comment=null;

            commentDAO.open();
            comment = commentDAO.createNewComment(retNotOkValues.getData());
            commentDAO.close();

            activeLoadLines.open();

            itemSelected.setStatus(LoadDetail.STATUS_NOT_DELIVERED);

            itemSelected.set_comment_id(comment.getComment_id());
            activeLoadLines.updateStatusAndContinue(itemSelected);
            activeLoadLines.close();

        } catch (SQLException e) {
            ShowDialogError();
        }

        RefreshGrid();
    }

    private void RefreshGrid() {


        try {
            activeLoad.open();

            activeHeader = activeLoad.getActivHeader();
            activeLoad.close();

            if (activeHeader==null) return;

            activeLoadLines.open();
            adapter.refreshData((ArrayList) activeLoadLines.getLoadLines(activeHeader.get_id()));
            adapter.notifyDataSetChanged();
            activeLoadLines.close();

        } catch ( SQLException e) {
            ShowDialogError();
        }

    }

    private void ShowDialogError() {
        /*DialogError dialogError = new DialogError();
        dialogError.setSzMessage("SQL Error");
        dialogError.show(manager, "error_fragment");*/
    }

    private void takeNewPicture() {
    //http://stackoverflow.com/questions/6448856/android-camera-intent-how-to-get-full-sized-photo
        try {
            pictureDAO.open();
            pic = pictureDAO.addNew(itemSelected.get_id(),itemSelected.get_line(),"");
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (cameraIntent.resolveActivity(getPackageManager()) != null)
                startActivityForResult(cameraIntent, 1);

        }catch (SQLException e) {

        } finally {

            pictureDAO.close();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                boolean bImageUploaded=false;

                // write image to disk
                Bundle extras = data.getExtras();
                Bitmap bmp = extras.getParcelable("data");

                FileOutputStream outputStream;

                try {

                    outputStream = openFileOutput(pic.getPic_location(), Context.MODE_PRIVATE);
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();

                    bImageUploaded=true;

                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    activeLoadLines.open();
                    // validate status
                    switch (itemSelected.getStatus()){
                        case LoadDetail.STATUS_CURERNT_DELIVERY:

                            if (bImageUploaded=true) {
                                itemSelected.setStatus(LoadDetail.STATUS_DELIVERY_OK);
                                activeLoadLines.updateStatusAndContinue(itemSelected);
                            } else {
                                itemSelected.setStatus(LoadDetail.STATUS_DELIVERED_NO_DOCS);
                                activeLoadLines.updateStatusAndContinue(itemSelected);

                            }
                            RefreshGrid();
                            break;

                        case LoadDetail.STATUS_DELIVERED_NO_DOCS:
                            if (bImageUploaded=true) {
                                itemSelected.setStatus(LoadDetail.STATUS_DELIVERY_OK);
                                activeLoadLines.UpdateLoadLine(itemSelected);
                            }
                            RefreshGrid();
                            break;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();

                } finally {
                    activeLoadLines.close();
                }

            } else {

                // if this is the current shipment and we cancel
                // set the line to done without pictures
                if (itemSelected.getStatus().equals(LoadDetail.STATUS_CURERNT_DELIVERY)) {
                    try {
                        activeLoadLines.open();
                        itemSelected.setStatus(LoadDetail.STATUS_DELIVERED_NO_DOCS);
                        activeLoadLines.updateStatusAndContinue(itemSelected);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        activeLoadLines.close();
                    }
                    RefreshGrid();
                }
            }
        }
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



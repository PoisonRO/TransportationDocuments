package com.pilot.dan.transportationdocuments.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pilot.dan.transportationdocuments.R;
import com.pilot.dan.transportationdocuments.database.Setting;
import com.pilot.dan.transportationdocuments.database.dao.SettingDAO;

import java.sql.SQLException;

/**
 * Created by dan on 9/20/15.
 */
public class EditSetting extends DialogFragment {

    private EditText settingValue;
    private EditText settingValueKey;

    private AppCompatActivity activity;
    private Dialog settingsDialog;
    private SettingDAO settingsDAO;
    private String setting;

    private Boolean bAddNew;

    Runnable addNewCallback;

    public EditSetting(){}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        Bundle arguments = getArguments();
        Integer setting_id=-1;
        View view;

        // if we don't pass setting id we need the new setting layout
        if (arguments != null && arguments.containsKey("setting_id") == true) {
            bAddNew = false;
            view = inflater.inflate(R.layout.dialog_edit_setting, null);

            // get input for update
            settingValue = (EditText) view.findViewById(R.id.editSettingInput);
        }
        else {
            bAddNew = true;
            view = inflater.inflate(R.layout.dialog_new_setting, null);

            // get input for key and value
            settingValue = (EditText) view.findViewById(R.id.newSettingInput);
            settingValueKey = (EditText) view.findViewById(R.id.newSettingKeyInput);
        }


        dialog.setView(view);

        // set button listeners
        Button bOK = (Button) view.findViewById(R.id.bSet_OK);
        Button bCancel = (Button) view.findViewById(R.id.bSet_Cancel);

        bOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (bAddNew== false) {

                    try {

                        // update selected setting

                        settingsDAO.open();

                        Setting s = new Setting();
                        s.setId(setting);
                        s.setValue(settingValue.getText().toString());
                        settingsDAO.updateSetting(s);
                        settingsDAO.close();

                    } catch (SQLException e) {
                        // TODO handle SQL Exception
                    }

                } else {

                    // add new setting
                    try {
                        settingsDAO.open();
                        settingsDAO.createSetting(
                                settingValueKey.getText().toString(),
                                settingValue.getText().toString());
                        settingsDAO.close();
                    } catch ( SQLException e) {
                        // TODO handle SQL Exception
                    }

                }

                if (addNewCallback!=null) addNewCallback.run();

                settingsDialog.dismiss();
            }
        });

        bCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                settingsDialog.dismiss();
            }
        });

        settingsDAO = new SettingDAO(activity);


        if (bAddNew == false) {
            setting_id = arguments.getInt("setting_id");

            try {

                settingsDAO.open();

                setting = settingsDAO.getSettings().get(setting_id).getId();
                String hit = settingsDAO.getSettings().get(setting_id).getValue();

                settingsDAO.close();

                dialog.setTitle("Modify " + setting);

                settingValue.setHint(hit);
            } catch ( SQLException e) {

            }
        } else {
            dialog.setTitle("Add New Setting");
        }

        settingsDialog = dialog.create();
        return settingsDialog;
    }

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void setAddNewCallback(Runnable addNewCallback) {
        this.addNewCallback = addNewCallback;
    }
}

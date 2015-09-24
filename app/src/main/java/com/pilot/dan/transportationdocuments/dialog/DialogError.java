package com.pilot.dan.transportationdocuments.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pilot.dan.transportationdocuments.R;

/**
 * Created by dan on 9/22/15.
 */
public class DialogError extends DialogFragment {

    private Dialog              dialog;
    private Button              bOk;
    private String              szMessage;
    private TextView            tMessage;

    public DialogError(){}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view;

        view = inflater.inflate(R.layout.dialog_error, null);

        bOk = (Button)view.findViewById(R.id.bOK);
        tMessage = (TextView)view.findViewById(R.id.errorText);

        tMessage.setText(szMessage.toString());

        bOk.setOnClickListener(

                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                }
        );

        return  null;
    }

    public void setSzMessage(String szMessage) {
        this.szMessage = szMessage;
    }
}

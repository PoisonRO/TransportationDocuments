package com.pilot.dan.transportationdocuments.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pilot.dan.transportationdocuments.R;
import com.pilot.dan.transportationdocuments.database.Comment;
import com.pilot.dan.transportationdocuments.database.dao.CommentDAO;
import com.pilot.dan.transportationdocuments.dialog.inputValues.InputDeliveryNotOK;
import com.pilot.dan.transportationdocuments.dialog.returnValues.ReturnDeliveryNotOK;

import java.sql.SQLException;

/**
 * Created by dan on 9/22/15.
 */
public class DeliveryNotOK extends DialogFragment {

    private Dialog              dialog;
    private Button              bOk;
    private Button              bCancel;
    private EditText            tObs;

    private Runnable            addNewCallback;

    private ReturnDeliveryNotOK retValues;
    private InputDeliveryNotOK  inputValues;

    private CommentDAO          commentDAO;
    private Comment             comment;

    public DeliveryNotOK(){}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view;

        view = inflater.inflate(R.layout.dialog_delivery_nok, null);

        bOk = (Button)view.findViewById(R.id.bOK);
        bCancel = (Button)view.findViewById(R.id.bCancel);
        tObs = (EditText)view.findViewById(R.id.editText);


        if (inputValues!=null) {
            commentDAO = new CommentDAO(this.getActivity());
            try {
                commentDAO.open();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            comment=commentDAO.getCommnet(inputValues.get_id(),inputValues.get_line());
            tObs.setText(comment.getComment());

            commentDAO.close();
        }

        //set listeners for buttons
        //Button OK
        bOk.setOnClickListener(

                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (inputValues!=null) {
                            try {
                                commentDAO.open();
                            }catch (SQLException e) {

                            }

                            comment.setComment_id(comment.getComment_id());
                            comment.setComment(tObs.getText().toString());
                            commentDAO.updateComment(comment);

                            commentDAO.close();

                        }

                        if (retValues!=null) retValues.setData(tObs.getText().toString());
                        if (addNewCallback!=null) addNewCallback.run();
                        dialog.dismiss();
                    }
                }

        );

        //Button Cancel
        bCancel.setOnClickListener(

                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }

                }
        );

        dialogBuilder.setView(view);

        dialog = dialogBuilder.create();
        return dialog;
    }

    public void setAddNewCallback(Runnable addNewCallback) {
        this.addNewCallback = addNewCallback;
    }

    public void setRetValues(ReturnDeliveryNotOK retValues) {
        this.retValues = retValues;
    }

    public void setInputValues(InputDeliveryNotOK inputValues) {
        this.inputValues = inputValues;
    }
}

package com.pilot.dan.transportationdocuments.adapters;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pilot.dan.transportationdocuments.R;
import com.pilot.dan.transportationdocuments.database.LoadDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dan on 9/21/15.
 */

//http://androidexample.com/How_To_Create_A_Custom_Listview_-_Android_Example/index.php?view=article_discription&aid=67&aaid=92
public class LoadDetailAdapter extends BaseAdapter{

    private Activity activity;
    private ArrayList<LoadDetail> data;
    private LayoutInflater inflater;
    private Boolean hideCompleted=false;


    // list components
    ImageView imageStatus;
    TextView textViewDestination;
    TextView textViewCustomer;
    TextView textViewItem;

    public LoadDetailAdapter(Activity activity,ArrayList<LoadDetail> data) {
        this.activity = activity;
        this.data = new ArrayList<LoadDetail>();
        if (data != null)
            this.data.addAll(data);
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void refreshData(ArrayList<LoadDetail> data) {
        this.data.clear();
        this.data.addAll(data);
    }

    @Override
    public int getCount(){
        int ret = data.size();
        if (ret<=0)
            return 0;
        else
            return ret;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View  view = inflater.inflate(R.layout.adapter_load_detail,null);
        LinearLayout layout = (LinearLayout)view.findViewById(R.id.idAdapterLoadDetail);

        textViewDestination = (TextView)view.findViewById(R.id.textViewDest);
        textViewCustomer = (TextView)view.findViewById(R.id.textViewCust);
        textViewItem = (TextView)view.findViewById(R.id.textViewItem);
        imageStatus = (ImageView)view.findViewById(R.id.imageDtlStatus);

        LoadDetail selectedItem;

        if (data.size() > 0) {


            selectedItem = data.get(position);
            // set image
            switch (selectedItem.getStatus()) {
                case LoadDetail.STATUS_WAITING:
                    imageStatus.setImageResource(R.drawable.delivery_waiting);
                    break;
                case LoadDetail.STATUS_CURERNT_DELIVERY:
                    imageStatus.setImageResource(R.drawable.current_delivery);
                    break;
                case LoadDetail.STATUS_DELIVERED_NO_DOCS:
                    if (hideCompleted==true)
                        layout.setVisibility(View.GONE);
                    imageStatus.setImageResource(R.drawable.delivery_nodocs);
                    break;
                case LoadDetail.STATUS_NOT_DELIVERED:
                    if (hideCompleted==true)
                        layout.setVisibility(View.GONE);
                    imageStatus.setImageResource(R.drawable.delivery_nok);
                    break;
                case LoadDetail.STATUS_DELIVERY_OK:
                    if (hideCompleted==true)
                        layout.setVisibility(View.GONE);
                    imageStatus.setImageResource(R.drawable.delivery_ok);
                    break;

            }

            textViewCustomer.setText(selectedItem.getCustomer());
            textViewDestination.setText(selectedItem.getDestination());
            textViewItem.setText(selectedItem.getItem());

        }


        return view;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Boolean getHideCompleted() {
        return hideCompleted;
    }

    public void setHideCompleted(Boolean hideCompleted) {
        this.hideCompleted = hideCompleted;
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


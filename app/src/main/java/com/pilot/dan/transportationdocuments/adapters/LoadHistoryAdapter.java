package com.pilot.dan.transportationdocuments.adapters;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pilot.dan.transportationdocuments.R;
import com.pilot.dan.transportationdocuments.database.LoadHeader;

import java.util.ArrayList;

/**
 * Created by dan on 9/22/15.
 */
public class LoadHistoryAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<LoadHeader> data;
    private LayoutInflater inflater;

    TextView textViewDesc;
    ImageView imageStatus;

    public LoadHistoryAdapter(Activity activity,ArrayList<LoadHeader> data)
    {
        this.activity = activity;
        this.data = new ArrayList<LoadHeader>();
        if (data != null)
            this.data.addAll(data);
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        View  view = inflater.inflate(R.layout.adapter_load_history,null);
        textViewDesc = (TextView)view.findViewById(R.id.textViewDesc);
        imageStatus = (ImageView)view.findViewById(R.id.imageViewHeaderStatus);

        LoadHeader selectedItem;

        if (data.size() > 0) {
            selectedItem = data.get(position);

            //set icon

            switch ( selectedItem.getStatus()) {
                case LoadHeader.STATUS_CURRENT:
                    imageStatus.setImageResource(R.drawable.current_delivery);
                    break;
                case LoadHeader.STATUS_NOT_UPLOADED:
                    imageStatus.setImageResource(R.drawable.load_not_uploaded);
                    break;
                case LoadHeader.STATUS_UPLOADED:
                    imageStatus.setImageResource(R.drawable.load_uploaded);
                    break;
            }

            textViewDesc.setText(selectedItem.getDesc());
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

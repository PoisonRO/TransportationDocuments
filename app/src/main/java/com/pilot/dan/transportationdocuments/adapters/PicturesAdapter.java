package com.pilot.dan.transportationdocuments.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pilot.dan.transportationdocuments.R;
import com.pilot.dan.transportationdocuments.database.Picture;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by dan on 9/23/15.
 */
public class PicturesAdapter  extends BaseAdapter {

    private Activity                activity;
    private ArrayList<Picture>      data;
    private LayoutInflater          inflater;
    private Picture                 selectedItem;
    private ImageView               imageView;

    public PicturesAdapter(Activity activity,ArrayList<Picture> data)
    {
        this.activity = activity;
        this.data = new ArrayList<Picture>();
        if (data != null)
            this.data.addAll(data);
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void updateData(ArrayList<Picture> data) {
        this.data.clear();
        this.data.addAll(data);
    }

    @Override
    public int getCount() {
        int ret = data.size();
        if (ret<=0)
            return 0;
        else
            return ret;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View  view = inflater.inflate(R.layout.adapter_pictures,null);

        TextView textView3 = (TextView)view.findViewById(R.id.textView3);
        imageView = (ImageView)view.findViewById(R.id.imageView);


        if (data.size() > 0) {
            selectedItem = data.get(position);

            try {

                //FileInputStream inputStream = openFileInput(selectedItem.getPic_location(),Context.MODE_PRIVATE);
                FileInputStream inputStream = activity.getApplicationContext().openFileInput(selectedItem.getPic_location());
                Bitmap bmp = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bmp);

                textView3.setText(bmp.getWidth()+" x " + bmp.getHeight());
                inputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return view;
    }
}

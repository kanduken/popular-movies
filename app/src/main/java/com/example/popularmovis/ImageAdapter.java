package com.example.popularmovis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Gallery.LayoutParams;

import com.example.popularmovis.R;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Bhalla on 2/29/2016.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    public ArrayList<String> images = new ArrayList<String>();

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
       return images.size();
    }

    public Object getItem(int position) {
        return images.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = (ImageView) convertView;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            // if it's not recycled, initialize some attributes

            View view= inflater.inflate(R.layout.grid_images, null, true);
            imageView = (ImageView) view.findViewById(R.id.image);


        }


//        ArrayList<String> apple = new ArrayList<>();
//        apple = images;
        if(images.get(position)==null) {
            imageView.setImageResource(R.mipmap.poster_not_available);
        }
        else{
            Picasso.with(mContext).load(images.get(position)).into(imageView);
        }



        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7
    };
    public void addItem(String url){
        images.add(url);
    }

    public void clearItems() {
        images.clear();
    }
}
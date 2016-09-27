package com.randap.popularmovis.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.randap.popularmovis.Model.Movie;
import com.randap.popularmovis.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by shenanigan on 21/9/16.
 */
public class RecyclerImageAdapter extends RecyclerView.Adapter<RecyclerImageAdapter.ViewHolder> {
    public static MovieClickHandler mMovieClickHandler;
    public ArrayList<Movie> imageUrlList;
    private Context mContext;

    public RecyclerImageAdapter(Context mContext, ArrayList<Movie> imageUrlList, MovieClickHandler ch) {
        this.imageUrlList = imageUrlList;
        this.mContext = mContext;
        mMovieClickHandler = ch;
    }

    @Override
    public RecyclerImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_images, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Picasso.with(mContext).load(imageUrlList.get(position).getPosterPath()).into(holder.mImageView);

    }

    @Override
    public int getItemCount() {
        return imageUrlList.size();
    }

    public interface MovieClickHandler {
        void getTheClickPosition(int position, Bitmap bitmap);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;


        public ViewHolder(View v) {
            super(v);
            mImageView = (ImageView) v.findViewById(R.id.image);
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mImageView.buildDrawingCache();
                    Bitmap bitmap = mImageView.getDrawingCache();
                    int adapterPosition = getAdapterPosition();
                    mMovieClickHandler.getTheClickPosition(adapterPosition, bitmap);
                }
            });

        }

    }
}

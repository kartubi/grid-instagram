package com.akdroid.gridinstagram.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akdroid.gridinstagram.R;
import com.akdroid.gridinstagram.activity.PicsActivity;
import com.akdroid.gridinstagram.model.Pics;
import com.akdroid.gridinstagram.rest.ApiEndPoint;
import com.squareup.picasso.Picasso;

import java.util.List;
public class PicsAdapter extends RecyclerView.Adapter<PicsAdapter.PicsViewHolder> {

    private List<Pics> picses;
    private int rowLayout;
    private Context context;


    PicsActivity picsActivity;



    public static class PicsViewHolder extends RecyclerView.ViewHolder {

        ImageView ivPics;
        TextView tvPicsTitle, tvPicsAuthor, tvPicsHits, tvPicsUpload;
        LinearLayout pics_item_layout;

        public PicsViewHolder(View v) {
            super(v);
            ivPics = (ImageView) v.findViewById(R.id.ivPics);
            pics_item_layout = (LinearLayout) v.findViewById(R.id.pics_item_layout);
        }
    }

    public PicsAdapter(List<Pics> picses, int rowLayout, Context context, PicsActivity picsActivity) {
        this.picsActivity = picsActivity;
        this.picses = picses;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public PicsAdapter.PicsViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new PicsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PicsViewHolder holder, final int position) {
        Picasso.with(context)
                .load(picses.get(position).link)
                .fit()
                .centerCrop()
                .into(holder.ivPics);

        String a = picsActivity.getheigth();
        int b = Integer.valueOf(a);
        holder.ivPics.getLayoutParams().height = b/3;
        holder.pics_item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "OnClick : " + String.valueOf(picses.get(position).id_pics), Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public int getItemCount() {
        return picses.size();
    }


}
package com.akdroid.gridinstagram.activity;


import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;

import android.widget.ImageView;


import com.akdroid.gridinstagram.R;
import com.akdroid.gridinstagram.adapter.PicsAdapter;
import com.akdroid.gridinstagram.model.Pics;
import com.akdroid.gridinstagram.rest.ApiEndPoint;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;


import java.util.List;

public class PicsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private static final String TAG = PicsActivity.class.getSimpleName();
    String screenHeight, screenWidth;
    ImageView ivPics;
    SwipeRefreshLayout srlPics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pics);

        getALLPics();

        ivPics = (ImageView)findViewById(R.id.ivPics);
        srlPics = (SwipeRefreshLayout)findViewById(R.id.srfPics);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        screenWidth = String.valueOf(width);
        screenHeight = String.valueOf(height);


        srlPics.setOnRefreshListener(this);
        srlPics.post(new Runnable() {
            @Override
            public void run() {
                srlPics.setRefreshing(true);
                getALLPics();
            }
        });

    }

    @Override
    public void onRefresh(){
        getALLPics();
    }

    public void getALLPics() {
        AndroidNetworking.get(ApiEndPoint.BASE_URL + ApiEndPoint.GET_PICS)
                .addPathParameter("id", "1")
                .setTag(this)
                .setPriority(Priority.LOW)
                .build()
                .getAsOkHttpResponseAndObjectList(Pics.class, new OkHttpResponseAndParsedRequestListener<List<Pics>>() {
                    @Override
                    public void onResponse(okhttp3.Response okHttpResponse, List<Pics> picses) {
                        if (okHttpResponse.isSuccessful()) {
                            if (picses.get(0).status == null){
                                final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.pics_rc);
                                int numberOfColumns = 3;
                                recyclerView.setLayoutManager(new GridLayoutManager(PicsActivity.this, numberOfColumns));
                                recyclerView.setAdapter(new PicsAdapter(picses, R.layout.list_item_pics, getApplicationContext(), PicsActivity.this));
                            }else if (picses.get(0).status.equals("error")){

                            }
                            srlPics.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        srlPics.setRefreshing(false);
                    }
                });
    }

    public String getHeight(){
        return screenWidth;
    }



}

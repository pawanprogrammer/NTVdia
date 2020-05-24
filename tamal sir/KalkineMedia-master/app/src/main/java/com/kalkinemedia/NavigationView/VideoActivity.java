package com.kalkinemedia.NavigationView;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.kalkinemedia.Adapter.VideoAdapter;
import com.kalkinemedia.POJO.Video;
import com.kalkinemedia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VideoActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String url = "https://kalkinemedia.com/mobileapp/km/fetchvideo.php";
    private List<Video> arrayList = new ArrayList<>();
    VideoAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        getSupportActionBar().setTitle("Kalkine Video");
        recyclerView = findViewById(R.id.video_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(VideoActivity.this, 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        myAdapter = new VideoAdapter(VideoActivity.this,arrayList);
        swipeRefreshLayout = findViewById(R.id.video_swipe);
        //default app open
        videofetch();
        //when pull down
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                arrayList.clear();
                videofetch();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void videofetch() {
        //progressBar.setVisibility(View.VISIBLE);
        JsonArrayRequest ja = new JsonArrayRequest(1, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Toast.makeText(BabyActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                        //to retrive all data from array
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jo = response.getJSONObject(i);
                                String code = jo.getString("video");
                                String title = jo.getString("title");
                                arrayList.add(new Video(title, code));
                                recyclerView.setAdapter(myAdapter);
                                //progressBar.setVisibility(View.GONE);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //progressBar.setVisibility(View.GONE);
                    }
                });
        RequestQueue rq = Volley.newRequestQueue(VideoActivity.this);
        rq.add(ja);
    }
}

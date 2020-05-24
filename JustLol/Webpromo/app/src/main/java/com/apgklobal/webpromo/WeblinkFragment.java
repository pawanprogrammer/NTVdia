package com.apgklobal.webpromo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeblinkFragment extends Fragment {
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    String url = "http://searchkero.com/aa/linkfetch.php";
    ArrayList<String> arrayList = new ArrayList<>();
    MyAdapter myAdapter;
    ProgressBar progressBar;

    public WeblinkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = v.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        progressBar = v.findViewById(R.id.progressBar);
        myAdapter = new MyAdapter(getActivity(), arrayList);
        swipeRefreshLayout = v.findViewById(R.id.swipe);
        //default app open
        linkfetch();
        //when pull down
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                arrayList.clear();
                progressBar.setVisibility(View.GONE);
                linkfetch();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return v;
    }

    private void linkfetch() {
        progressBar.setVisibility(View.VISIBLE);
        JsonArrayRequest ja = new JsonArrayRequest(1, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                        //to retrive all data from array
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jo = response.getJSONObject(i);
                                String link = jo.getString("weblink");
                                arrayList.add(link);
                                recyclerView.setAdapter(myAdapter);
                                progressBar.setVisibility(View.GONE);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                    }
                });
        RequestQueue rq = Volley.newRequestQueue(getActivity());
        rq.add(ja);

    }

}

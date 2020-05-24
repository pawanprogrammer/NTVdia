package com.kalkinemedia.Dashboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.kalkinemedia.API.Api;
import com.kalkinemedia.Adapter.MyAdapter;
import com.kalkinemedia.Helper.RecyclerItemTouchHelper;
import com.kalkinemedia.NavigationView.AdvertiseActivity;
import com.kalkinemedia.NavigationView.DisclaimerActivity;
import com.kalkinemedia.NavigationView.PrivacyPolicyActivity;
import com.kalkinemedia.NavigationView.VideoActivity;
import com.kalkinemedia.NavigationView.ViewBookmarkActivity;
import com.kalkinemedia.POJO.Book;
import com.kalkinemedia.POJO.Mydata;
import com.kalkinemedia.R;
import com.kalkinemedia.realm.RealmController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        SwipeRefreshLayout.OnRefreshListener,
        RecyclerItemTouchHelper.RecyclerItemTouchHelperListener,
        BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

    RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MyAdapter myAdapter;
    public static final int PAGE_START = 1;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage = 10;
    private boolean isLoading = false;
    int itemCount = 0;
    private String[] post_image;
    private String[] post_title;
    private String[] post_by;
    private String[] post_date;
    private String[] post_content;
    private String [] format_date;
    Call<List<Mydata>> call;
    SwipeRefreshLayout swipe;
    private  List<Mydata> heroList;
    private ProgressBar progressBar;
    public static Toolbar toolbar;
    private SliderLayout mDemoSlider;
    String code_url = "https://kalkinemedia.com/mobileapp/km/newcode.php";
    private ProgressDialog progressDialog;
    String scode;
    private Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDemoSlider = (SliderLayout)findViewById(R.id.slider);


        //for auto imageslider
        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Kalkine Media",R.drawable.nv_icon);
        file_maps.put("Stocks Updates",R.drawable.nv_icon);
        file_maps.put("Financial News",R.drawable.nv_icon);
        file_maps.put("Get Free Reports", R.drawable.nv_icon);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);

        //toolbar.setVisibility(View.GONE);
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        SharedPreferences sp = getSharedPreferences("userdata", 0);
        String myname = sp.getString("myname", null);


        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        swipe = findViewById(R.id.swipe);
        swipe.setOnRefreshListener(this);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        progressBar=findViewById(R.id.progressbar);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        myAdapter = new MyAdapter((Callback<List<Mydata>>) call, post_image,
                post_title, post_by, post_date, post_content);
        fetchnews();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView nv = (NavigationView) findViewById(R.id.nav_view);
        // get menu from navigationView
        Menu menu = nv.getMenu();

        // find MenuItem you want to change
        MenuItem nav_name = menu.findItem(R.id.nav_name);

        // set new title to the MenuItem
        nav_name.setTitle(myname);
        nv.setNavigationItemSelectedListener(this);

    }




    private void fetchnews() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Api api = retrofit.create(Api.class);

        call = api.getHeroes();

        call.enqueue(new Callback<List<Mydata>>() {
            @Override
            public void onResponse(Call<List<Mydata>> call, Response<List<Mydata>> response) {
                heroList= response.body();

//for using the pagination
                //Creating an String array for the ListView
                post_image = new String[heroList.size()];
                post_title = new String[heroList.size()];
                post_by = new String[heroList.size()];
                post_date = new String[heroList.size()];
                post_content = new String[heroList.size()];

                //looping through all the heroes and inserting the names inside the string array
                for (int i = 0; i < heroList.size(); i++) {
                    post_image[i] = heroList.get(i).getImg_link();
                    post_title[i] = heroList.get(i).getPost_title();
                    post_by[i] = heroList.get(i).getPost_author();
                    post_date[i] = String.valueOf(heroList.get(i).getPost_date()).substring(0,10);
                    post_content[i] = heroList.get(i).getPost_content();
                }

                MyAdapter myAdapter = new MyAdapter(this, post_image,
                        post_title, post_by, post_date, post_content);

                recyclerView.setAdapter(myAdapter);

                ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0,
                        ItemTouchHelper.LEFT, DashboardActivity.this) {
                    /*@Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder viewHolder1) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                        Intent intent = new Intent(viewHolder.itemView.getContext(), DetailActivity.class);
                        //to send the manually data using android intent
                        intent.putExtra("post_image", post_image);
                        intent.putExtra("post_title", post_title);
                        intent.putExtra("post_date", post_date);
                        intent.putExtra("post_by", post_by);
                        intent.putExtra("position", i);
                        //intent.putExtra("post_content", post_content);
                        viewHolder.itemView.getContext()
                                .startActivity(intent);
                    }*/
                };
                new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        /*        ItemTouchHelper.SimpleCallback itemTouchHelperCallback1 = new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.RIGHT ) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        // Row is swiped from recycler view
                        // remove it from adapter
                        String.valueOf(direction);
                    }

                    @Override
                    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    }
                };

                // attaching the touch helper to recycler view
                new ItemTouchHelper(itemTouchHelperCallback1).attachToRecyclerView(recyclerView);*/
                progressBar.setVisibility(View.GONE);
                //displaying the string array into listview
                /*recyclerView.setAdapter(new ArrayAdapter<String>(
                        getApplicationContext(),
                        android.R.layout.simple_list_item_1, heroes));*/

            }

            @Override
            public void onFailure(Call<List<Mydata>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            fetchnews();
            Toast.makeText(this, "referesh done", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        SharedPreferences sp = getSharedPreferences("userdata", 0);
        if (id == R.id.nav_name) {
            item.setTitle(sp.getString("myname", null));
            // Handle the camera action
        } else if (id == R.id.nav_markets) {

        } else if (id == R.id.nav_bookmarks) {
            Intent bookmarks = new Intent(DashboardActivity.this, ViewBookmarkActivity.class);
            startActivity(bookmarks);

        } else if (id == R.id.nav_video) {
            Intent video = new Intent(DashboardActivity.this, VideoActivity.class);
            startActivity(video);
            //finish();
        } else if (id == R.id.nav_refer) {
            //generateCode();
            int randomPin   =(int)(Math.random()*9000)+1000;
            final String code  ="Kalkine"+String.valueOf(randomPin);
            scode = code;
            Toast.makeText(this, code, Toast.LENGTH_SHORT).show();
            //before inflating the custom alert dialog layout, we will get the current activity viewgroup
            ViewGroup viewGroup = findViewById(android.R.id.content);

            //then we will inflate the custom alert dialog xml that we created
            View dialogView = LayoutInflater.from(this).inflate(R.layout.sharecode, viewGroup, false);
            Button btn_refer = dialogView.findViewById(R.id.btn_refer);
            btn_refer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent refer = new Intent(Intent.ACTION_SEND);
                    refer.putExtra(Intent.EXTRA_TEXT, "Just Download Kalkine Media Application for Stocks Updates and Use referral Code: "
                            +code+" for free Stocks Research Reports"+"\nHere the Application Link "+
                            "https://play.google.com/store/apps/details?id=com.kalkinemedia");
                    refer.setType("text/plain");
                    startActivity(Intent.createChooser(refer, "Share Application Via:"));

                    sendcodetoserver();
                }
            });

            //Now we need an AlertDialog.Builder object
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            //setting the view of the builder to our custom view that we already inflated
            builder.setView(dialogView);

            //finally creating the alert dialog and displaying it
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else if (id == R.id.nav_advertise) {
            Intent advertise = new Intent(getApplicationContext(), AdvertiseActivity.class);
            startActivity(advertise);
        }
        else if (id == R.id.nav_privacy) {
            Intent privacy = new Intent(getApplicationContext(), PrivacyPolicyActivity.class);
            startActivity(privacy);
        }else if (id == R.id.nav_disclaimer) {
            Intent disclaimer = new Intent(getApplicationContext(), DisclaimerActivity.class);
            startActivity(disclaimer);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void sendcodetoserver() {

        StringRequest sr = new StringRequest(1,
                code_url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //progressDialog.dismiss();

                        /*Toast.makeText(DashboardActivity.this, response,
                                Toast.LENGTH_SHORT).show();*/

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                SharedPreferences sp = getSharedPreferences("userdata", 0);
                String sname = sp.getString("myname", null);
                String semail = sp.getString("myemail", null);
                String smobile = sp.getString("mymobile", null);
                Map<String, String> map = new HashMap<>();
                map.put("nameid", sname);
                map.put("emailid", semail);
                map.put("mobileid", smobile);
                map.put("codeid", scode);
                return map;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(DashboardActivity.this);
        rq.add(sr);
    }

    @Override
    public void onRefresh() {
//myAdapter.clear();
        fetchnews();
        swipe.setRefreshing(false);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        Intent intent = new Intent(viewHolder.itemView.getContext(), DetailActivity.class);
        //to send the manually data using android intent
        intent.putExtra("post_image", post_image);
        intent.putExtra("post_title", post_title);
        intent.putExtra("post_date", post_date);
        intent.putExtra("post_by", post_by);
        intent.putExtra("position", position);
        //intent.putExtra("post_content", post_content);
        viewHolder.itemView.getContext()
                .startActivity(intent);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void showprogress() {
        progressDialog.setTitle("Please wait.....");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgress(0);
        progressDialog.show();
    }
}

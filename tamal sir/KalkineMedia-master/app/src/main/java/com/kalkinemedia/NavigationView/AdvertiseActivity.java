package com.kalkinemedia.NavigationView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.kalkinemedia.Authentication.ProfileActivity;
import com.kalkinemedia.Dashboard.DashboardActivity;
import com.kalkinemedia.MyPreferences;
import com.kalkinemedia.R;

import java.util.HashMap;
import java.util.Map;

public class AdvertiseActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{
    private SliderLayout mDemoSlider;
    private Button btn_quote, btn_download;
    private ProgressDialog progressDialog;
    private String quote_url ="https://kalkinemedia.com/mobileapp/km/newquote.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertise);
        progressDialog = new ProgressDialog(this);
        getSupportActionBar().setTitle("Advertise With Us");
        btn_download=findViewById(R.id.btn_download);
        btn_quote=findViewById(R.id.btn_quote);
        btn_quote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showprogress();
                requestquote();
            }
        });
        btn_download = findViewById(R.id.btn_download);
        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent download = new Intent(Intent.ACTION_VIEW);
                download.setData(Uri.parse("https://kalkinemedia.com/wp-content/uploads/2019/05/Media-pack.pdf"));
                startActivity(download);
            }
        });
        mDemoSlider = (SliderLayout)findViewById(R.id.slider);

        //for auto imageslider
        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Advertise With Us",R.drawable.nv_icon);
        file_maps.put("Discover The Benefits",R.drawable.nv_icon);
        file_maps.put("Reaching Investors In Right Context",R.drawable.nv_icon);
        file_maps.put("Our Daily Users", R.drawable.nv_icon);

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

    }

    private void showprogress() {
        progressDialog.setTitle("Please wait.....");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgress(0);
        progressDialog.show();
    }

    private void requestquote() {

        StringRequest sr = new StringRequest(1,
                quote_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Toast.makeText(AdvertiseActivity.this, response,
                                Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
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
                return map;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(AdvertiseActivity.this);
        rq.add(sr);
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
}

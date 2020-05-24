package com.kalkinemedia.API;

import com.kalkinemedia.POJO.Mydata;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
            String BASE_URL = "https://www.kalkinemedia.co.uk/old/km/";

    @GET("post.php")
    Call<List<Mydata>> getHeroes();
}

package com.example.googlebookapi.impl;

import com.example.googlebookapi.model.BookVolumes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface GoogleApi {

    String BASE_URL = "https://www.googleapis.com/";

    @Headers("Content-Type: application/json")
    @GET("books/v1/volumes")
    Call<BookVolumes> create(@Query("q") String searchItem);

}

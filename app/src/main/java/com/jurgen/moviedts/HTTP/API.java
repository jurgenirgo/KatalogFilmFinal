package com.jurgen.moviedts.HTTP;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {
    public static final String IMAGE_URL = "https://image.tmdb.org/t/p/w500";
    public static final String LANG = "en-US";
    public static final String API_KEY = "f86b1c0a6ae19306836d34ed10a8c269";
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String IMG_DUMMY = "http://thecompletepackagellc.com/wp-content/uploads/2012/07/placeholder_21-470x352.jpg";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build();
        }
        return retrofit;
    }
}

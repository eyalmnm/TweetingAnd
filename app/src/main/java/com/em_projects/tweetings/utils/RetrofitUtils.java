package com.em_projects.tweetings.utils;


import retrofit2.Response;

/**
 * Created by Sergey Ostrovsky
 * on 7/30/18
 */
public class RetrofitUtils {

    public static String handleErrorResponse(Response response) {
        String rawResponse = response.raw().message();
        return rawResponse;
    }

}

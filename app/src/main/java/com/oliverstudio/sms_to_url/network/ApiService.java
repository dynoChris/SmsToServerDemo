package com.oliverstudio.sms_to_url.network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    //https://bot.milliononline.ru/bots/tgbots/multifon_bot/send.php?user_id=-323474111&get_response_status=1&$disable_notification=false&content_type=text&text=TEXT_SAMOI_SMSKI
    @GET("bots/tgbots/multifon_bot/send.php?user_id=-323474111&get_response_status=1&$disable_notification=false&content_type=text")
    Call<String> sendMessage(
            @Query("text") String message
    );

    //https://myserver1.com/v1/postsms
    @POST("here put url for transfer sms")
    Call<String> postMessage(
            @Body String message
    );

}
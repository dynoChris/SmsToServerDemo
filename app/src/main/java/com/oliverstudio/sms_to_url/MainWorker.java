package com.oliverstudio.sms_to_url;

import android.content.Context;

import com.oliverstudio.sms_to_url.network.ApiService;
import com.oliverstudio.sms_to_url.network.NetworkUtils;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import retrofit2.Call;

public class MainWorker extends Worker {

    public static final String TAG = "main_worker";

    private ApiService mApiService;

    public MainWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

    }

    @NonNull
    @Override
    public Result doWork() {
        String message = getInputData().getString("message");

        mApiService = NetworkUtils.getApiService();

        sendRequest(message);

        return Result.success();
    }

    private void sendRequest(String message) {
        Call<String> call = mApiService.sendMessage(message);
        try {
            call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
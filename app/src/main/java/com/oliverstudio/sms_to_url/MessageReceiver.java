package com.oliverstudio.sms_to_url;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;

import androidx.preference.PreferenceManager;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class MessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data = intent.getExtras();
        Object[] pdus = (Object[]) data.get("pdus");
        for (int i = 0; i < pdus.length; i++) {
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
            String incomePhoneNumber = smsMessage.getDisplayOriginatingAddress();
            String message = smsMessage.getMessageBody();
            String fullMessage = "phoneNumber: " + incomePhoneNumber + ", " + "message: " + message;
            sendRequest(context, fullMessage);
        }
    }

    private void sendRequest(Context context, String message) {
        Constraints constraintRequiredNetwork = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MainWorker.class)
                .addTag(MainWorker.TAG)
                .setInputData(new Data.Builder().putString("message", message).build())
                .setConstraints(constraintRequiredNetwork)
                .build();
        WorkManager.getInstance(context).enqueue(oneTimeWorkRequest);
    }

}
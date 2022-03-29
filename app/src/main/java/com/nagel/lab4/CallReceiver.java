package com.nagel.lab4;

import static android.telephony.TelephonyManager.EXTRA_STATE_IDLE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.CallLog;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CallReceiver extends BroadcastReceiver {
    private static final String TAG = "CallReceiver";
    MainActivity mainActivity;
    public CallReceiver(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
    public MainActivity getMainActivity() {
        return mainActivity;
    }
    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, intent.getAction());
        String phoneState = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        String phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

        String time = "";

        Cursor cursor = context.getContentResolver().query(
                CallLog.Calls.CONTENT_URI,
                null,
                null,
                null,
                null);
        int dateValue = cursor.getColumnIndex(CallLog.Calls.DATE);
        while(cursor.moveToNext()){
            String callDate = cursor.getString(dateValue);
            Date callInfo = new Date(Long.parseLong(callDate));
            SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
            time = timeFormatter.format(callInfo);
        }

        Log.i(TAG, "State: " + phoneState + "\n Number: " +phoneNumber + "\n Time: " + time);
        if(phoneNumber != null){
            mainActivity.addPhoneNumberToList(phoneNumber+","+time);
        }


        /*
        Log.i(TAG, "State: " + phoneState + " Number: " + phoneNumber);
        if (phoneNumber != null){
            mainActivity.addPhoneNumberToList(phoneNumber);
        }*/
    }
}












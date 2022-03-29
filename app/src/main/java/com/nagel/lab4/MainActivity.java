package com.nagel.lab4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver callReceiver;
    private List<String> callList = new ArrayList<>();
    //private ArrayAdapter<String> adapter;
    CallAdapter callAdapter;
    String[] permissions = {
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_CALL_LOG
    };

    private boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void onPermission() {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission)
                    == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Access to " + permission + " given!", Toast.LENGTH_SHORT).show();
            } else {
                requestPhonePermission();
            }
        }

    }

    private void requestPhonePermission() {
        //Permission has not been granted and must be requested
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                Snackbar.make(findViewById(R.id.mainLayout), Manifest.permission.CALL_PHONE + " access needed because ...", Snackbar.LENGTH_INDEFINITE)
                        .setAction(android.R.string.ok, view ->
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{permission}, 100)).show();
            } else {
                Toast.makeText(this, "Need access to " + permission + " !", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(MainActivity.this, new String[]
                        {permission}, 100);
            }
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        callReceiver = new CallReceiver(this);
        IntentFilter filter = new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        registerReceiver(callReceiver, filter);
        //adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,callList);
        //ListView numbersList = findViewById(R.id.numbers_listview);
        //numbersList.setAdapter(adapter);
        RecyclerView recyclerView = findViewById(R.id.myRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        callAdapter = new CallAdapter(this, callList);
        recyclerView.setAdapter(callAdapter);

        onPermission();
        if(!hasPermissions(this, permissions))ActivityCompat.requestPermissions(this, permissions, 1);

    }

    //lisab telefoni numberi nimekirja
    public void addPhoneNumberToList(String number) {

        callList.add(number);

        //adapter.notifyDataSetChanged();
        Log.i("TAG", number);
        callAdapter.notifyDataSetChanged();
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(callReceiver);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, permissions[i] + " denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}













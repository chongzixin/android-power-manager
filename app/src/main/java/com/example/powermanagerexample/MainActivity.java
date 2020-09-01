package com.example.powermanagerexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.os.PowerManager;

public class MainActivity extends AppCompatActivity {
    public static PowerManager powerManager;
    public static boolean isWhitelisted;
    public static final String PACKAGE_NAME = "com.example.powermanagerexample";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // get whitelist status from power manager
        powerManager = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
        isWhitelisted = powerManager.isIgnoringBatteryOptimizations(PACKAGE_NAME);

        // set label to show whitelist status
        String whitelistStatus = isWhitelisted ? "whitelisted" : "not whitelisted";
        TextView textview = findViewById(R.id.txt_whitelist_status);
        textview.setText(whitelistStatus);

        // change button text according to whitelist status
        Button btnWhitelist = findViewById(R.id.btnAddWhitelist);
        if(isWhitelisted) btnWhitelist.setText("Remove from Whitelist");
        else btnWhitelist.setText("Add to Whitelist");
    }

    public void pressWhitelist(View view) {
        Intent intent = new Intent();
        if (powerManager.isIgnoringBatteryOptimizations(PACKAGE_NAME))
            intent.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
        else {
            // show the intent to add this app to whitelist
            intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + PACKAGE_NAME));
        }
        startActivity(intent);
    }
}
package com.myhexaville.overlayapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MainActivity";
    public static final int RC_OVERLAY_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (!Settings.canDrawOverlays(this)) {
            Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            myIntent.setData(Uri.parse("package:" + getPackageName()));
            startActivityForResult(myIntent, RC_OVERLAY_PERMISSION);
        } else {
            startService(new Intent(this, NoiseService.class).putExtra("size", 5000));
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            startService(new Intent(this, NoiseService.class));

        }
    }
}
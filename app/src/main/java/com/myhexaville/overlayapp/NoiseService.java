package com.myhexaville.overlayapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class NoiseService extends Service {
    private static final String LOG_TAG = "NoiseService";
    private WindowManager windowManager;
    private WaterSceneView noise;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        noise = new WaterSceneView(this);

        Display display = windowManager.getDefaultDisplay();
        int height = display.getHeight() + getNavBarHeight();

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(-1, height, 0, 0, WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                        | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                        | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, PixelFormat.TRANSLUCENT);

        windowManager.addView(noise, params);

        Log.d(LOG_TAG, "onCreate: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (noise != null) windowManager.removeView(noise);
    }

    private int getNavBarHeight() {
        Resources resources = getBaseContext().getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId)*2;
        }
        return 0;
    }
}
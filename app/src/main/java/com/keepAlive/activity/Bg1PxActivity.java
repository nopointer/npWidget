package com.keepAlive.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.keepAlive.utils.ServiceAliveUtils;
import com.keepAlive.service.BgCoreService;

public class Bg1PxActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window mWindow = getWindow();
        mWindow.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager.LayoutParams attrParams = mWindow.getAttributes();
        attrParams.x = 0;
        attrParams.y = 0;
        attrParams.height = 500;
        attrParams.width = 40;
        mWindow.setAttributes(attrParams);
        ScreenManager.getInstance(this).setSingleActivity(this);
    }


    @Override
    protected void onDestroy() {
        if (!ServiceAliveUtils.isAppALive(this, getPackageName())) {
            Intent intentAlive = new Intent(this, BgCoreService.class);
            startService(intentAlive);
        }
        super.onDestroy();

    }
}

package com.keepAlive.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.keepAlive.utils.ServiceAliveUtils;

public class GuardService extends Service {


    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            log("GuardService:建立连接");
            boolean isGuardAlive = ServiceAliveUtils.isServiceAlice(GuardService.this, BgCoreService.class);
            if (!isGuardAlive) {
                startService(new Intent(GuardService.this, BgCoreService.class));
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            log("GuardService:断开连接");
            startService(new Intent(GuardService.this, KeepService.class));
            bindService(new Intent(GuardService.this, KeepService.class), serviceConnection, BIND_AUTO_CREATE);
        }
    };


    @Override
    public IBinder onBind(Intent intent) {
        log("onBind?");
        return new KeepAliveConnection.Stub() {

        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        log("onStartCommand?");
        bindService(new Intent(this, KeepService.class), serviceConnection, BIND_AUTO_CREATE);
        return START_STICKY;
    }


    private static void log(String message) {
        Log.e("GuardService", message);
    }
}

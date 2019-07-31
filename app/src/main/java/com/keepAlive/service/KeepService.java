package com.keepAlive.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.keepAlive.utils.ServiceAliveUtils;

/**
 * 保存service
 */
public class KeepService extends Service {


    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            log("KeepService:建立连接");
            boolean isGuardAlive = ServiceAliveUtils.isServiceAlice(KeepService.this, BgCoreService.class);
            if (!isGuardAlive) {
                startService(new Intent(KeepService.this, BgCoreService.class));
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            log("KeepService:断开连接");
            startAndBindGuard();
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
        startAndBindGuard();
        return START_STICKY;
    }


    /**
     * 启动并且绑定guard服务
     */
    private void startAndBindGuard() {
        startService(new Intent(KeepService.this, GuardService.class));
        bindService(new Intent(KeepService.this, GuardService.class), serviceConnection, BIND_AUTO_CREATE);
    }


    private static void log(String message) {
        Log.e("keepAlive", message);
    }


}

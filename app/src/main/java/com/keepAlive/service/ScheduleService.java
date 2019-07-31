package com.keepAlive.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.keepAlive.utils.Logger;
import com.keepAlive.utils.ServiceAliveUtils;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ScheduleService extends JobService {

    private static final String TAG = "ScheduleService";

    @Override
    public boolean onStartJob(JobParameters params) {
        boolean isServiceRunning = ServiceAliveUtils.isServiceAlice(this, BgCoreService.class);
        if (!isServiceRunning) {
            Intent i = new Intent(this, BgCoreService.class);
            startService(i);
            Logger.d(TAG, "ScheduleService启动了BgCoreService");
        }
        jobFinished(params, false);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}

package com.community.jboss.leadmanagement;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class CallRecordingService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startRecording();
        return super.onStartCommand(intent, flags, startId);
    }

    private void startRecording(){
        stopSelf(); //TODO Implement call recording
    }
}

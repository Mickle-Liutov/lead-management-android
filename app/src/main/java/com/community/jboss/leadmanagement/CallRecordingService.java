package com.community.jboss.leadmanagement;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CallRecordingService extends Service {
    MediaRecorder recorder;
    File audiofile;
    boolean recordstarted = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.v("Service_my_unique","Service bind!");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("Service_my_unique","Service started!");
        String out = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss").format(new Date());
        File sampleDir = new File(Environment.getExternalStorageDirectory(), "/TestRecordingDasa1");
        if (!sampleDir.exists()) {
            sampleDir.mkdirs();
        }
        String file_name = "Record";
        try {
            audiofile = File.createTempFile(file_name, ".m4a", sampleDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();

        recorder = new MediaRecorder();
//                          recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);

        recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.HE_AAC);
        recorder.setAudioEncodingBitRate(48000);
        recorder.setAudioSamplingRate(16000);
        recorder.setOutputFile(audiofile.getAbsolutePath());
        try {
            recorder.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        recorder.start();
        recordstarted = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("Service_my_unique","Service command!");
        if(intent.getStringExtra("Command")!=null && intent.getStringExtra("Command").equals("STOP") && recordstarted){
            Log.v("Service_my_unique","Service shutting down!");
            try {
                recorder.stop();
            }
            catch (IllegalStateException e){
                Log.v("Service_my_unique","Service IllegalStaetException!");
                e.printStackTrace();
            }
            catch (RuntimeException e){
                Log.v("Service_my_unique","Service RuntimeException!");
                e.printStackTrace();
            }
            recorder.release();
            recordstarted = false;
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

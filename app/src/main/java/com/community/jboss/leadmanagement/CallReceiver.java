package com.community.jboss.leadmanagement;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.community.jboss.leadmanagement.main.contacts.editcontact.EditContactActivity;

public class CallReceiver extends BroadcastReceiver {
    private static final int ID = 47981;

    private String number;
    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!TextUtils.equals(intent.getAction(), "android.intent.action.PHONE_STATE")) {
            return;
        }

        final Bundle extras = intent.getExtras();
        if (extras == null) return;

        final String state = extras.getString(TelephonyManager.EXTRA_STATE);
        if (state == null) return;

        final String callerNum = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
        if (callerNum == null) return;

        mContext = context;
        this.number = callerNum;

        if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            showNotification();
        } else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
            hideNotification();
        } else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
            showNotification();
        }
    }

    private void hideNotification() {
        final NotificationManager manager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.cancel(ID);
        }
        final Intent recorderIntent = new Intent(mContext, CallRecordingService.class);
        if(isMyServiceRunning(CallRecordingService.class)) {
            recorderIntent.putExtra("Command", "STOP");
            mContext.startService(recorderIntent);
        }
    }

    private void showNotification() {

        final Intent notificationIntent = new Intent(mContext, EditContactActivity.class);
        String CHANNEL_ID = "lead-management-ch";

        notificationIntent.putExtra(
                EditContactActivity.INTENT_EXTRA_CONTACT_NUM, number);

        final PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        final Intent recorderIntent = new Intent(mContext, CallRecordingService.class);

        PendingIntent recorderPendingIntent = PendingIntent.getService(mContext, 0, recorderIntent, 0);

        final NotificationCompat.Builder notification = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.drawable.ic_call_black_24dp)
                .addAction(R.drawable.ic_settings_black_24dp,"Record call",recorderPendingIntent)
                .setContentTitle("Call in Progress")
                .setTicker("Lead Management")
                .setContentIntent(contentIntent)
                .setContentText("Number: " + number)
                .setChannelId(CHANNEL_ID);

        final NotificationManager manager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.cancel(ID);
            // check build version
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = "Lead-Management-Channel";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                manager.createNotificationChannel(mChannel);
            }
            manager.notify(ID, notification.build());
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}

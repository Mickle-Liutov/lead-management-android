package com.community.jboss.leadmanagement.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.community.jboss.leadmanagement.R;
import com.community.jboss.leadmanagement.main.MainActivity;
import com.community.jboss.leadmanagement.main.contacts.editcontact.EditContactActivity;

public class WidgetAddContact extends AppWidgetProvider {

    public static final String WIDGET_BUTTON = "com.community.jboss.leadmanagement.WIDGET_BUTTON";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (WIDGET_BUTTON.equals(intent.getAction())) {
            Intent mainIntent = new Intent(context,MainActivity.class);
            Intent addContactIntent = new Intent(context,EditContactActivity.class);
            context.startActivity(mainIntent);
            context.startActivity(addContactIntent);
            Log.v("LeadManagement","OpeningActivity");
        }
        Log.v("LeadManagement","onReceive");
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Intent intent = new Intent(WIDGET_BUTTON);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        for (int appWidgetID : appWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_add_contact);
            remoteViews.setOnClickPendingIntent(R.id.add_contact_button,pendingIntent );

            appWidgetManager.updateAppWidget(appWidgetID, remoteViews);

        }
        Log.v("LeadManagement","onUpdate");
    }
}

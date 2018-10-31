package com.community.jboss.leadmanagement.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.community.jboss.leadmanagement.R;

public class WidgetRecentContacts extends AppWidgetProvider {

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(
                    context.getPackageName(),
                    R.layout.widget_recent_contacts
            );
            Intent intent = new Intent(context, RemoteViewsService.class);
            views.setRemoteAdapter(R.id.contacts_list, intent);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.contacts_list);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}

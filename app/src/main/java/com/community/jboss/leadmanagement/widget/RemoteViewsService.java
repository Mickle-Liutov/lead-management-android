package com.community.jboss.leadmanagement.widget;

import android.content.Intent;

public class RemoteViewsService extends android.widget.RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new com.community.jboss.leadmanagement.widget.RemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

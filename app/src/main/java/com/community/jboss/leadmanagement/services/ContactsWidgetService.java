package com.community.jboss.leadmanagement.services;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.community.jboss.leadmanagement.data.providers.ContactsWidgetDataProvider;

public class ContactsWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ContactsWidgetDataProvider(this);
    }
}


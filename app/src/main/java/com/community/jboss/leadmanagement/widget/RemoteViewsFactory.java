package com.community.jboss.leadmanagement.widget;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.community.jboss.leadmanagement.R;
import com.community.jboss.leadmanagement.data.daos.ContactDao;
import com.community.jboss.leadmanagement.data.daos.ContactNumberDao;
import com.community.jboss.leadmanagement.data.entities.Contact;
import com.community.jboss.leadmanagement.utils.DbUtil;

import java.util.List;

public class RemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    LiveData<List<Contact>> rawData;
    List<Contact> data = null;
    ContactDao dao;
    Observer dataObserver;

    public RemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {
        dao = DbUtil.contactDao(mContext.getApplicationContext());
        rawData = dao.getContacts();
        dataObserver = o -> data = rawData.getValue();
        if (rawData != null) {
        rawData.observeForever(dataObserver);
        }
    }

    @Override
    public void onDataSetChanged() {
        rawData = dao.getContacts();
        Log.v("LeadManagement","Data updated");
    }

    @Override
    public void onDestroy() {
        //rawData.removeObserver(dataObserver);
        //if (data != null) {
        //    data.clear();
        //}
    }

    @Override
    public int getCount() {
        if (data == null) return 0;
        else return data.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {

        if (i == AdapterView.INVALID_POSITION ||
                data == null) {
            return null;
        }

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_recent_contact_item);
        Contact contact = data.get(i);
        rv.setTextViewText(R.id.contact_name,contact.getName() + ":");
        rv.setTextViewText(R.id.contact_number,getNumber(contact));
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private String getNumber(Contact mContact) {
        final ContactNumberDao dao_numbers = DbUtil.contactNumberDao(mContext.getApplicationContext());
        return dao_numbers.getContactNumbers(mContact.getId()).get(0).getNumber();
    }

}

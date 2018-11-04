package com.community.jboss.leadmanagement;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.community.jboss.leadmanagement.main.contacts.editcontact.EditContactActivity;

import static com.community.jboss.leadmanagement.SettingsActivity.PREF_DARK_THEME;

public class ContactDialogActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(this);
        PermissionManager permManager = new PermissionManager(this, (Activity) this);
        String name = getIntent().getStringExtra("name");
        String number = getIntent().getStringExtra("number");
        TextView txtClose;
        TextView popupName;
        TextView contactNum;
        TextView mail;
        Button btnEdit;
        Button btnCall;
        Button btnMsg;
        LinearLayout layout;

        setContentView(R.layout.popup_detail);
        int position = getIntent().getIntExtra("position", 0);
        txtClose = findViewById(R.id.txt_close);
        btnEdit = findViewById(R.id.btn_edit);
        popupName = findViewById(R.id.popup_name);
        contactNum = findViewById(R.id.txt_num);
        btnCall = findViewById(R.id.btn_call);
        btnMsg = findViewById(R.id.btn_msg);
        mail = findViewById(R.id.popupMail);
        layout = findViewById(R.id.popupLayout);

        if (mPref.getBoolean(PREF_DARK_THEME, false)) {
            layout.setBackgroundColor(Color.parseColor("#303030"));
            popupName.setTextColor(Color.WHITE);
            contactNum.setTextColor(Color.WHITE);
            mail.setTextColor(Color.WHITE);
            txtClose.setBackground(getResources().getDrawable(R.drawable.ic_close_white));
        }

        popupName.setText(name);
        contactNum.setText(number);

        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(ContactDialogActivity.this, EditContactActivity.class);
                intent.putExtra(EditContactActivity.INTENT_EXTRA_CONTACT_NUM, number);
                startActivity(intent);
            }
        });


        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (permManager.permissionStatus(Manifest.permission.CALL_PHONE)) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + number));
                    startActivity(intent);
                } else {
                    permManager.requestPermission(58, Manifest.permission.CALL_PHONE);
                }
            }
        });

        btnMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"
                        + number)));
            }
        });
    }
}

package com.community.jboss.leadmanagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.community.jboss.leadmanagement.SettingsActivity.PREF_DARK_THEME;

public class AboutActivity extends AppCompatActivity {
    @BindView(R.id.about_developers_button)
    Button developersButton;
    @BindView(R.id.about_bar)
    Toolbar toolbar;
    @BindView(R.id.app_name_text)
    TextView title;
    @BindView(R.id.app_description_text)
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);

        if (useDarkTheme) {
            setTheme(R.style.AppTheme_BG);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        toolbar.setTitle("About");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        developersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutActivity.this, DevelopersAboutActivity.class));
            }
        });

        if (useDarkTheme) {
            final TypedValue value = new TypedValue();
            getTheme().resolveAttribute(R.attr.colorPrimary, value, true);
            title.setTextColor(Color.WHITE);
            description.setTextColor(Color.WHITE);
            developersButton.setBackgroundColor(value.data);
        }
    }

}

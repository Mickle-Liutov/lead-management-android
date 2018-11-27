package com.community.jboss.leadmanagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.community.jboss.leadmanagement.adapters.ContributersAdapter;
import com.community.jboss.leadmanagement.utils.ContributersGetterApi;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.community.jboss.leadmanagement.SettingsActivity.PREF_DARK_THEME;

public class DevelopersAboutActivity extends AppCompatActivity {

    @BindView(R.id.contributors_recycler)
    RecyclerView contributors_recycler;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.about_developers_bar)
    Toolbar toolbar;
    @BindView(R.id.developed_by_text)
    TextView developed_by_text;
    @BindView(R.id.and_text)
    TextView and_text;
    @BindView(R.id.jboss_logo)
    ImageView jboss_logo;

    ContributersAdapter contributors_recycler_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);

        if (useDarkTheme) {
            setTheme(R.style.AppTheme_BG);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers_about);
        ButterKnife.bind(this);

        toolbar.setTitle("Developers");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        jboss_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://www.jboss.org/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        if (useDarkTheme) {
            developed_by_text.setTextColor(Color.WHITE);
            and_text.setTextColor(Color.WHITE);
        }

        contributors_recycler.setLayoutManager(new LinearLayoutManager(this));
        new ContributersGetterApi(this).execute();

    }

    public void receiveData(String[][] data) {
        progressBar.setVisibility(View.GONE);
        if (data != null) {
            contributors_recycler_adapter = new ContributersAdapter(data, this);
            contributors_recycler.setAdapter(contributors_recycler_adapter);
            contributors_recycler.invalidate();
        } else {
            Toast.makeText(this, "No connection to the server", Toast.LENGTH_SHORT).show();
        }
    }
}

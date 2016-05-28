package com.heinrichreimersoftware.materialintro.demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.start_intro) Button startIntro;
    @BindView(R.id.start_splash) Button startSplash;
    @BindView(R.id.option_fullscreen) CheckBox optionFullscreen;
    @BindView(R.id.option_scrollable) CheckBox optionScrollable;
    @BindView(R.id.option_custom_fragments) CheckBox optionCustomFragments;
    @BindView(R.id.option_permissions) CheckBox optionPermissions;
    @BindView(R.id.option_show_back) CheckBox optionShowBack;
    @BindView(R.id.option_show_next) CheckBox optionShowNext;
    @BindView(R.id.option_skip_enabled) CheckBox optionSkipEnabled;
    @BindView(R.id.option_finish_enabled) CheckBox optionFinishEnabled;
    @BindView(R.id.option_get_started_enabled) CheckBox optionGetStartedEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        startIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainIntroActivity.class);
                intent.putExtra(MainIntroActivity.EXTRA_FULLSCREEN, optionFullscreen.isChecked());
                intent.putExtra(MainIntroActivity.EXTRA_SCROLLABLE, optionScrollable.isChecked());
                intent.putExtra(MainIntroActivity.EXTRA_CUSTOM_FRAGMENTS, optionCustomFragments.isChecked());
                intent.putExtra(MainIntroActivity.EXTRA_PERMISSIONS, optionPermissions.isChecked());
                intent.putExtra(MainIntroActivity.EXTRA_SKIP_ENABLED, optionSkipEnabled.isChecked());
                intent.putExtra(MainIntroActivity.EXTRA_SHOW_BACK, optionShowBack.isChecked());
                intent.putExtra(MainIntroActivity.EXTRA_SHOW_NEXT, optionShowNext.isChecked());
                intent.putExtra(MainIntroActivity.EXTRA_FINISH_ENABLED, optionFinishEnabled.isChecked());
                intent.putExtra(MainIntroActivity.EXTRA_GET_STARTED_ENABLED, optionGetStartedEnabled.isChecked());
                startActivity(intent);
            }
        });

        startSplash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SplashActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_github, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_github) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/HeinrichReimer/material-intro"));
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

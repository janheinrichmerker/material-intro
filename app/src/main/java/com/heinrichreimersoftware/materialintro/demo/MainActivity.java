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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button startIntro = (Button) findViewById(R.id.start_intro);
        startIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox optionFullscreen = (CheckBox) findViewById(R.id.option_fullscreen);
                CheckBox optionCustomFragments = (CheckBox) findViewById(R.id.option_custom_fragments);
                CheckBox optionSkipEnabled = (CheckBox) findViewById(R.id.option_skip_enabled);
                CheckBox optionFinishEnabled = (CheckBox) findViewById(R.id.option_finish_enabled);
                CheckBox optionStartNewActivity = (CheckBox) findViewById(R.id.option_start_new_activity);

                Intent introIntent;
                if (optionStartNewActivity.isChecked()) {
                    introIntent = new Intent(MainActivity.this, FinishActivity.class);
                    introIntent.setAction(FinishActivity.ACTION_FORWARD_INTRO);
                } else {
                    introIntent = new Intent(MainActivity.this, MaterialIntroActivity.class);
                }

                introIntent.putExtra(MaterialIntroActivity.EXTRA_FULLSCREEN, optionFullscreen.isChecked());
                introIntent.putExtra(MaterialIntroActivity.EXTRA_CUSTOM_FRAGMENTS, optionCustomFragments.isChecked());
                introIntent.putExtra(MaterialIntroActivity.EXTRA_SKIP_ENABLED, optionSkipEnabled.isChecked());
                introIntent.putExtra(MaterialIntroActivity.EXTRA_FINISH_ENABLED, optionFinishEnabled.isChecked());

                startActivity(introIntent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_main, menu);
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

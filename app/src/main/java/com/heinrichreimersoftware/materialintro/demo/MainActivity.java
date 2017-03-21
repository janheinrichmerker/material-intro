package com.heinrichreimersoftware.materialintro.demo;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.heinrichreimersoftware.materialintro.demo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setSupportActionBar(binding.toolbar);

        binding.startIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainIntroActivity.class);
                intent.putExtra(MainIntroActivity.EXTRA_FULLSCREEN, binding.optionFullscreen.isChecked());
                intent.putExtra(MainIntroActivity.EXTRA_SCROLLABLE, binding.optionScrollable.isChecked());
                intent.putExtra(MainIntroActivity.EXTRA_CUSTOM_FRAGMENTS,
                        binding.optionCustomFragments.isChecked());
                intent.putExtra(MainIntroActivity.EXTRA_PERMISSIONS, binding.optionPermissions.isChecked());
                intent
                        .putExtra(MainIntroActivity.EXTRA_SKIP_ENABLED, binding.optionSkipEnabled.isChecked());
                intent.putExtra(MainIntroActivity.EXTRA_SHOW_BACK, binding.optionShowBack.isChecked());
                intent.putExtra(MainIntroActivity.EXTRA_SHOW_NEXT, binding.optionShowNext.isChecked());
                intent.putExtra(MainIntroActivity.EXTRA_FINISH_ENABLED,
                        binding.optionFinishEnabled.isChecked());
                intent.putExtra(MainIntroActivity.EXTRA_GET_STARTED_ENABLED,
                        binding.optionGetStartedEnabled.isChecked());
                startActivity(intent);
            }
        });

        binding.startCanteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CanteenIntroActivity.class);
                startActivity(intent);
            }
        });

        binding.startSplash.setOnClickListener(new View.OnClickListener() {
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
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://github.com/HeinrichReimer/material-intro"));
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

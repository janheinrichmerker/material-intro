package com.heinrichreimersoftware.materialintro.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

                Intent intent = new Intent(MainActivity.this, MaterialIntroActivity.class);
                intent.putExtra(MaterialIntroActivity.EXTRA_FULLSCREEN, optionFullscreen.isChecked());
                intent.putExtra(MaterialIntroActivity.EXTRA_CUSTOM_FRAGMENTS, optionCustomFragments.isChecked());

                startActivity(intent);
            }
        });
    }
}

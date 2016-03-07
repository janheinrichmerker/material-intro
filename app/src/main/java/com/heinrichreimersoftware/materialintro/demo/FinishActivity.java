package com.heinrichreimersoftware.materialintro.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class FinishActivity extends AppCompatActivity {

    public static final String ACTION_FORWARD_INTRO = "com.heinrichreimersoftware.materialintro.demo.ACTION_FORWARD_INTRO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String action = intent.getAction();
        if (action != null && action.equals(ACTION_FORWARD_INTRO)) {
            Intent introIntent = new Intent(this, MaterialIntroActivity.class);
            introIntent.putExtras(intent.getExtras());

            startActivity(introIntent);
        }
    }
}

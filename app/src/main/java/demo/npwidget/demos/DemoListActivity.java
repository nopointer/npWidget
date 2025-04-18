package demo.npwidget.demos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import demo.npwidget.NpCircleProgressViewActivity;
import demo.npwidget.NpColorBarProgressViewActivity;
import demo.npwidget.NpDateTypeActivity;
import demo.npwidget.R;

public class DemoListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_list);

        findViewById(R.id.btnViewSleep).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DemoListActivity.this, NpSleepStateAreaViewAct.class));
            }
        });

        findViewById(R.id.btnViewNpPoint).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DemoListActivity.this, NpPointViewActivity.class));
            }
        });

        findViewById(R.id.btnViewNpLine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DemoListActivity.this, NpLineViewActivity.class));
            }
        });

        findViewById(R.id.btnViewBattery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DemoListActivity.this, NpBatteryViewActivity.class));
            }
        });

        findViewById(R.id.btnViewNpColorBarProgress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DemoListActivity.this, NpColorBarProgressViewActivity.class));
            }
        });

        findViewById(R.id.btnViewNpCircleProgress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DemoListActivity.this, NpCircleProgressViewActivity.class));
            }
        });

        findViewById(R.id.btnViewNpNpColumn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DemoListActivity.this, NpColumnViewActivity.class));
            }
        });


        findViewById(R.id.btnNpDateType).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DemoListActivity.this, NpDateTypeActivity.class));
            }
        });


//        startActivity(new Intent(this, NpLineViewActivity.class));
        startActivity(new Intent(this, NpIdealRangeIntervalViewActivity.class));
    }
}
